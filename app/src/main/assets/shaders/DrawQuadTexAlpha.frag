precision mediump float;

varying vec2 varyTexCoord;
uniform sampler2D texture;

void main()
{
	vec4 tex = texture2D(texture, varyTexCoord);
	gl_FragColor = vec4(tex.rgb, tex.a);
}
