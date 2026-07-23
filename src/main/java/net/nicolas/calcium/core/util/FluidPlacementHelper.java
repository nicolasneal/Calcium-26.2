package net.nicolas.calcium.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;

public final class FluidPlacementHelper {

    private FluidPlacementHelper() {}

    public static boolean isDestroyedByFluid(BlockState state, LevelReader level, BlockPos pos) {
        return !(state.getBlock() instanceof LiquidBlockContainer)
            && !level.getFluidState(pos).isEmpty()
            && !state.isCollisionShapeFullBlock(level, pos);
    }

}