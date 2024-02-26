precision mediump float;

uniform sampler2D tex;
varying vec2 varyTexCoord;

void main()
{
	gl_FragColor = vec4(0.0, 0.0, 0.0, texture2D(tex, varyTexCoord).r);
}
