precision mediump float;

uniform sampler2D texShadow;
uniform sampler2D texGlow;

uniform float uStrength;
varying highp vec2 varyTexCoord;

uniform float uLightActive;

uniform vec4 uLightPosRadiusIntensity;
uniform vec2 uViewportSizeNormalized;
uniform vec3 uLightColor;
//uniform float uGlowToGamma;

float toGammaScalar(float v)
{
	// sqrt implementation seems to have some kind of "lowest value" over zero, or a discontinuity, resulting in a single banding at the lowest possible level of glow. It's only visible when the light is on
	if(v <= 0.0016)
		return v * 25.0;
	else
		return sqrt(v);
}

vec3 linearToGamma (vec3 color) {
        //color = max(color, vec3(0));
        //return max(1.055 * pow(color, vec3(0.416666667)) - 0.055, vec3(0));
		//return sqrt(color);
		//return mix(color, vec3(toGammaScalar(color.r), toGammaScalar(color.g), toGammaScalar(color.b)), uGlowToGamma);
		return vec3(toGammaScalar(color.r), toGammaScalar(color.g), toGammaScalar(color.b));
}

// - uViewportSizeNormalized was previously sent as uViewportSize to the shader, and normalized in glowSource(), but the squaring being the part of normalization
// put the values in poorly represented range
// - varyTexCoord needed to get highp to accurately compute the distance from light ('dist'), otherwise the high frequency brightness near the light position was not right
// varyTexCoord doesn't need highp otherwise, but I don't think it impacts performance too much when the lighting is disabled
vec3 glowSource()
{
	float dist = length((uLightPosRadiusIntensity.xy - varyTexCoord) * uViewportSizeNormalized);
	vec3 v = uLightColor * uLightPosRadiusIntensity.w * clamp(1.0 - sqrt(0.5 * dist / uLightPosRadiusIntensity.z), 0.0, 1.0);
	return v*v;
}

void main()
{
	vec3 glow = uStrength * texture2D(texGlow, varyTexCoord).rgb;
	glow = linearToGamma(glow);
	glow += uLightActive * glowSource();
	gl_FragColor = vec4(glow, texture2D(texShadow, varyTexCoord).r);
}
