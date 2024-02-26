precision mediump float;

uniform sampler2D texImage;
uniform float uColorChange;

varying highp vec2 varyTexCoord;
varying highp vec2 varyTexCoordXY;

void main()
{
	vec4 sum = texture2D(texImage, varyTexCoord);
	
	vec4 sampleMirrorXY = texture2D(texImage, varyTexCoordXY);
	sum += mix(sampleMirrorXY, sampleMirrorXY.bgra, uColorChange);

	gl_FragColor = sum;
}
