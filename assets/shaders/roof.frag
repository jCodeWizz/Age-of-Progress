#version 140
in vec4 v_color;
in vec2 v_texCoords;
out vec4 pixel;
uniform sampler2D u_texture;
uniform float vis;

float PHI = 1.61803398874989484820459;  // Φ = Golden Ratio

float random(vec2 xy, float seed){
    return fract(tan(distance(xy*PHI, xy)*seed)*xy.x);
}

void main() {
    pixel = texture(u_texture, v_texCoords);
    if(pixel.a > 0) {
        pixel.a = vis;
    }
}