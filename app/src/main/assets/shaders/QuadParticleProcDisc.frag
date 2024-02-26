precision mediump float;

varying vec4 varColor;
varying vec2 varTexCoord;
varying vec2 varParticleSize;

uniform float uIntensity;
uniform float uIsBubble;
uniform vec2 uBlendFlags; // r - multiply RGB by intensity, g - additive (multiply RGB by varColor.a, put sum of RGB in final A)
uniform float uDoubleColorScale;

void main()
{
	float intensity = varParticleSize.x*(1.0 - length(2.0 * (varTexCoord - vec2(0.5))));
	if(intensity * uIsBubble > 1.0) // if uIsBubble is 0, the inside of this if will never execute. It is responsible for making a dot into a bubble (decrease intensity in the interior of the dot)
		intensity = 1.0 - (intensity - 1.0) / varParticleSize.x;
	intensity = smoothstep(0.0, 1.0, intensity);

	// Explicit definitions of 3 blending modes. Based on this, they are realized using flags and mixing in the code below.
	//vec4 intensityRGBA = vec4(vec3(uIntensity) * intensity, SUM_OF_RGB); // ADDITIVE
	//vec4 intensityRGBA = vec4(vec3(uIntensity), intensity); // REPLACE
	//vec4 intensityRGBA = vec4(vec3(uIntensity) * intensity, intensity); // REPLACE + SHADOW
	
	vec4 adjustedColor = mix(varColor, vec4(varColor.r - varColor.g, 0.0, 0.0, varColor.a), uDoubleColorScale);
	
	vec4 finalColor = adjustedColor * vec4(vec3(uIntensity), intensity);
	finalColor.rgb *= mix(1.0, intensity, uBlendFlags.x);
	finalColor.rgb *= mix(1.0, adjustedColor.a, uBlendFlags.y);
	finalColor.a = mix(finalColor.a, dot(vec3(1.0), abs(finalColor.rgb)), uBlendFlags.y);
	gl_FragColor = finalColor;
}
