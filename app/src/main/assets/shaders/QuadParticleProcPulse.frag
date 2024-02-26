precision mediump float;

varying vec4 varColor;
varying vec2 varTexCoord;
varying vec2 varParticleSize;

uniform float uIntensity;
uniform vec2 uBlendFlags;

void main()
{
	float intensity = length(2.0*(varTexCoord - vec2(0.5)));
	float intensityColor = clamp((1.0 - intensity*intensity*intensity*intensity), 0.0, 1.0);
	float intensityAlpha = clamp((1.0 - intensity*intensity*intensity), 0.0, 1.0);
	//float intensity = clamp((1.0 - length(2.0*(varTexCoord - vec2(0.5)))), 0.0, 1.0);
	//gl_FragColor = varColor * vec4(vec3(uIntensity), 1.0) * vec4(vec3(intensityColor), intensityAlpha);
	
	vec4 finalColor = varColor * vec4(vec3(uIntensity), intensityAlpha);
	finalColor.rgb *= mix(1.0, intensityAlpha, uBlendFlags.x);
	finalColor.rgb *= mix(1.0, varColor.a, uBlendFlags.y);
	finalColor.a = mix(finalColor.a, dot(vec3(1.0), abs(finalColor.rgb)), uBlendFlags.y);
	gl_FragColor = finalColor;
}
