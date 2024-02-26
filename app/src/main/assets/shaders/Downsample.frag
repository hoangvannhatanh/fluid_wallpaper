precision mediump float;

uniform sampler2D tex;
varying vec2 varyTexCoord;

void main()
{
	vec4 sample = texture2D(tex, varyTexCoord);
	gl_FragColor = sample;
}
