precision mediump float;

uniform sampler2D texShadow;
uniform sampler2D texFluidInput;
uniform sampler2D texLev0;
uniform sampler2D texLev1;
uniform sampler2D texLev2;
uniform sampler2D texLev3;
uniform sampler2D texLev4;

uniform float uStr0;
uniform float uStr1;
uniform float uStr2;
uniform float uStr3;
uniform float uStr4;

uniform float uStrength;
//uniform float uShadowActive;

//uniform float uPaintSelfShadow;

varying vec2 varyTexCoord;

vec3 evalHdrSample(vec4 sample)
{
	vec3 color = sample.rgb;// * (max(sample.a - 0.5, 0.0)*2.0 + 1.0); // Commented out - decoding of a HDR "exponent" (disabled in store_glow_input)
	// vec3 color = sample.rgb * 3.0;
	return color;
}

vec3 linearToGamma (vec3 color) {
        color = max(color, vec3(0));
        return max(pow(color, vec3(0.416666667)), vec3(0));
    }

void main()
{
	vec3 val0 = evalHdrSample(texture2D(texLev0, varyTexCoord));
	vec3 val1 = evalHdrSample(texture2D(texLev1, varyTexCoord));
	vec3 val2 = evalHdrSample(texture2D(texLev2, varyTexCoord));
	vec3 val3 = evalHdrSample(texture2D(texLev3, varyTexCoord));
	vec3 val4 = evalHdrSample(texture2D(texLev4, varyTexCoord));
	vec3 final = uStrength * (uStr0 * val0 + uStr1 * val1 + uStr2 * val2 + uStr3 * val3 + uStr4 * val4);
	//float len = length(final);
	//float newLen = min(len, 1.5);
	//final *= newLen / len;
	
	/*
	final = clamp(final, 0.0, 2.0);
	float maxComp = max(final.r, max(final.g, final.b));
	vec3 normed = final / max(maxComp, 1.0);
	float shadow = clamp(texture2D(texShadow, varyTexCoord).r, 0.2, 1.0);
	
	float fluidAmount = texture2D(texFluidInput, varyTexCoord).a;
	float selfShadow = mix(1.0 - fluidAmount, 1.0, uPaintSelfShadow);
	shadow = 1.0 - selfShadow *  uShadowActive*(1.0 - shadow);
	//shadow = mix(1.0, shadow, uShadowActive);
	
	// 2. inverse shadow, very nice effect:
	// float shadow = clamp(texture2D(texShadow, varyTexCoord).r, 0.0, 1.0);
	// gl_FragColor = vec4(final * (1.0 - shadow), texture2D(texShadow, varyTexCoord).r);
	
	//gl_FragColor = vec4(final * shadow, texture2D(texShadow, varyTexCoord).r);
	gl_FragColor = vec4(final - normed * (1.0 - shadow), texture2D(texShadow, varyTexCoord).r);
	//gl_FragColor = vec4(final * shadow, texture2D(texShadow, varyTexCoord).r);
	//*/
	
	//final = linearToGamma(final);
	 gl_FragColor = vec4(sqrt(final * 1.0/ 1.8), texture2D(texShadow, varyTexCoord).r);
}
