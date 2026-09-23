#version 330
#extension GL_ARB_separate_shader_objects : require

#include <minecraft:fog.glsl>
#include <minecraft:dynamictransforms.glsl>

layout(location = 0) in float sphericalVertexDistance;
layout(location = 1) in float cylindricalVertexDistance;

layout(location = 0) out vec4 fragColor;

void main() {
    float fogValue = max(
        smoothstep(0.0, FogSkyEnd, sphericalVertexDistance),
        step(FogSkyEnd, cylindricalVertexDistance)
    );
    fragColor = vec4(mix(ColorModulator.rgb, FogColor.rgb, fogValue * FogColor.a), ColorModulator.a);
}
