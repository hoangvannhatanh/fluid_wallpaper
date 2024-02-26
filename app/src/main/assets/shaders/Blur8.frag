precision mediump float;

uniform sampler2D tex;
uniform vec2 uPixelStep;
uniform float uCoeffs[9];
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

vec3 zeroBorderSample(vec3 sample, vec2 t)
{
    float borderZero = 1.0 - uBorderRepeat;
    sample = (t.x > 1.0 || t.x < 0.0 || t.y > 1.0 || t.y < 0.0) ? mix(sample, vec3(0.0), borderZero) : sample;
    return sample;
}

vec3 sampleBlurTex(vec2 coords)
{
	coords = repeatAndMirrorTex(coords);
	coords = repeatAndMirrorTex(coords.yx).yx;
	vec3 sample = texture2D(tex, coords).rgb;
	return zeroBorderSample(sample, coords);
}

void main()
{
	vec3 sum = vec3(0.0);
	
	sum += sampleBlurTex(varyTexCoord - 8.0 * uPixelStep).rgb * uCoeffs[0];
	sum += sampleBlurTex(varyTexCoord - 7.0 * uPixelStep).rgb * uCoeffs[1];
	sum += sampleBlurTex(varyTexCoord - 6.0 * uPixelStep).rgb * uCoeffs[2];
	sum += sampleBlurTex(varyTexCoord - 5.0 * uPixelStep).rgb * uCoeffs[3];
	sum += sampleBlurTex(varyTexCoord - 4.0 * uPixelStep).rgb * uCoeffs[4];
	sum += sampleBlurTex(varyTexCoord - 3.0 * uPixelStep).rgb * uCoeffs[5];
	sum += sampleBlurTex(varyTexCoord - 2.0 * uPixelStep).rgb * uCoeffs[6];
	sum += sampleBlurTex(varyTexCoord - 1.0 * uPixelStep).rgb * uCoeffs[7];
	sum += sampleBlurTex(varyTexCoord).rgb * uCoeffs[8];
	sum += sampleBlurTex(varyTexCoord + 1.0 * uPixelStep).rgb * uCoeffs[7];
	sum += sampleBlurTex(varyTexCoord + 2.0 * uPixelStep).rgb * uCoeffs[6];
	sum += sampleBlurTex(varyTexCoord + 3.0 * uPixelStep).rgb * uCoeffs[5];
	sum += sampleBlurTex(varyTexCoord + 4.0 * uPixelStep).rgb * uCoeffs[4];
	sum += sampleBlurTex(varyTexCoord + 5.0 * uPixelStep).rgb * uCoeffs[3];
	sum += sampleBlurTex(varyTexCoord + 6.0 * uPixelStep).rgb * uCoeffs[2];
	sum += sampleBlurTex(varyTexCoord + 7.0 * uPixelStep).rgb * uCoeffs[1];
	sum += sampleBlurTex(varyTexCoord + 8.0 * uPixelStep).rgb * uCoeffs[0];
	
	sum *= uCoeffsSumInv;
	
	/*
	sum += texture2D(tex, vec2(varyTexCoord.x - 5.5*blurSize, varyTexCoord.y)).rgb * 0.09;
	sum += texture2D(tex, vec2(varyTexCoord.x - 3.5*blurSize, varyTexCoord.y)).rgb * 0.14;
	sum += texture2D(tex, vec2(varyTexCoord.x - 1.5*blurSize, varyTexCoord.y)).rgb * 0.2;
	sum += texture2D(tex, vec2(varyTexCoord.x, varyTexCoord.y)).rgb * 0.14;
	sum += texture2D(tex, vec2(varyTexCoord.x + 1.5*blurSize, varyTexCoord.y)).rgb * 0.2;
	sum += texture2D(tex, vec2(varyTexCoord.x + 3.5*blurSize, varyTexCoord.y)).rgb * 0.14;
	sum += texture2D(tex, vec2(varyTexCoord.x + 5.5*blurSize, varyTexCoord.y)).rgb * 0.09;
	*/

	gl_FragColor = vec4((sum), 1.0);
}
