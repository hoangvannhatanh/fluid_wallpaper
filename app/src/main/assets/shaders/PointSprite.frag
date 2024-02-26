precision mediump float;

uniform sampler2D texture;

varying vec3 varColor;

void main()
{
	gl_FragColor = texture2D(texture, gl_PointCoord) * vec4(varColor, 1.0);
}
