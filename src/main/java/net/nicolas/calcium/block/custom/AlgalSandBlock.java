package net.nicolas.calcium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class AlgalSandBlock extends SandBlock {

    public AlgalSandBlock(BlockBehaviour.Properties properties) {
        super(new ColorRGBA(14406560), properties);
    }

    @Override protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.getFluidState(pos.above()).is(FluidTags.WATER)) {
            level.setBlockAndUpdate(pos, Blocks.SAND.defaultBlockState());
        }
    }

}
