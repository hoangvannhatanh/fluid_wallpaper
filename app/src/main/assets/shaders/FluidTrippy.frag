precision mediump float;

varying vec2 varyTexCoord;
uniform sampler2D texFluid;

uniform vec3 invColor;

void main()
{
	vec3 color = texture2D(texFluid, varyTexCoord).rgb;
	color = smoothstep(0.0, 1.0, color);
	
	vec3 backgr = vec3(0.0, 0.0, 0.0);
	vec3 full = vec3(0.0, 0.0, 0.0);
	vec3 edge = vec3(0.5, 0.5, 0.5);
	
	vec3 color1 = mix(2.0*edge, backgr, (edge - color) / edge);
	vec3 color2 = mix(2.0*edge, full, (color - edge) / (vec3(1.0) - edge));
	color.r = color.r < edge.r ? color1.r : color2.r;
	color.g = color.g < edge.g ? color1.g : color2.g;
	color.b = color.b < edge.b ? color1.b : color2.b;
	
	//color.r = pow(color.r, 2.2);
	//color.g = pow(color.g, 2.2);
	//color.b = pow(color.b, 2.2);
	gl_FragColor = vec4(color, 1.0);
}
