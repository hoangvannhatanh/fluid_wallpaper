attribute vec2 attribPosition;
attribute vec2 attribTexCoord;
varying vec2 varyTexCoord;
varying vec2 varyTexCoordX;
varying vec2 varyTexCoordY;
varying vec2 varyTexCoordXY;

void main()
{
  gl_Position = vec4(attribPosition, 0.0, 1.0);
  varyTexCoord = attribTexCoord;
  varyTexCoordX = vec2(1.0 - attribTexCoord.x, attribTexCoord.y);
  varyTexCoordY = vec2(attribTexCoord.x, 1.0 - attribTexCoord.y);
  varyTexCoordXY = vec2(1.0 - attribTexCoord.x, 1.0 - attribTexCoord.y);
}