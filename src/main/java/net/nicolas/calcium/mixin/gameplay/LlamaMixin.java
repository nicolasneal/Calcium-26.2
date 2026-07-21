package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.world.entity.animal.equine.Llama;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Llama.class)
public abstract class LlamaMixin {

    @Inject(method = "getStrength", at = @At("HEAD"), cancellable = true)
    private void calcium$forceStrength(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(3);
    }

}