precision mediump float;

uniform sampler2D texImage;
uniform sampler2D texVel;
uniform vec2 uMotionScale;

varying highp vec2 varyTexCoord;

void main()
{
	vec2 vel = texture2D(texVel, varyTexCoord).rg;
	vel *= uMotionScale;
	//vel = vec2(vel.y, -vel.x);
	
	vec4 centerSample = texture2D(texImage, varyTexCoord);

	vec4 sum = centerSample;
	//sum += texture2D(texImage, varyTexCoord + vel);
	//sum += texture2D(texImage, varyTexCoord + vel*2.0);
	//sum += texture2D(texImage, varyTexCoord + vel*3.0);
	//sum += texture2D(texImage, varyTexCoord + vel*4.0);
	//sum += texture2D(texImage, varyTexCoord - vel);
	//sum += texture2D(texImage, varyTexCoord - vel*2.0);
	//sum += texture2D(texImage, varyTexCoord - vel*3.0);
	//sum += texture2D(texImage, varyTexCoord - vel*4.0);
	
	//sum += 0.25 * texture2D(texImage, (varyTexCoord - vec2(0.5)) * 0.75 + vec2(0.5));
	//sum += 0.0625 * texture2D(texImage, (varyTexCoord - vec2(0.5)) * 0.5 + vec2(0.5));
	//gl_FragColor = sum / 1.5;
	
	//sum += texture2D(texImage, vec2(varyTexCoord.x, 1.0 - varyTexCoord.y)).brga;
	//sum += texture2D(texImage, vec2(1.0 - varyTexCoord.x, varyTexCoord.y)).gbra;
	//sum += texture2D(texImage, vec2(1.0 - varyTexCoord.x, 1.0 - varyTexCoord.y));
	
	
	vec3 ker00 = texture2D(texImage, varyTexCoord + vec2(-1.0 / 1600.0, -1.0 / 900.0)).rgb;
	vec3 ker01 = texture2D(texImage, varyTexCoord + vec2( 0.0,          -1.0 / 900.0)).rgb;
	vec3 ker02 = texture2D(texImage, varyTexCoord + vec2( 1.0 / 1600.0, -1.0 / 900.0)).rgb;
	vec3 ker10 = texture2D(texImage, varyTexCoord + vec2(-1.0 / 1600.0,  0.0        )).rgb;
	vec3 ker11 = sum.rgb;
	vec3 ker12 = texture2D(texImage, varyTexCoord + vec2( 1.0 / 1600.0,  0.0        )).rgb;
	vec3 ker20 = texture2D(texImage, varyTexCoord + vec2(-1.0 / 1600.0,  1.0 / 900.0)).rgb;
	vec3 ker21 = texture2D(texImage, varyTexCoord + vec2( 0.0,           1.0 / 900.0)).rgb;
	vec3 ker22 = texture2D(texImage, varyTexCoord + vec2( 1.0 / 1600.0,  1.0 / 900.0)).rgb;
	
	float avg =  dot(vec3(1.0), (ker11 + ker01 + ker10 + ker12 + ker21)) / 5.0;
	
	vec3 lapl = avg > 0.05 ? (4 * ker11  - ker01 - ker10 - ker12  - ker21) / avg : vec3(0.0) ;
	lapl = abs(lapl);
	gl_FragColor = sum * (0.1 + 0.9 * 8.0 * max(lapl.x, max(lapl.y, lapl.z)));
	
	//vec3 sobelX = ker00 + 2.0 * ker10 + ker20 - ker02 - 2.0 * ker12 - ker22;
	//vec3 sobelY = ker00 + 2.0 * ker01 + ker02 - ker20 - 2.0 * ker21 - ker22;
	//float sobelStr = dot(vec3(1.0), (sqrt(sobelX*sobelX)) + (sqrt(sobelY*sobelY)));
	//gl_FragColor = sum * min(sobelStr, 1.0);
	
	
	//float amount = max(sum.x, max(sum.y, sum.z));
	//gl_FragColor = amount > 0.1 ? (sum * 0.2  / (amount*amount)) - 0.2 : 20.0 * sum;
	
	//sum.rgb = amount > 0.05 ? clamp(-50.0 * (amount - 0.2) * (amount - 0.2) + 1.0, 0.0, 1.0) * normalize(sum.rgb) : vec3(0.0);
	//sum.a = dot(vec3(1.0), abs(sum.xyz));
	//gl_FragColor = sum;
}
