precision mediump float;

varying vec2 varyTexCoord;
uniform sampler2D texture;

uniform vec3 invColor;

uniform vec3 color0Low;
uniform vec3 color0High;
uniform vec3 color1Low;
uniform vec3 color1High;
uniform vec3 color2Low;
uniform vec3 color2High;

uniform vec3 colorActiveMask;
uniform vec3 backgrColor;

void main()
{
	vec3 intens = texture2D(texture, varyTexCoord).rgb;
	intens = smoothstep(0.0, 1.0, intens);
	vec3 accum = vec3(0.0);
	float edge = 0.5;
	
	vec3 color1 = mix(color0Low, vec3(0.0), (edge - intens.r) / edge);
	vec3 color2 = mix(color0Low, color0High, (intens.r - edge) / (1.0 - edge));
	accum += colorActiveMask.r * (intens.r < edge ? color1 : color2);
	
	color1 = mix(color1Low, vec3(0.0), (edge - intens.g) / edge);
	color2 = mix(color1Low, color1High, (intens.g - edge) / (1.0 - edge));
	accum += colorActiveMask.g * (intens.g < edge ? color1 : color2);
	
	color1 = mix(color2Low, vec3(0.0), (edge - intens.b) / edge);
	color2 = mix(color2Low, color2High, (intens.b - edge) / (1.0 - edge));
	accum += colorActiveMask.b * (intens.b < edge ? color1 : color2);
	
	float paintAmount = min(dot(intens, colorActiveMask), 1.0);
	gl_FragColor.rgb = accum + (1.0 - paintAmount) * backgrColor;
	gl_FragColor.a = 1.0;
}
