package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({DoorBlock.class, LanternBlock.class})
public abstract class NoSupportRequiredMixin {

    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void calcium$noSupportNeeded(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof LanternBlock && state.getValue(LanternBlock.HANGING)) {
            return;
        }
        cir.setReturnValue(true);
    }

}