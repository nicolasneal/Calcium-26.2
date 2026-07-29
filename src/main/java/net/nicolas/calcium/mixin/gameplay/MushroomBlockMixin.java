package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MushroomBlock.class)
public abstract class MushroomBlockMixin {

    @Invoker("mayPlaceOn") abstract boolean calcium$mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos);

    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void calcium$ignoreLightRequirement(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockPos belowPos = pos.below();
        cir.setReturnValue(this.calcium$mayPlaceOn(level.getBlockState(belowPos), level, belowPos));
    }

}
