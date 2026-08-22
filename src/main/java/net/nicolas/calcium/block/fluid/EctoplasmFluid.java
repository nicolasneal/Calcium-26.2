package net.nicolas.calcium.block.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.block.ModFluids;
import net.nicolas.calcium.core.particle.ModParticleTypes;
import net.nicolas.calcium.item.ModItems;
import net.nicolas.calcium.sound.ModSoundGroups;

import java.util.Optional;

public abstract class EctoplasmFluid extends FlowingFluid {

    @Override public Fluid getSource() {
        return ModFluids.ECTOPLASM_STILL;
    }

    @Override public Fluid getFlowing() {
        return ModFluids.ECTOPLASM_FLOWING;
    }

    @Override public Item getBucket() {
        return ModItems.ECTOPLASM_BUCKET;
    }

    @Override protected BlockState createLegacyBlock(FluidState state) {
        return ModBlocks.ECTOPLASM.defaultBlockState().setValue(BlockStateProperties.LEVEL, getLegacyLevel(state));
    }

    @Override protected void beforeDestroyingBlock(LevelAccessor world, BlockPos pos, BlockState state) {
        final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropResources(state, world, pos, blockEntity);
    }

    @Override protected int getDropOff(LevelReader world) {
        return 2;
    }

    @Override public boolean isSame(Fluid fluid) {
        return fluid == getSource() || fluid == getFlowing();
    }

    @Override public int getTickDelay(LevelReader world) {
        return 15;
    }

    @Override protected boolean canBeReplacedWith(FluidState state, BlockGetter world, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !this.isSource(state) || fluid.is(FluidTags.LAVA) || fluid.is(FluidTags.WATER);
    }

    @Override protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override protected void entityInside(Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
        effectApplier.apply(InsideBlockEffectType.FREEZE);
        effectApplier.apply(InsideBlockEffectType.EXTINGUISH);
    }

    @Override protected void spreadTo(LevelAccessor world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
        if (direction == Direction.DOWN && state.getFluidState().is(FluidTags.WATER)) {
            world.setBlock(pos, Blocks.ICE.defaultBlockState(), 3);
            world.levelEvent(1501, pos, 0);
            return;
        }
        super.spreadTo(world, pos, state, direction, fluidState);
    }

    public static class Flowing extends EctoplasmFluid {

        @Override protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override protected boolean canConvertToSource(ServerLevel world) {
            return false;
        }

        @Override protected int getSlopeFindDistance(LevelReader world) {
            return 0;
        }

        @Override public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override public boolean isSource(FluidState state) {
            return false;
        }

    }

    public static class Still extends EctoplasmFluid {

        @Override protected boolean canConvertToSource(ServerLevel world) {
            return false;
        }

        @Override protected int getSlopeFindDistance(LevelReader world) {
            return 0;
        }

        @Override public int getAmount(FluidState state) {
            return 8;
        }

        @Override public boolean isSource(FluidState state) {
            return true;
        }

    }

    @Override public Optional<SoundEvent> getPickupSound() {
        return Optional.of(ModSoundGroups.ECTOPLASM_BUCKET_FILL);
    }

    @Override public void animateTick(Level world, BlockPos pos, FluidState state, RandomSource random) {

        if (random.nextInt(600) == 0) {
            world.playSound(null, pos, ModSoundGroups.ECTOPLASM_AMBIENT, SoundSource.BLOCKS, 0.3F, 1.0F);
        }

        BlockPos abovePos = pos.above();
        double x = pos.getX() + random.nextDouble();
        double y = pos.getY() + 1.0;
        double z = pos.getZ() + random.nextDouble();

        if (state.isSource() && !world.getBlockState(abovePos).isSolidRender() && random.nextInt(55) == 0) {
            world.addParticle(ModParticleTypes.ECTOPLASM_RAYS, x, y, z, 0.0, 0.0, 0.0);
        }

        if (random.nextInt(28) == 0) {
            world.addParticle(ModParticleTypes.ECTOPLASM_SPLASH, x, y, z, 0.0, 0.0, 0.0);
        }
    }

}