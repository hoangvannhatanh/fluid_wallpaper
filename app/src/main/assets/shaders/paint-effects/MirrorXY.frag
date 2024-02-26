precision mediump float;

uniform sampler2D texImage;
uniform float uColorChange;

varying highp vec2 varyTexCoord;
varying highp vec2 varyTexCoordX;
varying highp vec2 varyTexCoordY;
varying highp vec2 varyTexCoordXY;

void main()
{
	vec4 sum = texture2D(texImage, varyTexCoord);
	
	//vec4 sampleMirrorXY = texture2D(texImage, vec2(1.0 - varyTexCoord.x, 1.0 - varyTexCoord.y));
	//vec4 sampleMirrorX = texture2D(texImage, vec2(1.0 - varyTexCoord.x, varyTexCoord.y));
	//vec4 sampleMirrorY = texture2D(texImage, vec2(varyTexCoord.x, 1.0 - varyTexCoord.y));
	
	vec4 sampleMirrorXY = texture2D(texImage, varyTexCoordXY);
	vec4 sampleMirrorX = texture2D(texImage, varyTexCoordX);
	vec4 sampleMirrorY = texture2D(texImage, varyTexCoordY);
	
	sum += mix(sampleMirrorXY, sampleMirrorXY.bgra, uColorChange);
	sum += mix(sampleMirrorX, sampleMirrorX.brga, uColorChange);
	sum += mix(sampleMirrorY, sampleMirrorY.gbra, uColorChange);

	gl_FragColor = sum;
}
