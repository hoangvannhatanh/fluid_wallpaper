precision mediump float;

varying vec2 varyTexCoord;
uniform sampler2D texFluid;

uniform vec3 invColor;

uniform vec3 color0;
uniform vec3 color1;
uniform vec3 color2;

uniform vec3 colorActiveMask;
uniform vec3 backgrColor;

void main()
{
	vec3 intens = texture2D(texFluid, varyTexCoord).rgb;
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
