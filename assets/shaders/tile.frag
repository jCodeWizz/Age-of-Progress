#version 140
in vec4 v_color;
in vec2 v_texCoords;
out vec4 pixel;
uniform sampler2D u_texture;

vec4 grassShader(vec4 baseColor) {
    return baseColor;
}

vec4 waterShader(vec4 baseColor) {
    return baseColor;
}

void main() {
    vec4 texColor = texture(u_texture, v_texCoords);

    if (v_color.a >= 0.15) {
        pixel = grassShader(texColor);
    } else if (v_color.a >= 0.05) {
        pixel = waterShader(texColor);
    } else {
        pixel = texColor;
    }
}