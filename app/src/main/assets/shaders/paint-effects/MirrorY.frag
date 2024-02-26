precision mediump float;

uniform sampler2D texImage;
uniform float uColorChange;

varying highp vec2 varyTexCoord;
varying highp vec2 varyTexCoordY;

void main()
{
	vec4 sum = texture2D(texImage, varyTexCoord);

	vec4 sampleMirrorY = texture2D(texImage, varyTexCoordY);
	sum += mix(sampleMirrorY, sampleMirrorY.gbra, uColorChange);

	gl_FragColor = sum;
}
