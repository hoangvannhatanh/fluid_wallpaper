precision mediump float;

uniform sampler2D texPaint;
uniform vec2 uSampleOffset;

varying vec2 varyTexCoord;


void main()
{
	vec4 sample = texture2D(texPaint, varyTexCoord + uSampleOffset);
	gl_FragColor = sample;
}
