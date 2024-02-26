precision mediump float;

varying vec4 varColor;
varying vec2 varTexCoord;
varying vec2 varParticleSize;

uniform float uIntensity;
uniform vec2 uBlendFlags;

void main()
{
	float intensity = varParticleSize.x*(1.0 - length(2.0 * (varTexCoord - vec2(0.5))));
	if(intensity > 1.0)
		intensity = 1.0 - (intensity - 1.0) / varParticleSize.x;
	intensity = smoothstep(0.0, 1.0, intensity);
	
	vec4 finalColor = varColor * vec4(vec3(uIntensity), intensity);
	finalColor.rgb *= mix(1.0, intensity, uBlendFlags.x);
	finalColor.rgb *= mix(1.0, varColor.a, uBlendFlags.y);
	finalColor.a = mix(finalColor.a, dot(vec3(1.0), abs(finalColor.rgb)), uBlendFlags.y);
	gl_FragColor = finalColor;
}
