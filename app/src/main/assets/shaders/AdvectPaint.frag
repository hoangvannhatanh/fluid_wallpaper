precision highp float;

uniform sampler2D texPaint;
uniform sampler2D texInput;
uniform sampler2D texVel;
uniform sampler2D texNoise;
uniform vec2 uVelScale;
uniform float uFadeCoeff;
uniform float uBorderMirror;
uniform float uBorderRepeat;
uniform vec2 uNoiseIntensityAndSwitch;

varying highp vec2 varyTexCoord;

vec2 repeatAndMirrorTex(vec2 t)
{
#if defined(BORDER_MIRROR)
	t.y = (t.x < 0.0 || t.x > 1.0) ? 1.0 - t.y : t.y;
#endif
#if defined(BORDER_REPEAT)
	t.x = fract(t.x);
#endif
    //t = t.x > 1.0 ? vec2(mix(t.x, t.x - 1.0, uBorderRepeat), mix(t.y, 1.0 - t.y, uBorderMirror)) : t;
    //t = t.x < 0.0 ? vec2(mix(t.x, t.x + 1.0, uBorderRepeat), mix(t.y, 1.0 - t.y, uBorderMirror)) : t;
    return t;
}

void main()
{
	highp vec2 offset = texture2D(texVel, varyTexCoord).rg * uVelScale;
	highp vec2 samplePos = varyTexCoord - offset;
	
#if defined(VELOCITY_NOISE)
	highp vec4 noiseFieldsSample = texture2D(texNoise, varyTexCoord);
	highp vec2 noiseVel = mix(noiseFieldsSample.xy, noiseFieldsSample.zw, uNoiseIntensityAndSwitch.y);
	samplePos -= noiseVel * uVelScale * uNoiseIntensityAndSwitch.x * min(length(offset) * 100.0, 1.0);
#endif
	
	samplePos = repeatAndMirrorTex(samplePos);
	samplePos = repeatAndMirrorTex(samplePos.yx).yx;
	//samplePos.yx = repeatAndMirrorTex(samplePos.yx); // doesn't work on some devices for some reason...!
	
	vec4 paintInput = texture2D(texInput, samplePos);
	paintInput = 25.0 * (paintInput * paintInput); // decoding of encoding defined in Procedures.cpp -> copyVelAndPaintInputLoop
	
	vec4 sample = texture2D(texPaint, samplePos);
#if defined(PAINT_SIGNED)
	float totalInput = paintInput.r - paintInput.g;
	/*if(sign(totalInput) == sign(sample.r))
		sample.r += totalInput;
	else
		sample.r = mix(sample.r + totalInput, totalInput, clamp(abs(totalInput), 0.0, 1.0));*/
		
	// optimization of the commented code above, to remove if/else, working slightly different when sign(sample.r) = 0, but it is actually more desirable
	float inputSwitch = abs(sign(totalInput) - sign(sample.r));
	sample.r = min(2.0 - inputSwitch, 1.0) * (sample.r + totalInput) + max(inputSwitch - 1.0, 0.0) * mix(sample.r + totalInput, totalInput, clamp(abs(totalInput), 0.0, 1.0));
		
	sample.g = 0.0; // .g is used in the lowqual version for a competing color, instead of the opposite sign of float as here
#else
	sample = abs(sample) + paintInput;
#endif
	
	sample *= uFadeCoeff;
	sample.w = dot(vec3(1.0), abs(sample.xyz));

	gl_FragColor = sample;
	
	//gl_FragColor = vec4(texture2D(texVel, varyTexCoord).rg * 3.1 + vec2(0.5), 0.0, 1.0); // visualize velocity
	//gl_FragColor = vec4(noiseVel * 0.25 + vec2(0.5), 0.0, 1.0); // visualize noise
}
