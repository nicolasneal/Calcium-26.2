package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.world.level.block.PitcherCropBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PitcherCropBlock.class)
public abstract class PitcherCropDoubleStageMixin {

    @Inject(method = "isDouble", at = @At("HEAD"), cancellable = true)
    private static void calcium$doubleFromStageTwo(int age, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(age >= 2);
    }

}
