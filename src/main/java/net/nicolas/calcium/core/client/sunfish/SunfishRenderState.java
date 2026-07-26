package net.nicolas.calcium.core.client.sunfish;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.nicolas.calcium.entity.custom.SunfishVariant;

public class SunfishRenderState extends LivingEntityRenderState {

    public SunfishVariant variant = SunfishVariant.EARL;
    public int sunfishAge = 0;
    public float baskingProgress = 0.0F;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState landAnimationState = new AnimationState();

}
