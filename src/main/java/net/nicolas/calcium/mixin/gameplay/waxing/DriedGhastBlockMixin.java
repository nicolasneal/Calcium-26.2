package net.nicolas.calcium.mixin.gameplay.waxing;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DriedGhastBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.nicolas.calcium.event.CropWaxing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DriedGhastBlock.class)
public abstract class DriedGhastBlockMixin extends HorizontalDirectionalBlock implements CropWaxing.Waxable {

    protected DriedGhastBlockMixin(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void calcium$addWaxedDefault(BlockBehaviour.Properties properties, CallbackInfo ci) {
        this.registerDefaultState(this.defaultBlockState().setValue(WAXED, false));
    }

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    private void calcium$addWaxedProperty(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(WAXED);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void calcium$freezeHydrationWhileWaxed(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (state.getValue(WAXED)) {
            ci.cancel();
        }
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void calcium$skipSchedulingWhileWaxed(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (state.getValue(WAXED)) {
            ci.cancel();
        }
    }

}