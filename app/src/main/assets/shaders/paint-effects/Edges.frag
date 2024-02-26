precision mediump float;

uniform sampler2D texImage;
uniform float uIntensity;
uniform vec2 uTexelSize;

varying highp vec2 varyTexCoord;

void main()
{
	vec4 centerSample = texture2D(texImage, varyTexCoord);	
	
	
	vec3 ker01 = texture2D(texImage, varyTexCoord + vec2( 0.0,          -uTexelSize.y)).rgb;
	vec3 ker10 = texture2D(texImage, varyTexCoord + vec2(-uTexelSize.x,  0.0        )).rgb;
	vec3 ker11 = centerSample.rgb;
	vec3 ker12 = texture2D(texImage, varyTexCoord + vec2( uTexelSize.x,  0.0        )).rgb;
	vec3 ker21 = texture2D(texImage, varyTexCoord + vec2( 0.0,           uTexelSize.y)).rgb;
	
	vec3 ker00 = texture2D(texImage, varyTexCoord + vec2(-uTexelSize.x, -uTexelSize.y)).rgb;
	vec3 ker02 = texture2D(texImage, varyTexCoord + vec2( uTexelSize.x, -uTexelSize.y)).rgb;
	vec3 ker20 = texture2D(texImage, varyTexCoord + vec2(-uTexelSize.x,  uTexelSize.y)).rgb;
	vec3 ker22 = texture2D(texImage, varyTexCoord + vec2( uTexelSize.x,  uTexelSize.y)).rgb;
	
	float avg =  dot(vec3(1.0), (ker11 + ker01 + ker10 + ker12 + ker21 + ker00 + ker02 + ker20 + ker22)) / 9.0;
	
	// The simplest regular Laplacian is divided by the average value of the kernel samples, effectively creating a "relative Laplacian",
	// which suits here better, because it highlights edges also when the fluid density is smaller in magnitude.
	//float avg =  dot(vec3(1.0), (ker11 + ker01 + ker10 + ker12 + ker21)) / 5.0;
	//vec3 lapl = abs(avg) > 0.01 ? (4 * ker11  - ker01 - ker10 - ker12  - ker21) / avg : vec3(0.0) ;
	
	//vec3 lapl = abs(avg) > 0.01 ? (8 * ker11  - ker01 - ker10 - ker12  - ker21 - ker00 - ker02 - ker20 - ker22) / avg : vec3(0.0) ;
	//lapl = abs(lapl);
	//gl_FragColor = centerSample * mix(1.0, 8.0 * max(lapl.x, max(lapl.y, lapl.z)), uIntensity);
	
	
	vec3 sobelX = ker00 + 2.0 * ker10 + ker20 - ker02 - 2.0 * ker12 - ker22;
	vec3 sobelY = ker00 + 2.0 * ker01 + ker02 - ker20 - 2.0 * ker21 - ker22;
	float sobelStr = abs(avg) > 0.01 ? dot(vec3(1.0), (sqrt(sobelX*sobelX)) + (sqrt(sobelY*sobelY))) / abs(avg) : 0.0;
	//float sobelStr = abs(avg) > 0.01 ? dot(vec3(1.0), abs(sobelX) + abs(sobelY)) / abs(avg) : 0.0;
	gl_FragColor = centerSample * mix(1.0, sobelStr, uIntensity);
}
