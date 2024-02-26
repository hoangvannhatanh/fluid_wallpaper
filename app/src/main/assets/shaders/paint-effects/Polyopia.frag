precision mediump float;

uniform sampler2D texImage;
uniform float uIntensity;
uniform float uColorChange;

varying highp vec2 varyTexCoord;

void main()
{
	vec4 sum = texture2D(texImage, varyTexCoord);
	vec4 sample1 = texture2D(texImage, (varyTexCoord - vec2(0.5)) * (1.0 - uIntensity) + vec2(0.5));
	vec4 sample2 = texture2D(texImage, (varyTexCoord - vec2(0.5)) * (1.0 - uIntensity)*(1.0 - uIntensity) + vec2(0.5));
	sum += 0.2 * mix(sample1, sample1.gbra, uColorChange);
	sum += 0.04 * mix(sample2, sample2.brga, uColorChange);
	gl_FragColor = sum;
}
