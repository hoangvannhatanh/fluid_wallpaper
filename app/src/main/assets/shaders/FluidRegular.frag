precision mediump float;

varying vec2 varyTexCoord;
uniform sampler2D texFluid;

uniform vec3 invColor;

void main()
{
	vec3 color = texture2D(texFluid, varyTexCoord).rgb;
	color = smoothstep(0.0, 1.0, color);
	
	//color.r = pow(color.r, 2.2);
	//color.g = pow(color.g, 2.2);
	//color.b = pow(color.b, 2.2);
	gl_FragColor = vec4(color, 1.0);
}
