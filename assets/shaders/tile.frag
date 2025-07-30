#version 140
in vec2 v_uv;
in vec2 v_worldPos;
in vec4 v_data;

out vec4 pixel;

uniform sampler2D u_texture;
uniform float u_time;
uniform vec2  u_worldUVScale;
uniform float u_waveFreq;

//
// --- helpers ---
float quantize(float val) {
    // 3 discrete bands for a pixel-art look
    return floor((val * 0.5 + 0.5) * 3.0) / 3.0;
}

// NOTE: pos is assumed to already be in the local, direction-aligned frame.
float shadeAtTime(vec2 pos, float t) {
    // Multi-directional but in the local frame:
    //   X = along motion, Y = across motion
    float s1 = sin(pos.x * u_waveFreq + t);
    float s2 = sin(pos.y * (u_waveFreq * 1.3) + t * 1.4);
    float s3 = sin((pos.x + pos.y) * u_waveFreq * 0.7 + t * 0.6);
    return (s1 + s2 + s3) / 3.0;
}

vec4 waterShader(vec4 base) {
    // --------------------------
    // Pixelize world-space UVs
    // --------------------------
    float pixelUV = 1.0 / 32.0;
    vec2 worldUV = floor((v_worldPos * u_worldUVScale) / pixelUV) * pixelUV;

    // --------------------------------------
    // Per-tile direction from v_data.r
    // Map [0..1] -> [0..2π). If you pass radians directly, replace next line with:
    //   float dirAngle = v_data.r; // radians
    // If you pass degrees (0..360): float dirAngle = radians(v_data.r);
    // --------------------------------------
    const float TAU = 6.28318530718;
    float dirAngle = 0.25 * TAU;

    // Rotate into local frame where +X is motion direction
    float c = cos(dirAngle), s = sin(dirAngle);
    mat2 R = mat2(c, -s,  // column-major in GLSL, but we use row-vector * mat
                  s,  c);

    // Local UV aligned to direction
    vec2 localUV = worldUV * R;

    // --------------------------------------
    // Stretch into long ovals along +X
    // (tune these two numbers)
    // --------------------------------------
    float stretchAlong  = 2.5; // length along motion
    float stretchAcross = 0.5; // thickness across motion
    localUV *= vec2(stretchAlong, stretchAcross);

    // --------------------------
    // Base water color (quantized)
    // --------------------------
    float shadeRaw = shadeAtTime(localUV, u_time);
    float shadeQ   = quantize(shadeRaw);
    vec3  waterCol = mix(base.rgb * 0.8, base.rgb * 1.2, 0.5 + 0.5 * shadeQ);

    // --------------------------
    // Foam (leading edge only)
    // Use explicit motion direction (+X in local frame)
    // --------------------------
    float timeDelta = 0.15;        // how far ahead in time we look
    float aheadDist = pixelUV * 1.5; // how far ahead in space we look
    vec2  dir      = vec2(1.0, 0.0); // +X is forward in local frame

    // Samples for directional “ahead”
    vec2 aheadUV      = localUV + dir * aheadDist;
    float nowHere     = shadeAtTime(localUV,  u_time);
    float futureHere  = shadeAtTime(localUV,  u_time + timeDelta);
    float nowAhead    = shadeAtTime(aheadUV,  u_time);
    float futureAhead = shadeAtTime(aheadUV,  u_time + timeDelta);

    // Directional, continuous foam signal:
    //  - crest advancing: future is higher than now (both here & ahead)
    //  - trough advancing: future is lower than now (both here & ahead)
    float foamSignal = 0.0;
    if (futureAhead > nowAhead && futureHere > nowHere) {
        foamSignal = clamp(futureHere - nowHere, 0.0, 1.0);
    } else if (futureAhead < nowAhead && futureHere < nowHere) {
        foamSignal = clamp(nowHere - futureHere, 0.0, 1.0);
    }

    // Thinning noise in local frame (stable)
    float foamNoise = fract(sin(dot(floor(localUV * 100.0), vec2(12.9898,78.233))) * 43758.5453);
    foamSignal *= smoothstep(0.4, 0.8, foamNoise);

    // Extract a thin moving edge from the continuous signal
    float foamEdge = smoothstep(0.10, 0.20, foamSignal) - smoothstep(0.20, 0.30, foamSignal);

    // Composite foam as a thin white highlight
    waterCol = mix(waterCol, vec3(0.95, 0.95, 1), foamEdge);

    return vec4(waterCol, base.a);
}

vec4 grassShader(vec4 baseColor) {
    vec3 color = baseColor.rgb;

    float stripeWidth = 25.0;
    float angle = radians(45.0);
    vec2 rotatedPos = vec2(
        v_worldPos.x * cos(angle) - v_worldPos.y * sin(angle),
        v_worldPos.x * sin(angle) + v_worldPos.y * cos(angle)
    );

    float stripePos = (rotatedPos.x * u_worldUVScale.x) / stripeWidth;
    float stripe = smoothstep(0.5, fract(stripePos), 0.9);
    vec3 darkColor = color * 0.9;
    vec3 lightColor = color;
    vec3 finalColor = mix(lightColor, darkColor, stripe);
    return vec4(finalColor, baseColor.a);
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