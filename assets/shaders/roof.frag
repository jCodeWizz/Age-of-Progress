#version 140
in vec4 v_color;
in vec2 v_texCoords;
out vec4 pixel;
uniform sampler2D u_texture;
uniform float mx;
uniform float my;
uniform float viewportWidth; // Width of the viewport/screen
uniform float viewportHeight; // Height of the viewport/screen

// Function to convert texture coordinates to screen coordinates
vec2 toScreenCoords(vec2 texCoords) {
    return vec2(texCoords.x * viewportWidth, texCoords.y * viewportHeight);
}

// Function to calculate distance between two points in screen coordinates
float distance(vec2 p1, vec2 p2) {
    return sqrt(pow(p2.x - p1.x, 2.0) + pow(p2.y - p1.y, 2.0));
}

void main() {
    // Get the color of the pixel
    pixel = texture(u_texture, v_texCoords);

    // Convert texture coordinates to screen coordinates
    vec2 screenCoords = toScreenCoords(v_texCoords);

    // Calculate the distance between the current pixel and the mouse cursor in screen coordinates
    float dst = distance(vec2(mx, my), screenCoords);

    // Define a threshold distance within which pixels will fade out
    float fadeThreshold = 20.0; // Adjust this value as needed

    // If the distance is less than the threshold, fade out the pixel
    if (dst < fadeThreshold) {
        // Set the alpha component of the pixel color to 0 (fully transparent)
        pixel.a = 0.0;
    }
}