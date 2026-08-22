package net.nicolas.calcium.mixin.gameplay.logging;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.nicolas.calcium.core.util.LoggingHelper;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(IronBarsBlock.class)
public abstract class IronBarsBlockMixin extends Block implements LiquidBlockContainer, BucketPickup {

    protected IronBarsBlockMixin(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Unique
    private boolean calcium$isIronBars() {
        return (Object) this == Blocks.IRON_BARS;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void calcium$addLoggedProperties(BlockBehaviour.Properties properties, CallbackInfo ci) {
        this.registerDefaultState(LoggingHelper.unlogged(this.defaultBlockState()));
    }

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    private void calcium$defineLoggedProperties(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        LoggingHelper.addProperties(builder);
    }

    @ModifyReturnValue(method = "getStateForPlacement", at = @At("RETURN"))
    private BlockState calcium$loggedPlacement(BlockState original, BlockPlaceContext context) {
        if (!this.calcium$isIronBars()) {
            return original;
        }
        return LoggingHelper.withLogged(original, context.getLevel(), context.getClickedPos());
    }

    @Inject(method = "updateShape", at = @At("HEAD"))
    private void calcium$scheduleLoggedTick(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random, CallbackInfoReturnable<BlockState> cir) {
        if (this.calcium$isIronBars()) {
            LoggingHelper.scheduleTick(ticks, level, pos, state);
        }
    }

    @Override public boolean canPlaceLiquid(@Nullable LivingEntity user, BlockGetter level, BlockPos pos, BlockState state, Fluid type) {
        if (!this.calcium$isIronBars()) {
            return type == Fluids.WATER;
        }
        return LoggingHelper.canPlaceLiquid(state, type);
    }

    @Override public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!this.calcium$isIronBars()) {
            if (state.getValue(BlockStateProperties.WATERLOGGED) || !fluidState.is(Fluids.WATER)) {
                return false;
            }
            if (!level.isClientSide()) {
                level.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, true), 3);
                level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
            }
            return true;
        }
        return LoggingHelper.placeLiquid(level, pos, state, fluidState);
    }

    @Override public ItemStack pickupBlock(@Nullable LivingEntity user, LevelAccessor level, BlockPos pos, BlockState state) {
        if (!this.calcium$isIronBars()) {
            if (!state.getValue(BlockStateProperties.WATERLOGGED)) {
                return ItemStack.EMPTY;
            }
            level.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, false), 3);
            if (!state.canSurvive(level, pos)) {
                level.destroyBlock(pos, true);
            }
            return new ItemStack(Items.WATER_BUCKET);
        }
        return LoggingHelper.pickupBlock(user, level, pos, state);
    }

    @Override public Optional<SoundEvent> getPickupSound() {
        return this.calcium$isIronBars() ? Optional.empty() : Fluids.WATER.getPickupSound();
    }

}