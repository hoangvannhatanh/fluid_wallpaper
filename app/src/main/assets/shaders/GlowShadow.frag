precision mediump float;

//uniform sampler2D texGlow;
uniform sampler2D texFluid;
varying highp vec2 varyTexCoord;
uniform vec2 uPixelSize;
uniform vec2 uAspectVecNormalized;
uniform vec2 uLightPos;
uniform float uIntensityInv;
uniform float uFalloffLength;
uniform float uFalloffCurveOffset;
uniform float uIntensityMod;

void main()
{
	highp vec2 offset = varyTexCoord - uLightPos;
	highp float dist = length(offset);
	highp vec2 dir = -offset/(dist + 1e-6);
	
	// It's important to modify the offset by the aspect ratio before taking the length.
	// I have a feeling that these transformations back-and-forth in spaces can be simplified, but I struggled with it for a couple of hours,
	// and I'm leaving it for another time...
	offset *= uAspectVecNormalized;
	highp float distPixelSpace = length(offset);
	highp vec2 dirPixelSpace = -offset/(distPixelSpace + 1e-6);

	// *100 followed by *0.01 is to get the values to a more reasonable mediump range. Otherwise, the values get too small when squaring (which is a part of the process of taking length())
	// to be properly represented. Another way is to change the shader's precision to highp.
	// Related to the comment above, it is particularly important to use dirPixelSpace here, instead of dir, otherwise stepSize is ok when dir is horizontal or vertical,
	// but not ok when dir points at an angle, making the shadow not circular. Replace and see.
	float stepSize = length(dirPixelSpace * uPixelSize * 100.0) * 0.01;

	
	// improve glow level falloffs (more intermediate levels?)
	float accum = 0.0;
	dist /= stepSize;
	//float localDensity = texture2D(texFluid, varyTexCoord).a; // to test a more "3D" shadow, uncomment lines 37, 42, 43 AND enable floating-point for the shadow source texture
	for(float step = 0.0; step <= uFalloffLength; step += 1.0)
	{
		float stepDist = step * stepSize;
		float stepWeight = clamp(dist - step, 0.0, 1.0);
		//float stepDensity = texture2D(texFluid, varyTexCoord + dir * stepDist).a;
		//float density = min(2.0 * clamp(stepDensity * 0.7 + (stepDensity - localDensity) * 0.3, 0.0, 1.0), 0.35); // readout of the encoding in store_hdr
		
		float density = min(2.0 * texture2D(texFluid, varyTexCoord + dir * stepDist).a, 0.35); // readout of the encoding in store_hdr
		
		//density *= uIntensityMod; 
		//if(stepDensity > localDensity)
		accum += stepWeight * density / (step * uIntensityInv * uIntensityMod + uFalloffCurveOffset); // exp(-step * 0.1) / uIntensityInv;
	}
	
	accum *= uIntensityMod;
	
	// fluid covers light
	/*vec3 lightPosSample = texture2D(texFluid, uLightPos + vec2(uPixelSize.x, uPixelSize.y)).rgb;
	lightPosSample += texture2D(texFluid, uLightPos + vec2(-uPixelSize.x, uPixelSize.y)).rgb;
	lightPosSample += texture2D(texFluid, uLightPos + vec2(uPixelSize.x, -uPixelSize.y)).rgb;
	lightPosSample += texture2D(texFluid, uLightPos + vec2(-uPixelSize.x, -uPixelSize.y)).rgb;
	lightPosSample *= 0.25;
	accum -= 0.25 * clamp(max(lightPosSample.x, max(lightPosSample.x, lightPosSample.z)), 0.0, 1.0);*/
	
	//accum = clamp(accum * uFalloffLength / weightsAccum, 0.0, 1.0);
	//accum = clamp(accum * (1.0 + min(0.5, 1.0 / (distPixelSpace * 100.0))), 0.0, 1.0);
	float shadow = clamp(accum * 0.18, 0.0, 0.8);//0.4 + 0.6 * (1.0 - accum);
	
	gl_FragColor = vec4(shadow, 0.0, 0.0, 1.0);
}
