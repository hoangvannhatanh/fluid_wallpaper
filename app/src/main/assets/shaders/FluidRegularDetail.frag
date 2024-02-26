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

vec3 restoreLowPaintColor(vec4 tex)
{
	return tex.rgb * (4.0 * tex.a + 1.0);
}

vec3 evalColor(vec4 tex)
{
	return tex.rgb;
}

float calcDetailLayerCoeff(vec3 def0, vec3 weights0)
{
	return clamp( 1.5 - 2.5 * dot(def0, weights0), 0.0, 1.0 );
}

void main()
{
	vec3 color = restoreLowPaintColor(texture2D(texFluid, varyTexCoord));
	
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
	color *= (1.0-detail);
	color = tmp * vec3(color) + (1.0 - tmp) * detail;
	
	color = smoothstep(0.0, 1.0, color);
	
	
	//color = tmp * vec3(C) + (1.0 - tmp) * color;
	
	//color.r = pow(color.r, 2.2);
	//color.g = pow(color.g, 2.2);
	//color.b = pow(color.b, 2.2);
	gl_FragColor = vec4(color, 1.0);
}
