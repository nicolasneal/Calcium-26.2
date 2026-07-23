package net.nicolas.calcium.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.nicolas.calcium.core.util.WaterloggingHelper;
import org.jspecify.annotations.Nullable;

public class LargeSeaPickleBlock extends VegetationBlock implements BonemealableBlock, SimpleWaterloggedBlock {

    public static final MapCodec<LargeSeaPickleBlock> CODEC = simpleCodec(LargeSeaPickleBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty TIP = BooleanProperty.create("tip");
    public static final EnumProperty<LargeSeaPickleType> TYPE = EnumProperty.create("type", LargeSeaPickleType.class);

    private static final VoxelShape SHAPE_SINGLE = Block.column(12.0, 0.0, 8.0);
    private static final VoxelShape SHAPE_DOUBLE = Block.column(12.0, 0.0, 16.0);

    public LargeSeaPickleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(TIP, false).setValue(TYPE, LargeSeaPickleType.SINGLE));
    }

    @Override protected MapCodec<LargeSeaPickleBlock> codec() {
        return CODEC;
    }

    @Override public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState state = level.getBlockState(pos);
        boolean isWater = level.getFluidState(pos).is(Fluids.WATER);
        if (state.is(this) && state.getValue(TYPE) == LargeSeaPickleType.SINGLE) {
            BlockState aboveState = level.getBlockState(pos.above());
            return state.setValue(TYPE, aboveState.is(this) ? LargeSeaPickleType.BOTTOM : LargeSeaPickleType.DOUBLE)
                .setValue(TIP, shouldTipBeTrue(isWater, false, aboveState));
        }
        return WaterloggingHelper.withWaterlogged(this.defaultBlockState().setValue(TYPE, LargeSeaPickleType.SINGLE), level, pos)
            .setValue(TIP, shouldTipBeTrue(isWater, true, level, pos));
    }

    public static boolean shouldTipBeTrue(boolean isWater, boolean isSingle, LevelReader level, BlockPos pos) {
        return isWater && (isSingle || shouldTipBeTrue(level.getBlockState(pos.above())));
    }

    public static boolean shouldTipBeTrue(boolean isWater, boolean isSingle, BlockState aboveState) {
        return isWater && (isSingle || shouldTipBeTrue(aboveState));
    }

    private static boolean shouldTipBeTrue(BlockState aboveState) {
        return aboveState.getFluidState().is(Fluids.WATER) && !aboveState.is(Blocks.SEA_PICKLE);
    }

    @Override protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return !state.getCollisionShape(level, pos).getFaceShape(Direction.UP).isEmpty() || state.isFaceSturdy(level, pos, Direction.UP);
    }

    @Override protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        return this.mayPlaceOn(level.getBlockState(belowPos), level, belowPos);
    }

    @Override protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        WaterloggingHelper.scheduleWaterTick(ticks, level, pos, state);
        BlockState aboveState = level.getBlockState(pos.above());
        LargeSeaPickleType type = state.getValue(TYPE);
        boolean isWater = state.getValue(WATERLOGGED);
        if (type == LargeSeaPickleType.BOTTOM) {
            type = LargeSeaPickleType.DOUBLE;
        }
        if (aboveState.is(this) && type == LargeSeaPickleType.DOUBLE) {
            type = LargeSeaPickleType.BOTTOM;
        }
        return state.setValue(TIP, shouldTipBeTrue(isWater, type == LargeSeaPickleType.SINGLE, aboveState)).setValue(TYPE, type);
    }

    @Override protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        if (!context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && state.getValue(TYPE) == LargeSeaPickleType.SINGLE) {
            return true;
        }
        return super.canBeReplaced(state, context);
    }

    @Override protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(TYPE) != LargeSeaPickleType.SINGLE ? SHAPE_DOUBLE : SHAPE_SINGLE;
    }

    @Override protected FluidState getFluidState(BlockState state) {
        return WaterloggingHelper.fluidState(state, super.getFluidState(state));
    }

    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, TIP, TYPE);
    }

    @Override public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (!level.getBlockState(pos.below()).is(BlockTags.CORAL_BLOCKS)) {
            return;
        }
        if (state.getValue(TYPE) == LargeSeaPickleType.SINGLE) {
            boolean isWaterlogged = state.getValue(WATERLOGGED);
            BlockState newState = state.setValue(TYPE, LargeSeaPickleType.DOUBLE)
                .setValue(TIP, shouldTipBeTrue(isWaterlogged, false, level, pos))
                .setValue(WATERLOGGED, isWaterlogged);
            level.setBlock(pos, newState, 2);
        } else if (state.getValue(TYPE) == LargeSeaPickleType.DOUBLE) {
            boolean isWaterlogged = level.getFluidState(pos.above()).is(Fluids.WATER);
            BlockState newState = state.setValue(TYPE, LargeSeaPickleType.SINGLE)
                .setValue(TIP, shouldTipBeTrue(isWaterlogged, true, level, pos))
                .setValue(WATERLOGGED, isWaterlogged);
            level.setBlock(pos.above(), newState, 2);
        }
    }

    @Override protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

}