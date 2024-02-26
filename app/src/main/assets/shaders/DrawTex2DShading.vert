attribute vec2 attribPosition;
attribute vec2 attribTexCoord;
varying vec2 varyTexCoord;
varying vec2 varyTexCoordL;
varying vec2 varyTexCoordR;
varying vec2 varyTexCoordT;
varying vec2 varyTexCoordB;

uniform vec2 uFluidTexelSizeVert;

void main()
{
  gl_Position = vec4(attribPosition, 0.0, 1.0);
  varyTexCoord = attribTexCoord;
  varyTexCoordL = attribTexCoord + vec2(-uFluidTexelSizeVert.x, 0.0);
  varyTexCoordR = attribTexCoord + vec2( uFluidTexelSizeVert.x, 0.0);
  varyTexCoordT = attribTexCoord + vec2(0.0, -uFluidTexelSizeVert.y);
  varyTexCoordB = attribTexCoord + vec2(0.0,  uFluidTexelSizeVert.y);
}