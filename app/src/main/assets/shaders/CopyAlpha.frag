precision mediump float;

uniform sampler2D tex;
varying vec2 varyTexCoord;

void main()
{
	gl_FragColor.rgba = vec4(texture2D(tex, varyTexCoord).a);
}
