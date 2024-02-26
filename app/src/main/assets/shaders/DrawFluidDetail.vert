attribute vec2 attribPosition;
attribute vec2 attribTexCoord;
varying vec2 varyTexCoord;
varying vec2 varyTexCoordDetail;
uniform vec2 uDetailTexCoordScale;

void main()
{
  gl_Position = vec4(attribPosition, 0.0, 1.0);
  varyTexCoord = attribTexCoord;
  vec2 texCoordScaled = (attribTexCoord - vec2(0.5)) * uDetailTexCoordScale;
  varyTexCoordDetail =  texCoordScaled + vec2(0.5);
}