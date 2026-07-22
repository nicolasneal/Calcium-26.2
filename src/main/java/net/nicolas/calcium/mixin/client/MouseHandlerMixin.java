package net.nicolas.calcium.mixin.client;

import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import net.nicolas.calcium.core.client.viewfinder.ViewfinderController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {

    @Redirect(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"))
    private void calcium$redirectTurnForViewfinder(LocalPlayer player, double xo, double yo) {
        if (ViewfinderController.isActive()) {
            ViewfinderController.addMouseDelta(xo, yo);
            return;
        }
        player.turn(xo, yo);
    }

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void calcium$redirectScrollForViewfinder(long handle, double xoffset, double yoffset, CallbackInfo ci) {
        if (ViewfinderController.isActive()) {
            ViewfinderController.addZoom(yoffset);
            ci.cancel();
        }
    }

}