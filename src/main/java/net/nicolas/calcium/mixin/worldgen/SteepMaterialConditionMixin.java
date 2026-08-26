package net.nicolas.calcium.mixin.worldgen;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.world.level.levelgen.SurfaceRules$Context$SteepMaterialCondition")
public abstract class SteepMaterialConditionMixin {

    @ModifyReturnValue(method = "compute", at = @At(value = "RETURN", ordinal = 0))
    private boolean calcium$fixNorthSouthSteepness(boolean original, @Local(ordinal = 4) int heightNorth, @Local(ordinal = 5) int heightSouth) {
        return Math.abs(heightSouth - heightNorth) >= 4;
    }

    @ModifyReturnValue(method = "compute", at = @At(value = "RETURN", ordinal = 1))
    private boolean calcium$fixWestEastSteepness(boolean original, @Local(ordinal = 8) int heightWest, @Local(ordinal = 9) int heightEast) {
        return Math.abs(heightWest - heightEast) >= 4;
    }

}