package net.nicolas.calcium.mixin.gameplay.lavalogging;

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
import net.minecraft.world.level.material.Fluids;
import net.nicolas.calcium.core.util.LavaloggingHelper;
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
    private void calcium$addLavalogged(BlockBehaviour.Properties properties, CallbackInfo ci) {
        this.registerDefaultState(this.defaultBlockState().setValue(LavaloggingHelper.LAVALOGGED, false));
    }

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    private void calcium$addLavaloggedProperty(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(LavaloggingHelper.LAVALOGGED);
    }

    @ModifyReturnValue(method = "getStateForPlacement", at = @At("RETURN"))
    private @Nullable BlockState calcium$lavalogPlacement(@Nullable BlockState original, BlockPlaceContext context) {
        if (original == null) {
            return null;
        }
        return LavaloggingHelper.withLavalogged(original, context.getLevel(), context.getClickedPos());
    }

    @Inject(method = "updateShape", at = @At("HEAD"))
    private void calcium$scheduleLavaTick(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random, CallbackInfoReturnable<BlockState> cir) {
        LavaloggingHelper.scheduleLavaTick(ticks, level, pos, state);
    }

    @Inject(method = "getFluidState", at = @At("RETURN"), cancellable = true)
    private void calcium$lavaFluidState(BlockState state, CallbackInfoReturnable<FluidState> cir) {
        if (state.getValue(LavaloggingHelper.LAVALOGGED)) {
            cir.setReturnValue(Fluids.LAVA.getSource(true));
        }
    }

    @Override public boolean canPlaceLiquid(@Nullable LivingEntity user, BlockGetter level, BlockPos pos, BlockState state, Fluid type) {
        return LavaloggingHelper.canPlaceLiquid(state, type);
    }

    @Override public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        return LavaloggingHelper.placeLiquid(level, pos, state, fluidState);
    }

    @Override public ItemStack pickupBlock(@Nullable LivingEntity user, LevelAccessor level, BlockPos pos, BlockState state) {
        return LavaloggingHelper.pickupBlock(user, level, pos, state);
    }

    @Override public Optional<SoundEvent> getPickupSound() {
        return Optional.empty();
    }

}