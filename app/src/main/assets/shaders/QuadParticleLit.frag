precision mediump float;

uniform sampler2D texture;

varying vec3 varColor;
varying vec2 varTexCoord;

uniform sampler2D texGlow;
uniform vec2 uScreenRes;
uniform float uGlowIntensity;

uniform sampler2D texShadow;

uniform float shadowActive;
uniform float inverseShadow;


void main()
{
	vec3 glow = texture2D(texGlow, gl_FragCoord.xy / uScreenRes).rgb;
	
	glow = clamp(2.0 * (glow), 0.0, 1.0); 
	
	float shadow = texture2D(texShadow, gl_FragCoord.xy / uScreenRes).r;
	shadow = shadowActive*shadow;
	
	vec3 base = vec3(0.5) * (1.0 - uGlowIntensity);
	float shadowingFactor = mix(1.0 - shadow, shadow, inverseShadow);
	vec3 brightness = base + shadowingFactor * glow.rgb * uGlowIntensity;
	
	gl_FragColor = vec4(brightness, 1.0) * texture2D(texture, varTexCoord) * vec4(varColor, 1.0);
	//gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
}
