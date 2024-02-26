precision mediump float;

uniform sampler2D texTrails;
uniform float uFadeCoeff;
varying vec2 varyTexCoord;

void main()
{
	gl_FragColor = uFadeCoeff * texture2D(texTrails, varyTexCoord);
}
