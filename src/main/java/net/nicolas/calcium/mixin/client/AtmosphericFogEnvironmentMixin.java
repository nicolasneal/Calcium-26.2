package net.nicolas.calcium.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.nicolas.calcium.core.client.environment.HighAltitude;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.renderer.fog.environment.AtmosphericFogEnvironment.class)
public abstract class AtmosphericFogEnvironmentMixin {

    @Unique private static final float MIN_SKY_FOG_COLOR_MULTIPLIER = 0.05F;

    @Inject(method = "setupFog", at = @At("TAIL"))
    private void calcium$overrideSkyFogEnd(FogData fog, Camera camera, ClientLevel level, float renderDistance, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (fog.skyEnd == renderDistance) {
            fog.skyEnd = 160.0F;
        }
    }

    @ModifyReturnValue(method = "getBaseColor", at = @At("RETURN"))
    private int calcium$darkenSkyFogByAltitude(int color, ClientLevel level, Camera camera, int renderDistance, float partialTicks) {
        float altitudeFactor = HighAltitude.computeFactor(camera.position().y);
        if (altitudeFactor <= 0.0F) {
            return color;
        }
        float colorMultiplier = Mth.lerp(altitudeFactor, 1.0F, MIN_SKY_FOG_COLOR_MULTIPLIER);
        return ARGB.scaleRGB(color, colorMultiplier);
    }

}