package net.nicolas.calcium.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.nicolas.calcium.state.CakeEatingState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public class CakeBurpSoundMixin {

    @Inject(method = "playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V", at = @At("HEAD"), cancellable = true)
    private void calcium$fixCakeBurpSound(double x, double y, double z, SoundEvent sound, SoundSource source, float volume, float pitch, boolean distanceDelay, CallbackInfo ci) {
        if (!CakeEatingState.isInProgress() || sound != SoundEvents.PLAYER_BURP) {
            return;
        }
        ci.cancel();
        Player player = Minecraft.getInstance().player;
        if (player != null && player.canEat(false)) {
            ((ClientLevel) (Object) this).playLocalSound(x, y, z, SoundEvents.GENERIC_EAT.value(), source, volume, pitch, distanceDelay);
        }
    }

}
