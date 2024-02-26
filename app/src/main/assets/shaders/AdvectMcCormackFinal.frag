precision highp float;

uniform sampler2D texPaintOld;
uniform sampler2D texPaintAdvected;
uniform sampler2D texPaintBackAdvected;
uniform sampler2D texVelocity;
uniform vec2 uVelScale;
uniform vec2 uTexelSize;

varying highp vec2 varyTexCoord;

void main()
{
	highp vec2 samplePos = varyTexCoord - texture2D(texVelocity, varyTexCoord).rg * uVelScale;
	vec4 result = texture2D(texPaintAdvected, varyTexCoord) + 0.5 * (texture2D(texPaintOld, varyTexCoord) - texture2D(texPaintBackAdvected, varyTexCoord));
	
	vec2 samplePosInt = samplePos / uTexelSize;
	vec2 cellCorner = floor(samplePosInt);
	vec4 sample00 = texture2D(texPaintOld, cellCorner * uTexelSize);
	vec4 sample10 = texture2D(texPaintOld, (cellCorner + vec2(1.0, 0.0)) * uTexelSize);
	vec4 sample01 = texture2D(texPaintOld, (cellCorner + vec2(0.0, 1.0)) * uTexelSize);
	vec4 sample11 = texture2D(texPaintOld, (cellCorner + vec2(1.0, 1.0)) * uTexelSize);
	
	vec4 minVal = min(min(min(sample00, sample01), sample10), sample11);
	vec4 maxVal = max(max(max(sample00, sample01), sample10), sample11);
	gl_FragColor = clamp(result, minVal, maxVal);
	
	// Input addition must be separated for this to work!
}
