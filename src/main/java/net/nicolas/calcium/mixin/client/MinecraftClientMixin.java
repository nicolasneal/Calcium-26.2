package net.nicolas.calcium.mixin.client;

import com.mojang.blaze3d.platform.FramerateLimitTracker;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.nicolas.calcium.client.ViewfinderController;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {

    // Unlimit FPS on Main Menu

    @Shadow @Final public Options options;
    @Redirect(method = "renderFrame", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/FramerateLimitTracker;getFramerateLimit()I"))
    private int unlockTitleFps(FramerateLimitTracker instance) {
        return this.options.framerateLimit().get();
    }

    // Remove Buttons and Chat While Sleeping

    @Shadow public LocalPlayer player;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {

        Minecraft client = (Minecraft) (Object) this;
        LocalPlayer player = client.player;

        if (player != null && player.isSleeping()) {

            boolean sneakPressed = InputConstants.isKeyDown(client.getWindow(), InputConstants.getKey(client.options.keyShift.saveString()).getValue());
            boolean perspectivePressed = client.options.keyTogglePerspective.isDown();

            for (KeyMapping key : client.options.keyMappings) {
                key.setDown(false);
            }
            if (sneakPressed) {
                Objects.requireNonNull(client.getConnection()).send(
                        new ServerboundPlayerCommandPacket(player, ServerboundPlayerCommandPacket.Action.STOP_SLEEPING)
                );
            }

            client.options.keyShift.setDown(sneakPressed);
            client.options.keyTogglePerspective.setDown(perspectivePressed);
            client.options.keyAttack.setDown(false);
            client.options.keyUse.setDown(false);

        } else if (ViewfinderController.isActive()) {

            for (KeyMapping key : client.options.keyMappings) {
                key.setDown(false);
            }
            while (client.options.keyTogglePerspective.consumeClick()) {
            }

        }

    }

    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void onDoAttack(CallbackInfoReturnable<Boolean> cir) {
        Minecraft client = (Minecraft) (Object) this;
        if ((client.player != null && client.player.isSleeping()) || ViewfinderController.isActive()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "startUseItem", at = @At("HEAD"), cancellable = true)
    private void onDoItemUse(CallbackInfo ci) {
        Minecraft client = (Minecraft) (Object) this;
        if ((client.player != null && client.player.isSleeping()) || ViewfinderController.isActive()) {
            ci.cancel();
        }
    }

}