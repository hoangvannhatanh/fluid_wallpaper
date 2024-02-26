precision mediump float;

uniform sampler2D tex;
varying vec2 varyTexCoord;
uniform vec2 uLightPos;
uniform vec2 uTexSize;
uniform float uLightRadius;
uniform float uLightIntensity;
uniform vec3 uLightColor;

vec3 glowSource()
{
	float dist = length((uLightPos - varyTexCoord) * normalize(uTexSize));
	return vec3(1.0) * 1.0 * clamp(1.0 - sqrt(0.5 * dist / uLightRadius), 0.0, 1.0);
}

void main()
{
	vec3 sample = texture2D(tex, varyTexCoord).rgb;
	sample += uLightIntensity * uLightColor * glowSource();
	gl_FragColor = vec4(sample, 1.0);
}
