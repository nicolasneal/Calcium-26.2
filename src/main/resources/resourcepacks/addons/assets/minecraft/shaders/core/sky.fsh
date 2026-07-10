#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>

in float sphericalVertexDistance;
in float cylindricalVertexDistance;

out vec4 fragColor;

void main() {
    float fogValue = max(
        smoothstep(0.0, FogSkyEnd, sphericalVertexDistance),
        step(FogSkyEnd, cylindricalVertexDistance)
    );
    fragColor = vec4(mix(ColorModulator.rgb, FogColor.rgb, fogValue * FogColor.a), ColorModulator.a);
}
