package net.nicolas.calcium.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jspecify.annotations.Nullable;

public final class LavaloggingHelper {

    public static final BooleanProperty LAVALOGGED = BooleanProperty.create("lavalogged");

    private LavaloggingHelper() {}

    public static BlockState withLavalogged(BlockState state, LevelReader level, BlockPos pos) {
        return state.setValue(LAVALOGGED, level.getFluidState(pos).is(Fluids.LAVA));
    }

    public static void scheduleLavaTick(ScheduledTickAccess ticks, LevelReader level, BlockPos pos, BlockState state) {
        if (state.getValue(LAVALOGGED)) {
            ticks.scheduleTick(pos, Fluids.LAVA, Fluids.LAVA.getTickDelay(level));
        }
    }

    public static FluidState fluidState(BlockState state, FluidState fallback) {
        return state.getValue(LAVALOGGED) ? Fluids.LAVA.getSource(false) : fallback;
    }

    public static boolean canPlaceLiquid(BlockState state, Fluid type) {
        if (state.getValue(BlockStateProperties.WATERLOGGED) || state.getValue(LAVALOGGED)) {
            return false;
        }
        return type == Fluids.WATER || type == Fluids.LAVA;
    }

    public static boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {

        if (state.getValue(BlockStateProperties.WATERLOGGED) || state.getValue(LAVALOGGED)) {
            return false;
        }

        BooleanProperty property = fluidState.is(Fluids.LAVA) ? LAVALOGGED : fluidState.is(Fluids.WATER) ? BlockStateProperties.WATERLOGGED : null;
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
            level.setBlock(pos, state.setValue(LAVALOGGED, false), 3);
            if (!state.canSurvive(level, pos)) {
                level.destroyBlock(pos, true);
            }
            playPickupSound(user, level, pos, Fluids.LAVA);
            return new ItemStack(Items.LAVA_BUCKET);
        }

        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            level.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, false), 3);
            if (!state.canSurvive(level, pos)) {
                level.destroyBlock(pos, true);
            }
            playPickupSound(user, level, pos, Fluids.WATER);
            return new ItemStack(Items.WATER_BUCKET);
        }

        return ItemStack.EMPTY;

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
