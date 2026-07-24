package net.nicolas.calcium.core.client.giantclam;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.DyeColor;
import net.nicolas.calcium.entity.custom.GiantClamVariant;

public class GiantClamRenderState extends LivingEntityRenderState {

    public GiantClamVariant.BaseColor baseColor = GiantClamVariant.BaseColor.CYAN;
    public GiantClamVariant.Pattern pattern = GiantClamVariant.Pattern.NO_PATTERN;
    public DyeColor dyeColor = DyeColor.WHITE;
    public boolean anchored;
    public final AnimationState anchoringAnimationState = new AnimationState();
    public final AnimationState openAnimationState = new AnimationState();

}