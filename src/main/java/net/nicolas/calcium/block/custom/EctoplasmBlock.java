package net.nicolas.calcium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.redstone.Orientation;
import org.jspecify.annotations.Nullable;

public class EctoplasmBlock extends LiquidBlock {

    private static final Direction[] DIRECTIONS = Direction.values();

    public EctoplasmBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        this.scheduleReaction(state, level, pos);
    }

    @Override protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);
        this.scheduleReaction(state, level, pos);
    }

    private void scheduleReaction(BlockState state, Level level, BlockPos pos) {
        if (level.isClientSide()) {
            return;
        }
        for (Direction direction : DIRECTIONS) {
            FluidState neighbor = level.getBlockState(pos.relative(direction)).getFluidState();
            if (neighbor.is(FluidTags.WATER) || neighbor.is(FluidTags.LAVA)) {
                level.scheduleTick(pos, state.getFluidState().getType(), 1);
                return;
            }
        }
    }

}