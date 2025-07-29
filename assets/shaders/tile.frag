#version 140
in vec2 v_uv;
in vec2 v_worldPos;
in vec4 v_data;

out vec4 pixel;

uniform sampler2D u_texture;
uniform float u_time;
uniform vec2  u_worldUVScale;
uniform float u_waveFreq;

float quantize(float val) {
    return floor((val * 0.5 + 0.5) * 3.0) / 3.0;
}

float shadeAtTime(vec2 pos, float t) {
    float s1 = sin(pos.x * u_waveFreq + t);
    float s2 = sin(pos.y * (u_waveFreq * 1.3) + t * 1.4);
    float s3 = sin((pos.x + pos.y) * u_waveFreq * 0.7 + t * 0.6);
    return (s1 + s2 + s3) / 3.0;
}

vec4 waterShader(vec4 baseColor) {
    vec4 base = texture(u_texture, v_uv);

    // Pixelize UVs for a chunkier pixel-art style
    float pixelUV = 1.0 / 32.0;
    vec2 worldUV = floor((v_worldPos * u_worldUVScale) / pixelUV) * pixelUV;

    // Continuous shade and quantized bands for water colors
    float shadeRaw = shadeAtTime(worldUV, u_time);
    float shade = quantize(shadeRaw);

    // Base water color (light/dark variation)
    vec3 waterCol = mix(base.rgb * 0.8, base.rgb * 1.2, 0.5 + 0.5 * shade);

    // --- Foam logic ---
    float eps = pixelUV * 0.5;
    float timeDelta = 0.15;      // how far ahead in time to check
    float aheadDist = pixelUV * 1.5; // how far ahead in space

    // Gradient: direction the wave is moving
    float shadeL = shadeAtTime(worldUV + vec2(-eps, 0.0), u_time);
    float shadeR = shadeAtTime(worldUV + vec2( eps, 0.0), u_time);
    float shadeD = shadeAtTime(worldUV + vec2( 0.0,-eps), u_time);
    float shadeU = shadeAtTime(worldUV + vec2( 0.0, eps), u_time);
    vec2 grad = normalize(vec2(shadeR - shadeL, shadeU - shadeD));

    // Sample ahead in time and space
    vec2 aheadUV = worldUV + grad * aheadDist;
    float shadeNow = shadeAtTime(worldUV, u_time);
    float shadeFuture = shadeAtTime(worldUV, u_time + timeDelta);
    float aheadNow = shadeAtTime(aheadUV, u_time);
    float aheadFuture = shadeAtTime(aheadUV, u_time + timeDelta);

    // Compute directional foam signal (continuous)
    float foamSignal = 0.0;

    // Crest advancing
    if (aheadFuture > aheadNow && shadeFuture > shadeNow) {
        foamSignal = clamp(shadeFuture - shadeNow, 0.0, 1.0);
    }
    // Trough advancing
    else if (aheadFuture < aheadNow && shadeFuture < shadeNow) {
        foamSignal = clamp(shadeNow - shadeFuture, 0.0, 1.0);
    }

    // Add directional noise to break up foam
    float foamNoise = fract(sin(dot(floor(worldUV * 100.0), vec2(12.9898,78.233))) * 43758.5453);
    foamSignal *= smoothstep(0.4, 0.8, foamNoise); // modulate and thin

    // Extract only a thin edge highlight
    float foamEdge = smoothstep(0.1, 0.2, foamSignal) - smoothstep(0.2, 0.3, foamSignal);

    // Blend foam softly (invert the dominance)
    waterCol = mix(waterCol, vec3(1.0), foamEdge);

    return vec4(waterCol, base.a);
}

vec4 grassShader(vec4 baseColor) {
    return baseColor; // Placeholder for grass effect
}

void main() {
    vec4 base = texture(u_texture, v_uv);

    if (v_data.a >= 0.15) {
        pixel = grassShader(base);
    } else if (v_data.a >= 0.05) {
        pixel = waterShader(base);
    } else {
        pixel = base;
    }
}
