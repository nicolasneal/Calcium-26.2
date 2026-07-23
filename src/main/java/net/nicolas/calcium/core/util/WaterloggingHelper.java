package net.nicolas.calcium.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public final class WaterloggingHelper {

    private WaterloggingHelper() {}

    public static BlockState withWaterlogged(BlockState state, LevelReader level, BlockPos pos) {
        return state.setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(pos).is(Fluids.WATER));
    }

    public static void scheduleWaterTick(ScheduledTickAccess ticks, LevelReader level, BlockPos pos, BlockState state) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
    }

    public static FluidState fluidState(BlockState state, FluidState fallback) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : fallback;
    }

}
