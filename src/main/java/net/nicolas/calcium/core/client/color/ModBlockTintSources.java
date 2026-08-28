package net.nicolas.calcium.core.client.color;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.block.state.BlockState;
import net.nicolas.calcium.core.util.CalciumDirtColors;

public final class ModBlockTintSources {

    private static final ColorResolver DIRT_COLOR_RESOLVER = (biome, x, z) -> CalciumDirtColors.get(biome.getSpecialEffects());

    private static final BlockTintSource DIRT = new BlockTintSource() {

        @Override
        public int color(BlockState state) {
            return CalciumDirtColors.DEFAULT_DIRT_COLOR;
        }

        @Override
        public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
            return level.getBlockTint(pos, DIRT_COLOR_RESOLVER);
        }

    };

    public static BlockTintSource dirt() {
        return DIRT;
    }

    private static final BlockTintSource GRASS = BlockTintSources.grass();

    private static final BlockTintSource GRASS_UNTINTED_PARTICLE = new BlockTintSource() {

        @Override
        public int color(BlockState state) {
            return GRASS.color(state);
        }

        @Override
        public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
            return GRASS.colorInWorld(state, level, pos);
        }

        @Override
        public int colorAsTerrainParticle(BlockState state, BlockAndTintGetter level, BlockPos pos) {
            return 0xFFFFFF;
        }

    };

    public static BlockTintSource grassUntintedParticle() {
        return GRASS_UNTINTED_PARTICLE;
    }

    private static final BlockTintSource GRASS_DIRT_PARTICLE = new BlockTintSource() {

        @Override
        public int color(BlockState state) {
            return GRASS.color(state);
        }

        @Override
        public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
            return GRASS.colorInWorld(state, level, pos);
        }

        @Override
        public int colorAsTerrainParticle(BlockState state, BlockAndTintGetter level, BlockPos pos) {
            return DIRT.colorAsTerrainParticle(state, level, pos);
        }

    };

    public static BlockTintSource grassDirtParticle() {
        return GRASS_DIRT_PARTICLE;
    }

    private ModBlockTintSources() {}

}
