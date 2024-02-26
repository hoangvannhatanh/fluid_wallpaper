attribute vec2 attribPosition;
attribute vec2 attribTexCoord;
varying vec2 varyTexCoord;

void main()
{
  gl_Position = vec4(attribPosition, 0.0, 1.0);
  varyTexCoord = attribTexCoord;
}