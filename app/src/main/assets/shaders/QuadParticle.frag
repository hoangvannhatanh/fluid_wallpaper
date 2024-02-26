precision mediump float;

uniform sampler2D texture;

varying vec4 varColor;
varying vec2 varTexCoord;

void main()
{
	gl_FragColor = varColor;
	//gl_FragColor = texture2D(texture, varTexCoord).rgbr * vec4(varColor.rgb, 1.0);
}
