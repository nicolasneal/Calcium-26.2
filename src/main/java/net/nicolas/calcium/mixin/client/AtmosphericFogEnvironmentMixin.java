package net.nicolas.calcium.mixin.client;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.renderer.fog.environment.AtmosphericFogEnvironment.class)
public abstract class AtmosphericFogEnvironmentMixin {
    
    @Inject(method = "setupFog", at = @At("TAIL"))
    private void calcium$overrideSkyFogEnd(FogData fog, Camera camera, ClientLevel level, float renderDistance, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (fog.skyEnd == renderDistance) {
            fog.skyEnd = 160.0F;
        }
    }

}