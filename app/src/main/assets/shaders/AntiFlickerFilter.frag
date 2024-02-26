precision mediump float;

uniform sampler2D tex;
varying vec2 varyTexCoord;
uniform vec2 uPixelSize;

    // 3-tap median filter
vec3 median(vec3 a, vec3 b, vec3 c)
{
	return a + b + c - min(min(a, b), c) - max(max(a, b), c);
}

void main()
{
	vec3 s0 = texture2D(tex, varyTexCoord).rgb;
	vec3 s1 = texture2D(tex, varyTexCoord + vec2(uPixelSize.x, 0.0)).rgb;
	vec3 s2 = texture2D(tex, varyTexCoord - vec2(uPixelSize.x, 0.0)).rgb;
	vec3 s3 = texture2D(tex, varyTexCoord + vec2(0.0, uPixelSize.y)).rgb;
	vec3 s4 = texture2D(tex, varyTexCoord - vec2(0.0, uPixelSize.y)).rgb;
	gl_FragColor = vec4(median(median(s0, s1, s2), s3, s4), 1.0);
}
