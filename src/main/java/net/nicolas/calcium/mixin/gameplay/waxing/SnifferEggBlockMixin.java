package net.nicolas.calcium.mixin.gameplay.waxing;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnifferEggBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.nicolas.calcium.event.CropWaxing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnifferEggBlock.class)
public abstract class SnifferEggBlockMixin extends Block implements CropWaxing.Waxable {

    protected SnifferEggBlockMixin(BlockBehaviour.Properties properties) {
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
    private void calcium$skipHatchWhileWaxed(BlockState state, ServerLevel level, BlockPos position, RandomSource random, CallbackInfo ci) {
        if (state.getValue(WAXED)) {
            boolean boosted = SnifferEggBlock.hatchBoost(level, position);
            int progressionTickDelay = (boosted ? 12000 : 24000) / 3;
            level.scheduleTick(position, this, progressionTickDelay + random.nextInt(300));
            ci.cancel();
        }
    }

}
