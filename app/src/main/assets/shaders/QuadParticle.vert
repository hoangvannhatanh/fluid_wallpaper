attribute vec2 attribPosition;
attribute vec4 attribColor;
attribute vec2 attribTexCoord;

varying vec4 varColor;
varying vec2 varTexCoord;

void main()
{
  gl_Position = vec4(attribPosition, 0.0, 1.0);
  varColor = attribColor;
  varTexCoord = attribTexCoord;
}