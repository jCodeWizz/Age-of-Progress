#version 140
in vec4 v_color;
in vec2 v_texCoords;
out vec4 pixel;
uniform sampler2D u_texture;

void main() {
    pixel = texture(u_texture, v_texCoords);
    if (pixel.a >= 0) {
        pixel.x += 0.15f;
        pixel.y += 0.15f;
        pixel.z += 0.15f;
    }
}