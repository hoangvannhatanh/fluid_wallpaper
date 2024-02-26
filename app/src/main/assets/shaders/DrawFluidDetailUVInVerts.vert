attribute vec2 attribPosition;
attribute vec2 attribTexCoord;
varying vec2 varyTexCoord;


attribute vec4 attribDetailUV0;
attribute vec4 attribDetailUV1;
attribute vec4 attribDetailUV2;
varying vec4 varyDetailUV0;
varying vec4 varyDetailUV1;
varying vec4 varyDetailUV2;

void main()
{
  gl_Position = vec4(attribPosition, 0.0, 1.0);
  varyTexCoord = attribTexCoord;
  varyDetailUV0 = attribDetailUV0;
  varyDetailUV1 = attribDetailUV1;
  varyDetailUV2 = attribDetailUV2;
}