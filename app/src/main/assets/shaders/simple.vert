uniform float aspectRatio;
attribute vec3 aPosition;
varying vec2 vPos;

void main()
{
  gl_Position = vec4(aPosition, 1.0);
  vPos = aPosition.xy;
  vPos.x *= aspectRatio;
}