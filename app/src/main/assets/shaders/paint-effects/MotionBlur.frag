precision mediump float;

uniform sampler2D texImage;
uniform sampler2D texVel;
uniform vec2 uMotionScale;
uniform vec2 uTexelSize;
uniform float uNoiseSwitch;

varying highp vec2 varyTexCoord;

void main()
{
	vec4 vectorFieldsSample = texture2D(texVel, varyTexCoord);
	vec2 vel = mix(vectorFieldsSample.xy, vectorFieldsSample.zw, uNoiseSwitch);
	//vec2 vel = texture2D(texVel, varyTexCoord).rg;
	vel *= uMotionScale;
	//vel = vec2(vel.y, -vel.x);
	
	//vel = clamp(vel, -uTexelSize * 12.0, uTexelSize * 12.0) / 6.0;
	
	vec4 centerSample = texture2D(texImage, varyTexCoord);

	vec4 sum = centerSample;
	sum += texture2D(texImage, varyTexCoord + vel);
	sum += texture2D(texImage, varyTexCoord + vel*2.0);
	//sum += texture2D(texImage, varyTexCoord + vel*3.0);
	//sum += texture2D(texImage, varyTexCoord + vel*4.0);
	sum += texture2D(texImage, varyTexCoord - vel);
	sum += texture2D(texImage, varyTexCoord - vel*2.0);
	//sum += texture2D(texImage, varyTexCoord - vel*3.0);
	//sum += texture2D(texImage, varyTexCoord - vel*4.0);

	gl_FragColor = sum / 5.0;
	
	//gl_FragColor = vec4((texture2D(texVel, varyTexCoord).rgb) * 0.06 + vec3(0.5), 1.0);
}
