package net.nicolas.calcium.mixin.gameplay.logging;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.WaterloggedTransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.nicolas.calcium.core.util.LoggingHelper;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(WaterloggedTransparentBlock.class)
public abstract class WaterloggedTransparentBlockMixin extends Block implements LiquidBlockContainer, BucketPickup {

    protected WaterloggedTransparentBlockMixin(BlockBehaviour.Properties properties) {
        super(properties);
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
    private @Nullable BlockState calcium$loggedPlacement(@Nullable BlockState original, BlockPlaceContext context) {
        if (original == null) {
            return null;
        }
        return LoggingHelper.withLogged(original, context.getLevel(), context.getClickedPos());
    }

    @Inject(method = "updateShape", at = @At("HEAD"))
    private void calcium$scheduleLoggedTick(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random, CallbackInfoReturnable<BlockState> cir) {
        LoggingHelper.scheduleTick(ticks, level, pos, state);
    }

    @ModifyReturnValue(method = "getFluidState", at = @At("RETURN"))
    private FluidState calcium$loggedFluidState(FluidState original, BlockState state) {
        return LoggingHelper.fluidState(state, original, true);
    }

    @Override public boolean canPlaceLiquid(@Nullable LivingEntity user, BlockGetter level, BlockPos pos, BlockState state, Fluid type) {
        return LoggingHelper.canPlaceLiquid(state, type);
    }

    @Override public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        return LoggingHelper.placeLiquid(level, pos, state, fluidState);
    }

    @Override public ItemStack pickupBlock(@Nullable LivingEntity user, LevelAccessor level, BlockPos pos, BlockState state) {
        return LoggingHelper.pickupBlock(user, level, pos, state);
    }

    @Override public Optional<SoundEvent> getPickupSound() {
        return Optional.empty();
    }

}