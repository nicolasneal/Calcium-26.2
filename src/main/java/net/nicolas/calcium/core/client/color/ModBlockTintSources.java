package net.nicolas.calcium.core.client.color;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.block.state.BlockState;
import net.nicolas.calcium.core.util.CalciumDirtColors;

public final class ModBlockTintSources {

    private static final ColorResolver DIRT_COLOR_RESOLVER = (biome, x, z) -> CalciumDirtColors.get(biome.getSpecialEffects());

    public static BlockTintSource dirt() {
        return new BlockTintSource() {

            @Override
            public int color(BlockState state) {
                return CalciumDirtColors.DEFAULT_DIRT_COLOR;
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return level.getBlockTint(pos, DIRT_COLOR_RESOLVER);
            }

        };
    }

    private ModBlockTintSources() {}

}
