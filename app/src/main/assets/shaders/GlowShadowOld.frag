precision mediump float;

//uniform sampler2D texGlow;
uniform sampler2D texFluid;
varying highp vec2 varyTexCoord;
uniform vec2 uPixelSize;
uniform vec2 uAspectVecNormalized;
uniform vec2 uLightPos;
uniform float uIntensityInv;
uniform float uFalloffLength;
uniform float uIntensityMod;

/*
vec2 sampleDir(vec2 texCoord)
{
	vec3 L = texture2D(texGlow, texCoord + vec2(-uPixelSize.x, 0.0)).rgb;
	vec3 R = texture2D(texGlow, texCoord + vec2(uPixelSize.x, 0.0)).rgb;
	vec3 T = texture2D(texGlow, texCoord + vec2(0.0, -uPixelSize.y)).rgb;
	vec3 B = texture2D(texGlow, texCoord + vec2(0.0, uPixelSize.y)).rgb;
	vec2 grad = vec2(length(R)-length(L), length(T) - length(B));
	
	return grad;
}
*/

void main()
{
	/*vec3 glowSample = texture2D(texGlow, varyTexCoord).rgb;
	
	vec2 d0 = sampleDir(varyTexCoord);
	vec2 d1 = sampleDir(varyTexCoord + uPixelSize * vec2(-1.0, -1.0));
	vec2 d2 = sampleDir(varyTexCoord + uPixelSize * vec2(-1.0, 0.0));
	vec2 d3 = sampleDir(varyTexCoord + uPixelSize * vec2(-1.0, 1.0));
	vec2 d4 = sampleDir(varyTexCoord + uPixelSize * vec2(0.0, -1.0));
	vec2 d5 = sampleDir(varyTexCoord + uPixelSize * vec2(0.0, 1.0));
	vec2 d6 = sampleDir(varyTexCoord + uPixelSize * vec2(1.0, -1.0));
	vec2 d7 = sampleDir(varyTexCoord + uPixelSize * vec2(1.0, 0.0));
	vec2 d8 = sampleDir(varyTexCoord + uPixelSize * vec2(1.0, 1.0));
	
	vec2 grad = (d0 + d1 + d2 + d3 + d4 + d5 + d6 + d7 + d8) / 9.0;
	
	vec2 dir = normalize(grad);
	dir.y *= -1.0;
	*/
	
	highp vec2 offset = varyTexCoord - uLightPos;
	highp float dist = length(offset);
	highp vec2 dir = -offset/(dist + 1e-6);
	
	// It's important to modify the offset by the aspect ratio before taking the length.
	// I have a feeling that these transformations back-and-forth in spaces can be simplified, but I struggled with it for a couple of hours,
	// and I'm leaving it for another time...
	offset *= uAspectVecNormalized;
	highp float distPixelSpace = length(offset);
	highp vec2 dirPixelSpace = -offset/(distPixelSpace + 1e-6);
	
	float distValid = 1.0;// clamp(distPixelSpace * 100.0, 0.0, 1.0);
	
	// *100 followed by *0.01 is to get the values to a more reasonable mediump range. Otherwise, the values get too small when squaring (which is a part of the process of taking length())
	// to be properly represented. Another way is to change the shader's precision to highp.
	// Related to the comment above, it is particularly important to use dirPixelSpace here, instead of dir, otherwise stepSize is ok when dir is horizontal or vertical,
	// but not ok when dir points at an angle, making the shadow not circular. Replace and see.
	float stepSize = length(dirPixelSpace * uPixelSize * 100.0) * 0.01;
	
	// improve glow level falloffs (more intermediate levels?)
	float accum = 0.0;
	float weightsAccum = 0.0;
	for(float step = 0.0; step <= uFalloffLength; step += 1.0)
	{
		float stepDist = step * stepSize;
		float stepWeight = clamp(dist - stepDist, 0.0, stepSize) / stepSize;
		weightsAccum += stepWeight;
		//vec3 fluidSample = texture2D(texFluid, varyTexCoord + dir * stepDist).rgb;
		//float density = max(fluidSample.x, max(fluidSample.y, fluidSample.z));
		float density = min(1.4 * (texture2D(texFluid, varyTexCoord + dir * stepDist).a), 0.4); // readout of the encoding in store_hdr
		//density = min(density, 0.5); // Earlier, this min() was simply by design of the shadow algorithm - it looked good if the density of fluid for the purpose of shadowing was already maxxed out at 0.5 (altough this exact value was different before). Now, it is not needed because only the 0-0.5 range is passed by the texture decoding in the previous line, due to additionally encoding a HDR "exponent" in the alpha channel
		density *= uIntensityMod; 
		//float density = max(fluidSample.x, max(fluidSample.y, fluidSample.z)) * 40.0;
		//density = min(density, 0.25);
		accum += distValid * stepWeight * density * 0.03 * 6.0 / (step * uIntensityInv * uIntensityMod + 1.0); // exp(-step * 0.1) / uIntensityInv;
	}
	
	// fluid covers light
	/*vec3 lightPosSample = texture2D(texFluid, uLightPos + vec2(uPixelSize.x, uPixelSize.y)).rgb;
	lightPosSample += texture2D(texFluid, uLightPos + vec2(-uPixelSize.x, uPixelSize.y)).rgb;
	lightPosSample += texture2D(texFluid, uLightPos + vec2(uPixelSize.x, -uPixelSize.y)).rgb;
	lightPosSample += texture2D(texFluid, uLightPos + vec2(-uPixelSize.x, -uPixelSize.y)).rgb;
	lightPosSample *= 0.25;
	accum -= 0.25 * clamp(max(lightPosSample.x, max(lightPosSample.x, lightPosSample.z)), 0.0, 1.0);*/
	
	//accum = clamp(accum * uFalloffLength / weightsAccum, 0.0, 1.0);
	//accum = clamp(accum * (1.0 + min(0.5, 1.0 / (distPixelSpace * 100.0))), 0.0, 1.0);
	accum = clamp(accum, 0.0, 1.0);
	float shadow = clamp(accum, 0.0, 0.7);//0.4 + 0.6 * (1.0 - accum);
	
	gl_FragColor = vec4(shadow, 0.0, 0.0, 1.0);
}
