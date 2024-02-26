precision mediump float;

uniform sampler2D tex;
uniform vec2 uInputPixelSize;
varying vec2 varyTexCoord;

// Brightness function
float Brightness(vec3 c)
{
	return max(max(c.r, c.g), c.b);
}


void main()
{
	vec4 offsets = uInputPixelSize.xyxy * 1.0 * vec4(-1.0, -1.0, 1.0, 1.0);
	vec4 s1 = texture2D(tex, varyTexCoord + offsets.xy);
	vec4 s2 = texture2D(tex, varyTexCoord + offsets.zy);
	vec4 s3 = texture2D(tex, varyTexCoord + offsets.xw);
	vec4 s4 = texture2D(tex, varyTexCoord + offsets.zw);
	
	/* // Not sure why I was not using it
	// Karis's luma weighted average
	float s1w = 1.0 / (Brightness(s1) + 1.0);
	float s2w = 1.0 / (Brightness(s2) + 1.0);
	float s3w = 1.0 / (Brightness(s3) + 1.0);
	float s4w = 1.0 / (Brightness(s4) + 1.0);
	float one_div_wsum = 1.0 / (s1w + s2w + s3w + s4w);

	//vec3 final = (s1 * s1w + s2 * s2w + s3 * s3w + s4 * s4w) * one_div_wsum;
	*/
	vec4 final = (s1 + s2 + s3 + s4) * 0.25;
	
	gl_FragColor = final;
}
