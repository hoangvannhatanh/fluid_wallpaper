precision mediump float;

uniform sampler2D tex;
uniform vec2 uOutputPixelSize;
varying vec2 varyTexCoord;

uniform float uBorderMirror;
uniform float uBorderRepeat;

#define BORDER_EPS 0.001

// Brightness function
float Brightness(vec3 c)
{
	return max(max(c.r, c.g), c.b);
}

// should have a "partial repeat" instead?
vec2 repeatAndMirrorTex(vec2 t)
{
    t = t.x > (1.0-BORDER_EPS) ?
		vec2(mix(t.x, t.x - 1.0, uBorderRepeat), mix(t.y, 1.0 - t.y, uBorderMirror))
		: (t.x < BORDER_EPS ? vec2(mix(t.x, t.x + 1.0, uBorderRepeat), mix(t.y, 1.0 - t.y, uBorderMirror)) : t);
    return t;
}

vec4 zeroBorderSample(vec4 sample, vec2 t)
{
    float borderZero = 1.0 - uBorderRepeat;
    sample = (t.x > (1.0-BORDER_EPS) || t.x < BORDER_EPS) ? (mix(sample, vec4(0.0), borderZero) + sample) * 0.5 : sample;
    sample = (t.y > (1.0-BORDER_EPS) || t.y < BORDER_EPS) ? (mix(sample, vec4(0.0), borderZero) + sample) * 0.5 : sample;
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
	vec4 offsets = uOutputPixelSize.xyxy * 0.5 * vec4(-1.0, -1.0, 1.0, 1.0);
	vec4 sum = vec4(0.0);
	sum += sampleBlurTex(varyTexCoord + offsets.xy);
	sum += sampleBlurTex(varyTexCoord + offsets.zy);
	sum += sampleBlurTex(varyTexCoord + offsets.xw);
	sum += sampleBlurTex(varyTexCoord + offsets.zw);
	
	float outerWeight = 1.0;
	sum += outerWeight * sampleBlurTex(varyTexCoord + vec2( 3.0 * offsets.z, 0.0));
	sum += outerWeight * sampleBlurTex(varyTexCoord + vec2(-3.0 * offsets.z, 0.0));
	sum += outerWeight * sampleBlurTex(varyTexCoord + vec2(0.0,  3.0 * offsets.w));
	sum += outerWeight * sampleBlurTex(varyTexCoord + vec2(0.0, -3.0 * offsets.w));
	
	vec4 final = sum / (4.0 + 4.0 * outerWeight);
	
	gl_FragColor = final;
}
