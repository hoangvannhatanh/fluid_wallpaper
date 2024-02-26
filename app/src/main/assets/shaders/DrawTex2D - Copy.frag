precision mediump float;

varying vec2 varyTexCoord;
uniform sampler2D texture;

void main()
{
	//gl_FragColor = texture2D(texture, varyTexCoord);
	
  vec2 onePixel = vec2(1.0 / 64.0, 1.0 / 32.0);
 
  // 3
  vec2 texCoord = varyTexCoord;
 
  // 4
  vec4 color;
  color.rgb = vec3(0.5);
  color -= texture2D(texture, texCoord - onePixel) * 5.0;
  color += texture2D(texture, texCoord + onePixel) * 5.0;
  // 5
  color.rgb = vec3((color.r + color.g + color.b) / 3.0);
  gl_FragColor = texture2D(texture, varyTexCoord) + 0.1 * max(vec4(color.rgb, 1) - vec4(0.5), vec4(0.0));// * texture2D(texture, varyTexCoord);

  //gl_FragColor = texture2D(texture, varyTexCoord);

  //gl_FragColor.rgb = 1.0 - gl_FragColor.rgb;
}
