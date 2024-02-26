precision mediump float;

varying vec2 vPos;
uniform sampler2D sTexture;

#define NUM_BALLS 16

uniform vec2 blobPos[NUM_BALLS];
uniform vec3 blobColor[NUM_BALLS];

void main()
{
	float sumStr = 0.0;
	float str = 0.0;
	
	vec3 sumCol = vec3(0.0);
	
	for(int i = 0; i < 12; i++)
	{
		vec2 blobToPos = vPos - blobPos[i];
		float distSq = dot(blobToPos, blobToPos);
		distSq = max(0.0001, distSq);
		str = 1.0 / distSq;
		sumStr += str;
		sumCol += blobColor[i] * str;
	}
	
	vec3 col = vec3(0.0);
	//vec3 col = sumStr > 50.0 ? vec3(1.0, 0.0, 0.0) : vec3(0.0);
	col = (1.0 - smoothstep(0.0, 8.0, abs(50.0 - sumStr))) * sumCol / sumStr;
	col = sumStr > 50.0 ? sumCol / sumStr : col;
	
	gl_FragColor = vec4(col, 1.0);
	
	
	
	//gl_FragColor = texture2D(sTexture, vPos);
}
