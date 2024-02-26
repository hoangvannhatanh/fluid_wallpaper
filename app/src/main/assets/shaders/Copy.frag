precision mediump float;

uniform sampler2D tex;
varying vec2 varyTexCoord;

void main()
{
	gl_FragColor = texture2D(tex, varyTexCoord);
}
