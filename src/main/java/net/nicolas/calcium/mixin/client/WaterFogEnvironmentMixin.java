package net.nicolas.calcium.mixin.client;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.WaterFogEnvironment;
import net.minecraft.util.Mth;
import net.nicolas.calcium.client.UnderwaterDepth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WaterFogEnvironment.class)
public abstract class WaterFogEnvironmentMixin {

    @Unique private static final float MIN_DISTANCE_MULTIPLIER = 0.3F;

    @Inject(method = "setupFog", at = @At("TAIL"))
    private void calcium$scaleFogDistanceByDepth(FogData fog, Camera camera, ClientLevel level, float renderDistance, DeltaTracker deltaTracker, CallbackInfo ci) {
        float depthFactor = UnderwaterDepth.computeFactor(camera, level);
        float distanceMultiplier = Mth.lerp(depthFactor, 1.0F, MIN_DISTANCE_MULTIPLIER);
        fog.environmentalStart *= distanceMultiplier;
        fog.environmentalEnd *= distanceMultiplier;
        fog.skyEnd = fog.environmentalEnd;
        fog.cloudEnd = fog.environmentalEnd;
    }

}