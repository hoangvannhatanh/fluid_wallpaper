precision mediump float;

varying vec2 varyTexCoord;
uniform sampler2D texPaint0;
uniform sampler2D texPaint1;

float DecodeFloatRG( vec2 rg )
{
	float range = 12.75;
    return range * dot( rg, vec2(1.0, 1.0/255.0) );
}

void main()
{
	vec4 sample0 = texture2D(texPaint0, varyTexCoord);
	vec4 sample1 = texture2D(texPaint1, varyTexCoord);
	
	//gl_FragColor = clamp( vec4(DecodeFloatRG(sample0.rg), DecodeFloatRG(sample0.ba), DecodeFloatRG(sample1.rg), 1.0), 0.0, 1.0 );
	
	vec3 color = vec3(DecodeFloatRG(sample0.rg), DecodeFloatRG(sample0.ba), DecodeFloatRG(sample1.rg));
	float maxComp = max(color.r, max(color.g, color.b));
	float magn = clamp( (maxComp - 1.0) * 0.20, 0.0, 1.0);
	color = color / max(maxComp, 1.0);
	gl_FragColor = vec4( clamp(color, 0.0, 1.0 ), magn);
	
}
