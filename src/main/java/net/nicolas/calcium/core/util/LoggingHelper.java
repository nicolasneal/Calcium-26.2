package net.nicolas.calcium.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.nicolas.calcium.block.ModFluids;
import net.nicolas.calcium.item.ModItems;
import org.jspecify.annotations.Nullable;

public final class LoggingHelper {

    public static final BooleanProperty LAVALOGGED = BooleanProperty.create("lavalogged");
    public static final BooleanProperty ECTOLOGGED = BooleanProperty.create("ectologged");

    private LoggingHelper() {}

    public static void addProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAVALOGGED, ECTOLOGGED);
    }

    public static BlockState unlogged(BlockState state) {
        return state.setValue(LAVALOGGED, false).setValue(ECTOLOGGED, false);
    }

    public static BlockState withLogged(BlockState state, LevelReader level, BlockPos pos) {
        FluidState fluidState = level.getFluidState(pos);
        return state.setValue(LAVALOGGED, fluidState.is(Fluids.LAVA)).setValue(ECTOLOGGED, fluidState.is(ModFluids.ECTOPLASM_STILL));
    }

    public static void scheduleTick(ScheduledTickAccess ticks, LevelReader level, BlockPos pos, BlockState state) {
        if (state.getValue(LAVALOGGED)) {
            ticks.scheduleTick(pos, Fluids.LAVA, Fluids.LAVA.getTickDelay(level));
        }
        else if (state.getValue(ECTOLOGGED)) {
            ticks.scheduleTick(pos, ModFluids.ECTOPLASM_STILL, ModFluids.ECTOPLASM_STILL.getTickDelay(level));
        }
    }

    public static FluidState fluidState(BlockState state, FluidState fallback, boolean falling) {
        if (state.getValue(LAVALOGGED)) {
            return Fluids.LAVA.getSource(falling);
        }
        if (state.getValue(ECTOLOGGED)) {
            return ModFluids.ECTOPLASM_STILL.getSource(falling);
        }
        return fallback;
    }

    public static boolean canPlaceLiquid(BlockState state, Fluid type) {
        if (isLogged(state)) {
            return false;
        }
        return type == Fluids.WATER || type == Fluids.LAVA || type == ModFluids.ECTOPLASM_STILL;
    }

    public static boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {

        if (isLogged(state)) {
            return false;
        }

        BooleanProperty property = propertyFor(fluidState);
        if (property == null) {
            return false;
        }

        if (!level.isClientSide()) {
            level.setBlock(pos, state.setValue(property, true), 3);
            level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
        }

        return true;

    }

    public static ItemStack pickupBlock(@Nullable LivingEntity user, LevelAccessor level, BlockPos pos, BlockState state) {

        if (state.getValue(LAVALOGGED)) {
            return drain(user, level, pos, state, LAVALOGGED, Fluids.LAVA, Items.LAVA_BUCKET);
        }

        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            return drain(user, level, pos, state, BlockStateProperties.WATERLOGGED, Fluids.WATER, Items.WATER_BUCKET);
        }

        if (state.getValue(ECTOLOGGED)) {
            return drain(user, level, pos, state, ECTOLOGGED, ModFluids.ECTOPLASM_STILL, ModItems.ECTOPLASM_BUCKET);
        }

        return ItemStack.EMPTY;

    }

    private static boolean isLogged(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) || state.getValue(LAVALOGGED) || state.getValue(ECTOLOGGED);
    }

    private static @Nullable BooleanProperty propertyFor(FluidState fluidState) {
        if (fluidState.is(Fluids.LAVA)) {
            return LAVALOGGED;
        }
        if (fluidState.is(Fluids.WATER)) {
            return BlockStateProperties.WATERLOGGED;
        }
        return fluidState.is(ModFluids.ECTOPLASM_STILL) ? ECTOLOGGED : null;
    }

    private static ItemStack drain(@Nullable LivingEntity user, LevelAccessor level, BlockPos pos, BlockState state, BooleanProperty property, Fluid fluid, Item bucket) {
        level.setBlock(pos, state.setValue(property, false), 3);
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
        playPickupSound(user, level, pos, fluid);
        return new ItemStack(bucket);
    }

    private static void playPickupSound(@Nullable LivingEntity user, LevelAccessor level, BlockPos pos, Fluid fluid) {
        fluid.getPickupSound().ifPresent(sound -> {
            if (user != null) {
                user.playSound(sound, 1.0F, 1.0F);
            } else {
                level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        });
    }
}
