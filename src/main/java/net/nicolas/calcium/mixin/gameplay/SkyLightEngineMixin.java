package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.SkyLightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SkyLightEngine.class)
public abstract class SkyLightEngineMixin {

    @ModifyExpressionValue(method = "propagateIncrease", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/lighting/SkyLightEngine;getOpacity(Lnet/minecraft/world/level/block/state/BlockState;)I"))
    private int calcium$halveWaterLightAttenuation(int opacity, @Local(name = "toState") BlockState toState, @Local(name = "propagationDirection") Direction propagationDirection, @Local(name = "toNode") long toNode) {
        if (propagationDirection == Direction.DOWN && (BlockPos.getY(toNode) & 1) == 0 && toState.getFluidState().is(FluidTags.WATER)) {
            return 0;
        }
        return opacity;
    }

    // Vanilla's early-exit bound (`fromLevel - 1`) assumes opacity is always at least 1,
    // which no longer holds now that water sometimes has opacity 0. Relaxing it to
    // `fromLevel - 0` prevents that stale bound from wrongly skipping the free-cost step.
    @ModifyConstant(method = "propagateIncrease", constant = @Constant(intValue = 1, ordinal = 0))
    private int calcium$relaxOpacityLowerBound(int one) {
        return 0;
    }

}