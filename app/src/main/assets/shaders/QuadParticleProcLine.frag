precision mediump float;

varying vec4 varColor;
varying vec2 varTexCoord;
varying vec2 varParticleSize;

uniform float uIntensity;
uniform float uTrailSmoothingOffset; // when set to 1.0 instead of 0.0, disables smoothing along the line particle, so it can be used in trail rendering
uniform vec2 uBlendFlags;

uniform float uDoubleColorScale;
uniform float uIntensitySlope;
uniform vec2 uIntensitySlope2;

void main()
{
	vec2 intensityXY = min(varParticleSize*(vec2(1.0) - 2.0 * abs(varTexCoord - vec2(0.5))) + vec2(uTrailSmoothingOffset, 0.0), vec2(1.0));
	intensityXY = smoothstep(vec2(0.0), vec2(1.0), intensityXY);
	// intensityXY.x *= pow(uIntensitySlope, -(2.0 * varTexCoord.x - 1.0));
	// piecewise optimization of above line to get rid of pow()
	intensityXY.x += -(varTexCoord.x - 0.5) * uIntensitySlope2.x + uIntensitySlope2.y;
	float intensity = intensityXY.x * intensityXY.y;
	
	vec4 adjustedColor = mix(varColor, vec4(varColor.r - varColor.g, 0.0, 0.0, varColor.a), uDoubleColorScale);
	
	vec4 finalColor = adjustedColor * vec4(vec3(uIntensity), intensity);
	finalColor.rgb *= mix(1.0, intensity, uBlendFlags.x);
	finalColor.rgb *= mix(1.0, adjustedColor.a, uBlendFlags.y);
	finalColor.a = mix(finalColor.a, dot(vec3(1.0), abs(finalColor.rgb)), uBlendFlags.y);
	gl_FragColor = finalColor;
}
