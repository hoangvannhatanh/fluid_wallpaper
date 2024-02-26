precision mediump float;

varying vec2 varyTexCoord;

varying highp vec4 varyDetailUV0;
varying highp vec4 varyDetailUV1;
varying highp vec4 varyDetailUV2;

uniform sampler2D texDetailDef0_8;
uniform sampler2D texDetail;

uniform float tmp;
uniform vec3 uUVInterp0;
uniform vec3 uUVInterp1;

float calcDetailLayerCoeff(vec3 def0, vec3 weights0)
{
	return clamp( 1.5 - 2.5 * dot(def0, weights0), 0.0, 1.0 );
}

void main()
{
	vec3 detail0 = vec3(
		texture2D(texDetail, varyDetailUV0.xy).a,
		texture2D(texDetail, varyDetailUV1.xy).a,
		texture2D(texDetail, varyDetailUV2.xy).a);
	vec3 detail1 = vec3(
		texture2D(texDetail, varyDetailUV0.zw).a,
		texture2D(texDetail, varyDetailUV1.zw).a,
		texture2D(texDetail, varyDetailUV2.zw).a);
		
	vec3 detailDef0_8 = texture2D(texDetailDef0_8, varyTexCoord).xyz;
	
	float detailLayer0 = dot(detail0, uUVInterp0);
	float detailLayer1 = dot(detail1, uUVInterp1);

	float C = calcDetailLayerCoeff(detailDef0_8, uUVInterp0);
	
	float detail = mix(detailLayer1, detailLayer0, C);
	
	gl_FragColor = vec4(detail);
}
