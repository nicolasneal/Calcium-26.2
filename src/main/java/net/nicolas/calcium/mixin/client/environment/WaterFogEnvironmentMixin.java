package net.nicolas.calcium.mixin.client.environment;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.WaterFogEnvironment;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.nicolas.calcium.core.client.environment.UnderwaterDepth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WaterFogEnvironment.class)
public abstract class WaterFogEnvironmentMixin {

    @Unique private static final float MIN_DISTANCE_MULTIPLIER = 0.3F;
    @Unique private static final float BLUE_STRENGTH = 0.4F;

    @Inject(method = "setupFog", at = @At("TAIL"))
    private void calcium$scaleFogDistanceByDepth(FogData fog, Camera camera, ClientLevel level, float renderDistance, DeltaTracker deltaTracker, CallbackInfo ci) {
        float depthFactor = UnderwaterDepth.computeFactor(camera, level);
        float distanceMultiplier = Mth.lerp(depthFactor, 1.0F, MIN_DISTANCE_MULTIPLIER);
        fog.environmentalStart *= distanceMultiplier;
        fog.environmentalEnd *= distanceMultiplier;
        fog.skyEnd = fog.environmentalEnd;
        fog.cloudEnd = fog.environmentalEnd;
    }

    @ModifyReturnValue(method = "getBaseColor", at = @At("RETURN"))
    private int calcium$scaleColorByDaylight(int color, ClientLevel level, Camera camera, int renderDistance, float partialTicks) {
        float daylightFactor = camera.attributeProbe().getValue(EnvironmentAttributes.SKY_LIGHT_FACTOR, partialTicks);
        float multiplier = Mth.lerp(1.0F, 1.0F, daylightFactor);
        float blueMultiplier = 1.0F - (1.0F - multiplier) * (1.0F - BLUE_STRENGTH);
        return ARGB.colorFromFloat(
            ARGB.alphaFloat(color),
            ARGB.redFloat(color) * multiplier,
            ARGB.greenFloat(color) * multiplier,
            ARGB.blueFloat(color) * blueMultiplier
        );
    }

}