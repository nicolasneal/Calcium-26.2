package net.nicolas.calcium.mixin.client.environment;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.nicolas.calcium.core.util.CalciumTintVariation;
import net.nicolas.calcium.core.util.CalciumWaterTint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BiomeColors.class)
public abstract class BiomeColorsVariationMixin {

    @ModifyReturnValue(method = "getAverageGrassColor", at = @At("RETURN"))
    private static int calcium$varyGrassTint(int original, BlockAndTintGetter level, BlockPos pos) {
        return CalciumTintVariation.GRASS.apply(pos, original);
    }

    @ModifyReturnValue(method = "getAverageFoliageColor", at = @At("RETURN"))
    private static int calcium$varyFoliageTint(int original, BlockAndTintGetter level, BlockPos pos) {
        return CalciumTintVariation.FOLIAGE.apply(pos, original);
    }

    @ModifyReturnValue(method = "getAverageWaterColor", at = @At("RETURN"))
    private static int calcium$varyWaterTint(int original, BlockAndTintGetter level, BlockPos pos) {
        return CalciumWaterTint.apply(pos, original);
    }

}
