#version 140
in vec4 a_position;
in vec4 a_color;
in vec2 a_texCoord0;

uniform mat4 u_projTrans;

out vec2 v_uv;         // tile-local UV (0..1 inside tile’s rect)
out vec2 v_worldPos;   // world-space position (continuous across tiles)
out vec4 v_data;       // packed per-tile data from color

void main() {
   v_uv = a_texCoord0;
   v_worldPos = a_position.xy;   // SpriteBatch feeds world units here
   v_data = a_color;             // r: shoreDist, g: angle/seed, b: depth, a: effect code
   gl_Position = u_projTrans * a_position;
}