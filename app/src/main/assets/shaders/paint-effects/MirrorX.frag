precision mediump float;

uniform sampler2D texImage;
uniform float uColorChange;

varying highp vec2 varyTexCoord;
varying highp vec2 varyTexCoordX;

void main()
{
	vec4 sum = texture2D(texImage, varyTexCoord);

	vec4 sampleMirrorX = texture2D(texImage, varyTexCoordX);
	sum += mix(sampleMirrorX, sampleMirrorX.brga, uColorChange);

	gl_FragColor = sum;
}
