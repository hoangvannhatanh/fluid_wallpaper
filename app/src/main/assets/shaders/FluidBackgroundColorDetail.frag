precision mediump float;

varying vec2 varyTexCoord;
uniform sampler2D texFluid;

uniform sampler2D texDetailUV0;
uniform sampler2D texDetailUV1;
uniform sampler2D texDetailUV2;
uniform sampler2D texDetailDef0_8;
uniform sampler2D texDetail;

uniform vec3 invColor;

uniform float tmp;
uniform vec3 uUVInterp0;
uniform vec3 uUVInterp1;



uniform vec3 color0;
uniform vec3 color1;
uniform vec3 color2;

uniform vec3 colorActiveMask;
uniform vec3 backgrColor;

float calcDetailLayerCoeff(vec3 def0, vec3 weights0)
{
	return clamp( 1.5 - 2.5 * dot(def0, weights0), 0.0, 1.0 );
}

void main()
{
	vec3 intens = texture2D(texFluid, varyTexCoord).rgb;
	
	vec4 detailCoords0 = texture2D(texDetailUV0, varyTexCoord);
	vec4 detailCoords1 = texture2D(texDetailUV1, varyTexCoord);
	vec4 detailCoords2 = texture2D(texDetailUV2, varyTexCoord);
	vec3 detail0 = vec3(
		texture2D(texDetail, detailCoords0.xy).a,
		texture2D(texDetail, detailCoords1.xy).a,
		texture2D(texDetail, detailCoords2.xy).a);
	vec3 detail1 = vec3(
		texture2D(texDetail, detailCoords0.zw).a,
		texture2D(texDetail, detailCoords1.zw).a,
		texture2D(texDetail, detailCoords2.zw).a);
		
	vec3 detailDef0_8 = texture2D(texDetailDef0_8, varyTexCoord).xyz;
	
	float detailLayer0 = dot(detail0, uUVInterp0);
	float detailLayer1 = dot(detail1, uUVInterp1);

	float C = calcDetailLayerCoeff(detailDef0_8, uUVInterp0);
	
	float detail = mix(detailLayer1, detailLayer0, C);
	intens *= (1.0-detail);
	
	intens = smoothstep(0.0, 1.0, intens);
	vec3 accum = vec3(0.0);
	
	accum += color0 * intens.r * colorActiveMask.r;
	accum += color1 * intens.g * colorActiveMask.g;
	accum += color2 * intens.b * colorActiveMask.b;
	
	float paintAmount = dot(intens, colorActiveMask);
	accum /= max(paintAmount, 1.0);
	
	gl_FragColor.rgb = accum + (1.0 - min(paintAmount, 1.0)) * backgrColor;
	gl_FragColor.a = 1.0;
}
