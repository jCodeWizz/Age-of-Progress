#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
void main() {
    vec4 colour = v_color * texture2D(u_texture, v_texCoords);
    if(colour.a > 0.0) {
        gl_FragColor = new vec4(0.0, 0.0, 0.0, 0.58);
    } else {
        gl_FragColor = new vec4(0.0, 0.0, 0.0, 0.0);
    }
}