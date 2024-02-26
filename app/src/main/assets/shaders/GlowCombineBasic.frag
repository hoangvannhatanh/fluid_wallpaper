precision mediump float;

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

varying vec2 varyTexCoord;

vec3 evalHdrSample(vec4 sample)
{
	vec3 color = sample.rgb;// * (max(sample.a - 0.5, 0.0)*2.0 + 1.0); // Commented out - decoding of a HDR "exponent" (disabled in store_glow_input)
	// vec3 color = sample.rgb * 3.0;
	return color;
}

void main()
{
	vec3 val0 = evalHdrSample(texture2D(texLev0, varyTexCoord));
	vec3 val1 = evalHdrSample(texture2D(texLev1, varyTexCoord));
	vec3 val2 = evalHdrSample(texture2D(texLev2, varyTexCoord));
	vec3 val3 = evalHdrSample(texture2D(texLev3, varyTexCoord));
	vec3 val4 = evalHdrSample(texture2D(texLev4, varyTexCoord));
	vec3 final = uStrength * (uStr0 * val0 + uStr1 * val1 + uStr2 * val2 + uStr3 * val3 + uStr4 * val4);

	gl_FragColor = vec4(final, 1.0);
}
