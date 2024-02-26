precision mediump float;

uniform sampler2D texture;

varying vec3 varColor;

uniform sampler2D texGlow;
uniform vec2 uScreenRes;
uniform float uGlowIntensity;

void main()
{
	vec4 glow = texture2D(texGlow, gl_FragCoord.xy / uScreenRes);
	glow.a = 1.0;
	vec3 base = vec3(0.5) * (1.0 - uGlowIntensity);
	glow.rgb = base + 8.0 * glow.rgb * uGlowIntensity;
	gl_FragColor = glow * texture2D(texture, gl_PointCoord) * vec4(varColor, 1.0);
}
