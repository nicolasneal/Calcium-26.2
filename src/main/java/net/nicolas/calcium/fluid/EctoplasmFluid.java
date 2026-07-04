package net.nicolas.calcium.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.item.ModItems;
import net.nicolas.calcium.sound.ModSounds;

import java.util.Optional;

public abstract class EctoplasmFluid extends FlowableFluid {

    @Override public Fluid getStill() {
        return ModFluids.ECTOPLASM_STILL;
    }

    @Override public Fluid getFlowing() {
        return ModFluids.ECTOPLASM_FLOWING;
    }

    @Override public Item getBucketItem() {
        return ModItems.ECTOPLASM_BUCKET;
    }

    @Override protected BlockState toBlockState(FluidState state) {
        return ModBlocks.ECTOPLASM.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    @Override protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    @Override protected int getLevelDecreasePerBlock(WorldView world) {
        return 2;
    }

    @Override public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    @Override public int getTickRate(WorldView world) {
        return 15;
    }

    @Override protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !this.isStill(state) || fluid.isIn(FluidTags.LAVA);
    }

    @Override protected float getBlastResistance() {
        return 100.0F;
    }

    @Override protected void flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
        super.flow(world, pos, state, direction, fluidState);
        this.interactWithLava(world, pos);
    }

    private void interactWithLava(WorldAccess world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction);
            if (world.getFluidState(blockPos).isIn(FluidTags.LAVA)) {
                world.setBlockState(blockPos, ModBlocks.SOULSLATE.getDefaultState(), 3);
                world.syncWorldEvent(1501, pos, 0);
            }
        }
    }

    public static class Flowing extends EctoplasmFluid {

        @Override protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override protected boolean isInfinite(ServerWorld world) {
            return false;
        }

        @Override protected int getMaxFlowDistance(WorldView world) {
            return 0;
        }

        @Override public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        @Override public boolean isStill(FluidState state) {
            return false;
        }

    }

    public static class Still extends EctoplasmFluid {

        @Override protected boolean isInfinite(ServerWorld world) {
            return false;
        }

        @Override protected int getMaxFlowDistance(WorldView world) {
            return 0;
        }

        @Override public int getLevel(FluidState state) {
            return 8;
        }

        @Override public boolean isStill(FluidState state) {
            return true;
        }

    }

    @Override public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(ModSounds.ECTOPLASM_BUCKET_FILL);
    }

    @Override public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {

        if (!world.getBlockState(pos.up()).isAir()) {
            return;
        }

        if (random.nextInt(20) == 0) {
            world.playSound(null, pos, ModSounds.ECTOPLASM_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F);
        }

    }

}