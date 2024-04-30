#version 140
in vec4 a_position;
in vec4 a_color;
in vec2 a_texCoord;
uniform mat4 u_projTrans;
out vec4 v_color;
out vec2 v_texCoords;

void main() {
    v_color = a_color;
    v_texCoords = a_texCoord;
    gl_Position = u_projTrans * a_position;
}