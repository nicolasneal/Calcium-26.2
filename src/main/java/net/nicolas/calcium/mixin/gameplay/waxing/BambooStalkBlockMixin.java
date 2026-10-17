package net.nicolas.calcium.mixin.gameplay.waxing;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.nicolas.calcium.event.CropWaxing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BambooStalkBlock.class)
public abstract class BambooStalkBlockMixin extends Block implements CropWaxing.Waxable {

    protected BambooStalkBlockMixin(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Shadow protected abstract int getHeightAboveUpToMax(BlockGetter level, BlockPos pos);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void calcium$addWaxedDefault(BlockBehaviour.Properties properties, CallbackInfo ci) {
        this.registerDefaultState(this.defaultBlockState().setValue(WAXED, false));
    }

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    private void calcium$addWaxedProperty(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(WAXED);
    }

    @Override public boolean canBeWaxed(BlockGetter level, BlockPos pos, BlockState state) {
        return !level.getBlockState(pos.above()).is(this);
    }

    @ModifyReturnValue(method = "updateShape", at = @At("RETURN"))
    private BlockState calcium$clearWaxWhenNoLongerTop(BlockState original, BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (directionToNeighbour == Direction.UP && neighbourState.is(this) && original.is(this) && original.getValue(WAXED)) {
            return original.setValue(WAXED, false);
        }
        return original;
    }

    @Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
    private void calcium$freezeTicking(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.getValue(WAXED)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void calcium$freezeGrowth(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (state.getValue(WAXED)) {
            ci.cancel();
        }
    }

    @Inject(method = "isValidBonemealTarget", at = @At("HEAD"), cancellable = true)
    private void calcium$blockBonemeal(LevelReader level, BlockPos pos, BlockState state, BonemealSource source, CallbackInfoReturnable<Boolean> cir) {
        if (this.calcium$isTopWaxed(level, pos)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "performBonemeal", at = @At("HEAD"), cancellable = true)
    private void calcium$blockBonemealGrowth(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source, CallbackInfo ci) {
        if (this.calcium$isTopWaxed(level, pos)) {
            ci.cancel();
        }
    }

    @Unique
    private boolean calcium$isTopWaxed(BlockGetter level, BlockPos pos) {
        BlockState top = level.getBlockState(pos.above(this.getHeightAboveUpToMax(level, pos)));
        return top.is(this) && top.getValue(WAXED);
    }

}