package net.nicolas.calcium.core.client.giantclam;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;

public class GiantClamAnimations {

    public static final AnimationDefinition ANCHOR = Builder.withLength(1.0F)
        .addAnimation("clam", new AnimationChannel(Targets.ROTATION, new Keyframe[]{
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
        }))
        .addAnimation("clam", new AnimationChannel(Targets.POSITION, new Keyframe[]{
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
        }))
        .addAnimation("all", new AnimationChannel(Targets.POSITION, new Keyframe[]{
            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.LINEAR)
        }))
        .build();

    public static final AnimationDefinition ANCHORED = Builder.withLength(0.0F)
        .addAnimation("clam", new AnimationChannel(Targets.ROTATION, new Keyframe[]{
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), Interpolations.LINEAR)
        }))
        .addAnimation("clam", new AnimationChannel(Targets.POSITION, new Keyframe[]{
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
        }))
        .addAnimation("all", new AnimationChannel(Targets.POSITION, new Keyframe[]{
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.LINEAR)
        }))
        .build();

    public static final AnimationDefinition OPEN = Builder.withLength(6.0F)
        .addAnimation("top", new AnimationChannel(Targets.ROTATION, new Keyframe[]{
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.0833F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.625F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
            new Keyframe(5.5F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(6.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
        }))
        .addAnimation("bottom", new AnimationChannel(Targets.ROTATION, new Keyframe[]{
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.5417F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
            new Keyframe(5.5F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(6.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
        }))
        .addAnimation("all", new AnimationChannel(Targets.ROTATION, new Keyframe[]{
            new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 5.0F, 0.0F), Interpolations.CATMULLROM),
            new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, -5.0F, 0.0F), Interpolations.CATMULLROM),
            new Keyframe(0.7083F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
        }))
        .build();

}