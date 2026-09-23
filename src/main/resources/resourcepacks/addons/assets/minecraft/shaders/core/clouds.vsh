#version 330
#extension GL_ARB_separate_shader_objects : require

#include <minecraft:fog.glsl>
#include <minecraft:dynamictransforms.glsl>
#include <minecraft:projection.glsl>

const int FLAG_MASK_DIR = 7;
const int FLAG_INSIDE_FACE = 1 << 4;
const int FLAG_USE_TOP_COLOR = 1 << 5;
const int FLAG_EXTRA_Z = 1 << 6;
const int FLAG_EXTRA_X = 1 << 7;

layout(std140) uniform CloudInfo {
    vec4 CloudColor;
    vec3 CloudOffset;
    vec3 CellSize;
};

uniform isamplerBuffer CloudFaces;

layout(location = 0) out float vertexDistance;
const float CloudFadeAlpha   = 0.0;   // 0 = a full 0 alpha fade
const float CloudHeight      = 2.5;   // vertical scaling
const float CloudYOffset     = 0.0;   // Y offset
const float BrightnessBottom = 1.0;
const float BrightnessTop    = 1.0;
const float BrightnessNorth  = 1.0;
const float BrightnessSouth  = 1.0;
const float BrightnessWest   = 1.0;
const float BrightnessEast   = 1.0;

layout(location = 1) out vec4 vertexColor;

/*
// vertex function below should generate vec3s matching the indices in this array, but using less constants
const vec3[] vertices = vec3[](
    // Bottom face
    vec3(1, 0, 0),
    vec3(1, 0, 1),
    vec3(0, 0, 1),
    vec3(0, 0, 0),
    // Top face
    vec3(0, 1, 0),
    vec3(0, 1, 1),
    vec3(1, 1, 1),
    vec3(1, 1, 0),
    // North face
    vec3(0, 0, 0),
    vec3(0, 1, 0),
    vec3(1, 1, 0),
    vec3(1, 0, 0),
    // South face
    vec3(1, 0, 1),
    vec3(1, 1, 1),
    vec3(0, 1, 1),
    vec3(0, 0, 1),
    // West face
    vec3(0, 0, 1),
    vec3(0, 1, 1),
    vec3(0, 1, 0),
    vec3(0, 0, 0),
    // East face
    vec3(1, 0, 0),
    vec3(1, 1, 0),
    vec3(1, 1, 1),
    vec3(1, 0, 1)
);
*/

const int packedX = 0xF03CC3;
const int packedY = 0x6666F0;
const int packedZ = 0xC3F066;

vec3 vertex(int index) {
    vec3 pos = vec3(0);
    pos.x = float((packedX >> index) & 1);
    pos.y = float((packedY >> index) & 1);
    pos.z = float((packedZ >> index) & 1);
    return pos;
}

const vec4[] faceColors = vec4[](
    // Bottom face
    vec4(0.7, 0.7, 0.7, 1.0),
    // Top face
    vec4(1.0, 1.0, 1.0, 1.0),
    // North face
    vec4(0.8, 0.8, 0.8, 1.0),
    // South face
    vec4(0.8, 0.8, 0.8, 1.0),
    // West face
    vec4(0.9, 0.9, 0.9, 1.0),
    // East face
    vec4(0.9, 0.9, 0.9, 1.0)
);

void main() {
    int quadVertex = gl_VertexIndex % 4;
    int index = (gl_VertexIndex / 4) * 3;

    int cellX = texelFetch(CloudFaces, index).r;
    int cellZ = texelFetch(CloudFaces, index + 1).r;
    int dirAndFlags = texelFetch(CloudFaces, index + 2).r;
    int direction = dirAndFlags & FLAG_MASK_DIR;
    bool isInsideFace = (dirAndFlags & FLAG_INSIDE_FACE) == FLAG_INSIDE_FACE;
    bool useTopColor = (dirAndFlags & FLAG_USE_TOP_COLOR) == FLAG_USE_TOP_COLOR;
    cellX = (cellX << 1) | ((dirAndFlags & FLAG_EXTRA_X) >> 7);
    cellZ = (cellZ << 1) | ((dirAndFlags & FLAG_EXTRA_Z) >> 6);
    vec3 faceVertex = vertex((direction * 4) + (isInsideFace ? 3 - quadVertex : quadVertex));
    vec3 scaledVertex = faceVertex * CellSize;
    scaledVertex.y *= CloudHeight;
    vec3 pos = scaledVertex + (vec3(cellX, 0, cellZ) * CellSize) + CloudOffset + vec3(0, CloudYOffset, 0);
    gl_Position = ProjMat * ModelViewMat * vec4(pos, 1.0);

    vertexDistance = fog_spherical_distance(pos);
    float brightness = 1.0;
    if (useTopColor || direction == 1) brightness = BrightnessTop;
    else if (direction == 0) brightness = BrightnessBottom;
    else if (direction == 2) brightness = BrightnessNorth;
    else if (direction == 3) brightness = BrightnessSouth;
    else if (direction == 4) brightness = BrightnessWest;
    else if (direction == 5) brightness = BrightnessEast;

    float baseA = CloudColor.a;
    float vertexY = pos.y - CloudOffset.y;
    float normalizedY = clamp(vertexY / CloudHeight, 0.0, 1.0);

    float dir = clamp(CloudOffset.y / CloudHeight, -1.0, 1.0);
    float fadeBelow = mix(1.0, CloudFadeAlpha, normalizedY);
    float fadeAbove = mix(1.0, CloudFadeAlpha, 1.0 - normalizedY);
    float mixFactor = (dir + 1.0) * 0.5;
    float fade = mix(fadeBelow, fadeAbove, mixFactor);

    float finalA = baseA * (0.8 - fade);

    vertexColor = vec4(vec3(brightness), finalA) * CloudColor;
}
