attribute vec3 attribPositionSize;
attribute vec3 attribColor;

varying vec3 varColor;

void main()
{
  gl_Position = vec4(attribPositionSize.xy, 0.0, 1.0);
  gl_PointSize = attribPositionSize.z;
  varColor = attribColor;
}