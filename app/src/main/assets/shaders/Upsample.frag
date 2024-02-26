precision mediump float;

uniform sampler2D tex;
varying vec2 varyTexCoord;

uniform vec2 uInputPixelSize;

uniform float uSrcScale;
uniform float uDestScale;

uniform float uBorderMirror;
uniform float uBorderRepeat;
uniform float uAllowBorderZero;

#define BORDER_EPS 0.001


// should have a "partial repeat" instead?
vec2 repeatAndMirrorTex(vec2 t)
{
    t = t.x > (1.0-BORDER_EPS) ?
		vec2(mix(t.x, t.x - 1.0, uBorderRepeat), mix(t.y, 1.0 - t.y, uBorderMirror))
		: (t.x < BORDER_EPS ? vec2(mix(t.x, t.x + 1.0, uBorderRepeat), mix(t.y, 1.0 - t.y, uBorderMirror)) : t);
    return t;
}

vec3 zeroBorderSample(vec3 sample, vec2 t)
{
    float borderZero = 1.0 - uBorderRepeat;
	borderZero *= uAllowBorderZero;
    sample = (t.x > (1.0-BORDER_EPS) || t.x < BORDER_EPS) ? (mix(sample, vec3(0.0), borderZero) + sample) * 0.5 : sample;
    sample = (t.y > (1.0-BORDER_EPS) || t.y < BORDER_EPS) ? (mix(sample, vec3(0.0), borderZero) + sample) * 0.5 : sample;
    return sample;
}

vec3 sampleBlurTex(vec2 coords)
{
	coords = repeatAndMirrorTex(coords);
	coords = repeatAndMirrorTex(coords.yx).yx;
	vec3 sample = texture2D(tex, coords).rgb;
	//if(uPixelStep.y > 0.0)
	//{
	//if( coords.x < 0.55  && coords.x > 0.45 && coords.y < 0.55  && coords.y > 0.45) sample = vec3(1.0);
	//else sample = vec3(0.0);
	//}
	return zeroBorderSample(sample, coords);
}

void main()
{
	// Update 01.2022: 1.0 step in line below is quite weird, because it weighs texels further around the sample point more than the texel that is closest to the sample point.
	// As a result, it spreads out some of the glow away from the glowing sources.
	// However, it does additionally increase the range of glow, which may be desirable. I'm leaving it now for consistency as the differences are not very noticeable.
	// 0.5 step, on the other hand, seems to correspond exactly to the "tent" upsampling, and it may also be a tiny bit faster due to touching less pixels per kernel.
	vec4 offsets = uInputPixelSize.xyxy * 1.0 * vec4(-1.0, -1.0, 1.0, 1.0);
	vec3 s1 = sampleBlurTex(varyTexCoord + offsets.xy);
	vec3 s2 = sampleBlurTex(varyTexCoord + offsets.zy);
	vec3 s3 = sampleBlurTex(varyTexCoord + offsets.xw);
	vec3 s4 = sampleBlurTex(varyTexCoord + offsets.zw);
	//vec4 s1 = texture2D(tex, varyTexCoord + vec2(uInputPixelSize.x, 0.0));
	//vec4 s2 = texture2D(tex, varyTexCoord - vec2(uInputPixelSize.x, 0.0));
	//vec4 s3 = texture2D(tex, varyTexCoord + vec2(0.0, uInputPixelSize.y));
	//vec4 s4 = texture2D(tex, varyTexCoord - vec2(0.0, uInputPixelSize.y));
	
	/* // Not sure why I was not using it
	// Karis's luma weighted average
	float s1w = 1.0 / (Brightness(s1) + 1.0);
	float s2w = 1.0 / (Brightness(s2) + 1.0);
	float s3w = 1.0 / (Brightness(s3) + 1.0);
	float s4w = 1.0 / (Brightness(s4) + 1.0);
	float one_div_wsum = 1.0 / (s1w + s2w + s3w + s4w);

	//vec3 final = (s1 * s1w + s2 * s2w + s3 * s3w + s4 * s4w) * one_div_wsum;
	*/
	vec3 final = (s1 + s2 + s3 + s4) * 0.25;
	
	gl_FragColor = vec4(1.2 * uSrcScale * final, uDestScale);
	//gl_FragColor = texture2D(tex, varyTexCoord);
}
