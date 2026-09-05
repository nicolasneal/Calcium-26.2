package net.nicolas.calcium.core.client.color;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.block.state.BlockState;
import net.nicolas.calcium.core.util.CalciumDirtColors;
import net.nicolas.calcium.core.util.CalciumSandColors;

public final class ModBlockTintSources {

    private static BlockTintSource biomeColorTint(int defaultColor, ColorResolver resolver) {
        return new BlockTintSource() {

            @Override
            public int color(BlockState state) {
                return defaultColor;
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return level.getBlockTint(pos, resolver);
            }

        };
    }

    private static final BlockTintSource DIRT = biomeColorTint(CalciumDirtColors.DEFAULT_DIRT_COLOR, (biome, x, z) -> CalciumDirtColors.get(biome.getSpecialEffects()));

    public static BlockTintSource dirt() {
        return DIRT;
    }

    private static final BlockTintSource SAND = biomeColorTint(CalciumSandColors.DEFAULT_SAND_COLOR, (biome, x, z) -> CalciumSandColors.get(biome.getSpecialEffects()));

    public static BlockTintSource sand() {
        return SAND;
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
