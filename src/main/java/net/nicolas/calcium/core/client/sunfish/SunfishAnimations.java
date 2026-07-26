package net.nicolas.calcium.core.client.sunfish;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.AnimationDefinition.Builder;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class SunfishAnimations {

    public static final AnimationDefinition IDLE = Builder.withLength(4.0F)
        .addAnimation("all", new AnimationChannel(Targets.ROTATION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(1.0F, 0.0F, 1.73F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.73F, 0.0F, 1.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(2.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(1.73F, 0.0F, -1.0F), Interpolations.LINEAR),
            new Keyframe(1.6667F, KeyframeAnimations.degreeVec(1.0F, 0.0F, -1.73F), Interpolations.LINEAR),
            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.0F), Interpolations.LINEAR),
            new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-1.0F, 0.0F, -1.73F), Interpolations.LINEAR),
            new Keyframe(2.6667F, KeyframeAnimations.degreeVec(-1.73F, 0.0F, -1.0F), Interpolations.LINEAR),
            new Keyframe(3.0F, KeyframeAnimations.degreeVec(-2.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(3.3333F, KeyframeAnimations.degreeVec(-1.73F, 0.0F, 1.0F), Interpolations.LINEAR),
            new Keyframe(3.6667F, KeyframeAnimations.degreeVec(-1.0F, 0.0F, 1.73F), Interpolations.LINEAR),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR)
        }))
        .addAnimation("all", new AnimationChannel(Targets.POSITION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.87F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.posVec(0.0F, 0.87F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), Interpolations.LINEAR),
            new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(2.3333F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), Interpolations.LINEAR),
            new Keyframe(2.6667F, KeyframeAnimations.posVec(0.0F, -0.87F, 0.0F), Interpolations.LINEAR),
            new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(3.3333F, KeyframeAnimations.posVec(0.0F, -0.87F, 0.0F), Interpolations.LINEAR),
            new Keyframe(3.6667F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), Interpolations.LINEAR),
            new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
        }))
        .addAnimation("topFin", new AnimationChannel(Targets.ROTATION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.73F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -1.0F), Interpolations.LINEAR),
            new Keyframe(1.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -1.73F), Interpolations.LINEAR),
            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.0F), Interpolations.LINEAR),
            new Keyframe(2.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -1.73F), Interpolations.LINEAR),
            new Keyframe(2.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -1.0F), Interpolations.LINEAR),
            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(3.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.0F), Interpolations.LINEAR),
            new Keyframe(3.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.73F), Interpolations.LINEAR),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR)
        }))
        .addAnimation("leftFin", new AnimationChannel(Targets.ROTATION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 30.0F), Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 27.32F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 20.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.32F), Interpolations.LINEAR),
            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), Interpolations.LINEAR),
            new Keyframe(2.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.32F), Interpolations.LINEAR),
            new Keyframe(2.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.LINEAR),
            new Keyframe(3.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 20.0F), Interpolations.LINEAR),
            new Keyframe(3.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 27.32F), Interpolations.LINEAR),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 30.0F), Interpolations.LINEAR)
        }))
        .addAnimation("bottomFin", new AnimationChannel(Targets.ROTATION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.73F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -1.0F), Interpolations.LINEAR),
            new Keyframe(1.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -1.73F), Interpolations.LINEAR),
            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.0F), Interpolations.LINEAR),
            new Keyframe(2.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -1.73F), Interpolations.LINEAR),
            new Keyframe(2.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -1.0F), Interpolations.LINEAR),
            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(3.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.0F), Interpolations.LINEAR),
            new Keyframe(3.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.73F), Interpolations.LINEAR),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.0F), Interpolations.LINEAR)
        }))
        .addAnimation("rightFin", new AnimationChannel(Targets.ROTATION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -30.0F), Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -27.32F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -20.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 7.32F), Interpolations.LINEAR),
            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), Interpolations.LINEAR),
            new Keyframe(2.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 7.32F), Interpolations.LINEAR),
            new Keyframe(2.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), Interpolations.LINEAR),
            new Keyframe(3.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -20.0F), Interpolations.LINEAR),
            new Keyframe(3.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -27.32F), Interpolations.LINEAR),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -30.0F), Interpolations.LINEAR)
        }))
        .build();

    public static final AnimationDefinition SWIM = Builder.withLength(2.0F)
        .addAnimation("all", new AnimationChannel(Targets.ROTATION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 6.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 5.2F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 3.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, -3.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, -5.2F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -6.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.0F, -5.2F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0.0F, -3.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.6667F, KeyframeAnimations.degreeVec(0.0F, 3.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.8333F, KeyframeAnimations.degreeVec(0.0F, 5.2F, 0.0F), Interpolations.LINEAR),
            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 6.0F, 0.0F), Interpolations.LINEAR)
        }))
        .addAnimation("all", new AnimationChannel(Targets.POSITION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.87F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.87F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.1667F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.posVec(0.0F, -0.87F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, -0.87F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.8333F, KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F), Interpolations.LINEAR),
            new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
        }))
        .addAnimation("topFin", new AnimationChannel(Targets.ROTATION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 40.0F), Interpolations.LINEAR),
            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(4.0F, 0.0F, 34.64F), Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(6.93F, 0.0F, 20.0F), Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(8.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(6.93F, 0.0F, -20.0F), Interpolations.LINEAR),
            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(4.0F, 0.0F, -34.64F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -40.0F), Interpolations.LINEAR),
            new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-4.0F, 0.0F, -34.64F), Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-6.93F, 0.0F, -20.0F), Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-8.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-6.93F, 0.0F, 20.0F), Interpolations.LINEAR),
            new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-4.0F, 0.0F, 34.64F), Interpolations.LINEAR),
            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 40.0F), Interpolations.LINEAR)
        }))
        .addAnimation("tailFin", new AnimationChannel(Targets.ROTATION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 17.32F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, -17.32F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 17.32F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, -17.32F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.0F, 17.32F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0.0F, -17.32F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.6667F, KeyframeAnimations.degreeVec(0.0F, 17.32F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.8333F, KeyframeAnimations.degreeVec(0.0F, -17.32F, 0.0F), Interpolations.LINEAR),
            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
        }))
        .addAnimation("bottomFin", new AnimationChannel(Targets.ROTATION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -40.0F), Interpolations.LINEAR),
            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-4.0F, 0.0F, -34.64F), Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-6.93F, 0.0F, -20.0F), Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-8.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-6.93F, 0.0F, 20.0F), Interpolations.LINEAR),
            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-4.0F, 0.0F, 34.64F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 40.0F), Interpolations.LINEAR),
            new Keyframe(1.1667F, KeyframeAnimations.degreeVec(4.0F, 0.0F, 34.64F), Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(6.93F, 0.0F, 20.0F), Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.degreeVec(8.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.6667F, KeyframeAnimations.degreeVec(6.93F, 0.0F, -20.0F), Interpolations.LINEAR),
            new Keyframe(1.8333F, KeyframeAnimations.degreeVec(4.0F, 0.0F, -34.64F), Interpolations.LINEAR),
            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -40.0F), Interpolations.LINEAR)
        }))
        .build();

    public static final AnimationDefinition LAND = Builder.withLength(2.0F)
        .addAnimation("all", new AnimationChannel(Targets.ROTATION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, -90.0F), Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, -90.0F), Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, -90.0F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-4.9811F, 0.4352F, -85.0189F), Interpolations.LINEAR),
            new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-4.9811F, -0.4352F, -94.9811F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, -90.0F), Interpolations.LINEAR)
        }))
        .addAnimation("all", new AnimationChannel(Targets.POSITION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -10.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -10.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -10.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, -6.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, -6.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -10.0F, 0.0F), Interpolations.LINEAR)
        }))
        .addAnimation("topFin", new AnimationChannel(Targets.ROTATION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.0F), Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.0F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -27.5F), Interpolations.LINEAR),
            new Keyframe(0.7917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), Interpolations.LINEAR)
        }))
        .addAnimation("topFin", new AnimationChannel(Targets.POSITION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(1.0F, -1.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.posVec(1.0F, -1.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.posVec(1.0F, -1.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
        }))
        .addAnimation("tailFin", new AnimationChannel(Targets.ROTATION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 10.39F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 10.39F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, -10.39F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, -10.39F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.0F, 10.39F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0.0F, 10.39F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.6667F, KeyframeAnimations.degreeVec(0.0F, -10.39F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.8333F, KeyframeAnimations.degreeVec(0.0F, -10.39F, 0.0F), Interpolations.LINEAR),
            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
        }))
        .addAnimation("leftFin", new AnimationChannel(Targets.ROTATION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -42.5F, 0.0F), Interpolations.LINEAR)
        }))
        .addAnimation("bottomFin", new AnimationChannel(Targets.ROTATION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 7.5F), Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 15.0F), Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 15.0F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 17.5F), Interpolations.LINEAR),
            new Keyframe(0.7917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 7.5F), Interpolations.LINEAR)
        }))
        .addAnimation("bottomFin", new AnimationChannel(Targets.POSITION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(1.0F, 1.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.posVec(1.0F, 1.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.posVec(1.0F, 1.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
        }))
        .addAnimation("rightFin", new AnimationChannel(Targets.ROTATION, new Keyframe[] {
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 42.5F, 0.0F), Interpolations.LINEAR)
        }))
        .build();

}
