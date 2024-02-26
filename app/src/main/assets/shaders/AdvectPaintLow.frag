precision highp float;

uniform sampler2D texPaint;
uniform sampler2D texInput;
uniform sampler2D texVel;
uniform vec2 uVelScale;
uniform float uFadeCoeff;
uniform float uBorderMirror;
uniform float uBorderRepeat;

uniform vec2 uTexInputMask;
uniform vec2 uRes;
uniform vec2 uPixelSize;
uniform vec2 uVelRes;
uniform vec2 uVelPixelSize;

varying vec2 varyTexCoord;

//uniform vec2 uPixelSize;
//uniform vec2 uRes;

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

vec2 EncodeFloatRG( float v, float range )
{
	v = clamp(v, 0.0, range);
	v /= range;
	
    vec2 enc = vec2(1.0, 255.0) * v;
    enc = fract(enc);
    enc -= enc.yy * vec2(1.0/255.0, 0.0);
    return enc;
}

float DecodeFloatRG01( vec2 rg )
{
    return dot( rg, vec2(1.0, 1.0/255.0) );
}

vec2 sampleTex(vec2 samplePos, sampler2D texture, vec2 resolution, vec2 pixelSize)
{
	vec2 intPos = samplePos * resolution - vec2(0.5);
	float x0 = floor(intPos.x);
	float x1 = x0 + 1.0;
	float y0 = floor(intPos.y);
	float y1 = y0 + 1.0;
	float s0 = x1 - intPos.x;
	float t0 = y1 - intPos.y;
	x0 = (x0 + 0.5) * pixelSize.x;
	x1 = (x1 + 0.5) * pixelSize.x;
	y0 = (y0 + 0.5) * pixelSize.y;
	y1 = (y1 + 0.5) * pixelSize.y;
	
	vec4 sample = texture2D(texture, vec2(x0, y0));
	vec2 v00 = vec2(DecodeFloatRG01(sample.rg), DecodeFloatRG01(sample.ba));
	sample = texture2D(texture, vec2(x0, y1));
	vec2 v01 = vec2(DecodeFloatRG01(sample.rg), DecodeFloatRG01(sample.ba));
	sample = texture2D(texture, vec2(x1, y0));
	vec2 v10 = vec2(DecodeFloatRG01(sample.rg), DecodeFloatRG01(sample.ba));
	sample = texture2D(texture, vec2(x1, y1));
	vec2 v11 = vec2(DecodeFloatRG01(sample.rg), DecodeFloatRG01(sample.ba));

	return s0*t0*v00 + s0*(1.0-t0)*v01 + (1.0-s0)*t0*v10 + (1.0-s0)*(1.0-t0)*v11;
}

void main()
{
	float velEncodeRange = 6.375;
	float paintEncodeRange = 12.75;
	//vec2 offset = (texture2D(texVel, varyTexCoord).rg * 2.0 - vec2(1.0)) * 0.25 * uVelScale;
	vec2 velOffset = sampleTex(varyTexCoord, texVel, uVelRes, uVelPixelSize);
	velOffset = (velEncodeRange * velOffset - vec2(velEncodeRange*0.5)) * uVelScale;
	vec2 samplePos = varyTexCoord - velOffset;
	
	samplePos = repeatAndMirrorTex(samplePos);
	samplePos.yx = repeatAndMirrorTex(samplePos.yx);
	
	//vec4 sample = texture2D(texPaint, samplePos);
	//vec2 colors = vec2(DecodeFloatRG(sample.rg), DecodeFloatRG(sample.ba));
	vec2 colors = sampleTex(samplePos, texPaint, uRes, uPixelSize) * paintEncodeRange;
	
	vec4 paintInput = texture2D(texInput, samplePos);
	paintInput = 25.0 * (paintInput * paintInput); // decoding of encoding defined in Procedures.cpp -> copyVelAndPaintInputLoop
	vec2 actualInput = vec2(dot(paintInput.rb, uTexInputMask), dot(paintInput.ga, uTexInputMask));
#if defined(PAINT_SIGNED)
	// A different way to deal with "signed" input than in the high quality version (and here it's not actually signed, but positive and negative magnitudes are stored in R and G),
	// but it's good enough for the backward-compatible low qual version.
	vec2 inputValueMask = abs(sign(actualInput));
	actualInput = inputValueMask.x * actualInput.xx * vec2(1.0, -1.0) + inputValueMask.y * actualInput.yy * vec2(-1.0, 1.0);
#endif
	colors += actualInput;
	colors *= uFadeCoeff;
	colors = clamp(colors, 0.0, paintEncodeRange*0.999); // value equal to paintEncodeRange is not properly encoded, so it's necessary to clamp a bit more
	gl_FragColor = vec4(EncodeFloatRG(colors.r, paintEncodeRange), EncodeFloatRG(colors.g, paintEncodeRange));
}
