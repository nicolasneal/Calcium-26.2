package net.nicolas.calcium.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.nicolas.calcium.core.client.color.ModBlockTintSources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ColoredFallingBlock.class)
public abstract class ColoredFallingBlockMixin {

    @ModifyReturnValue(method = "getDustColor", at = @At("RETURN"))
    private int calcium$biomeTintedSandDust(int original, BlockState blockState, BlockGetter level, BlockPos pos) {
        if ((Object) this == Blocks.SAND && level instanceof BlockAndTintGetter tintGetter) {
            return ModBlockTintSources.sand().colorAsTerrainParticle(blockState, tintGetter, pos);
        }
        return original;
    }

}
