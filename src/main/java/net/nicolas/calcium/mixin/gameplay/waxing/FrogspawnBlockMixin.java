package net.nicolas.calcium.mixin.gameplay.waxing;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FrogspawnBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.nicolas.calcium.event.CropWaxing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FrogspawnBlock.class)
public abstract class FrogspawnBlockMixin extends Block implements CropWaxing.Waxable {

    protected FrogspawnBlockMixin(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Shadow private static int getFrogspawnHatchDelay(RandomSource random) {
        throw new AssertionError();
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void calcium$addWaxedDefault(BlockBehaviour.Properties properties, CallbackInfo ci) {
        this.registerDefaultState(this.defaultBlockState().setValue(WAXED, false));
    }

    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WAXED);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void calcium$skipHatchWhileWaxed(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (state.getValue(WAXED)) {
            ci.cancel();
        }
    }

    @Override public void onUnwaxed(ServerLevel level, BlockPos pos, BlockState unwaxedState) {
        level.scheduleTick(pos, this, getFrogspawnHatchDelay(level.getRandom()));
    }

}