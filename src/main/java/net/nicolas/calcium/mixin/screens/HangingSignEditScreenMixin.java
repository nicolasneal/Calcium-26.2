package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.screens.inventory.HangingSignEditScreen;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HangingSignEditScreen.class)
public class HangingSignEditScreenMixin {

    @ModifyConstant(method = "extractSignBackground", constant = @Constant(floatValue = 4.5F))
    private float calcium$fixHangingSignScale(float constant) {
        return 6.0F;
    }

    @Inject(method = "getSignTextScale", at = @At("HEAD"), cancellable = true)
    private void calcium$modifyTextScale(CallbackInfoReturnable<Vector3f> cir) {
        cir.setReturnValue(new Vector3f(1.33F, 1.33F, 1.33F));
    }

}