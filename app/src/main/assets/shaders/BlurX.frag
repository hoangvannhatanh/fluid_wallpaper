precision mediump float;

#define NUM_SAMPLES_HALF XX

uniform sampler2D tex;
uniform vec2 uPixelStep;
uniform float uCoeffs[NUM_SAMPLES_HALF+1];
uniform float uCoeffsSumInv;
uniform float uBorderMirror;
uniform float uBorderRepeat;

varying vec2 varyTexCoord;

vec2 repeatAndMirrorTex(vec2 t)
{
    t = t.x > 1.0 ? vec2(mix(t.x, t.x - 1.0, uBorderRepeat), mix(t.y, 1.0 - t.y, uBorderMirror)) : t;
    t = t.x < 0.0 ? vec2(mix(t.x, t.x + 1.0, uBorderRepeat), mix(t.y, 1.0 - t.y, uBorderMirror)) : t;
    return t;
}

vec4 zeroBorderSample(vec4 sample, vec2 t)
{
    float borderZero = 1.0 - uBorderRepeat;
    sample = (t.x > 1.0 || t.x < 0.0 || t.y > 1.0 || t.y < 0.0) ? mix(sample, vec4(0.0), borderZero) : sample;
    return sample;
}

vec4 sampleBlurTex(vec2 coords)
{
	coords = repeatAndMirrorTex(coords);
	coords = repeatAndMirrorTex(coords.yx).yx;
	vec4 sample = texture2D(tex, coords);
	//if(uPixelStep.y > 0.0)
	//{
	//if( coords.x < 0.55  && coords.x > 0.45 && coords.y < 0.55  && coords.y > 0.45) sample = vec3(1.0);
	//else sample = vec3(0.0);
	//}
	return zeroBorderSample(sample, coords);
}

void main()
{
	vec4 sum = vec4(0.0);
	
	float stepScale = 1.0;
	vec2 sampleTexCoord = varyTexCoord - float(NUM_SAMPLES_HALF) * uPixelStep * stepScale;
	for(int i = -NUM_SAMPLES_HALF; i <= NUM_SAMPLES_HALF ; i++)
	{
		//sum += sampleBlurTex(sampleTexCoord).rgb * ((float)(NUM_SAMPLES_HALF-abs(i))) / ((float)NUM_SAMPLES_HALF);
		//sum += sampleBlurTex(sampleTexCoord).rgb * uCoeffs[NUM_SAMPLES_HALF-abs(i)] * uCoeffs[NUM_SAMPLES_HALF-abs(i)] * 10.0 * 6.0;
		sum += sampleBlurTex(sampleTexCoord) * uCoeffs[NUM_SAMPLES_HALF-int(abs(float(i)))] * 6.0;
		//sum += sampleBlurTex(sampleTexCoord).rgb * 2.0 * exp(-((float)abs(i)) * 1.5 / ((float)NUM_SAMPLES_HALF));
		sampleTexCoord += uPixelStep * stepScale;
	}

	sum *= 0.15;

	gl_FragColor = sum;
}
