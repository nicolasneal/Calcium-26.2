package net.nicolas.calcium.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.FogType;
import net.nicolas.calcium.core.client.environment.UnderwaterDepth;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {

    @Unique private static final float MIN_COLOR_MULTIPLIER = 0.05F;

    @ModifyExpressionValue(method = "computeFogColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getWaterVision()F"))
    private float calcium$suppressWaterFogBrightening(float waterVision) {
        return 0.0F;
    }

    @Inject(method = "computeFogColor", at = @At("TAIL"))
    private void calcium$darkenFogColorByDepth(Camera camera, float partialTicks, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f dest, CallbackInfo ci) {
        if (camera.getFluidInCamera() != FogType.WATER) {
            return;
        }
        float depthFactor = UnderwaterDepth.computeFactor(camera, level);
        if (depthFactor <= 0.0F) {
            return;
        }
        float colorMultiplier = Mth.lerp(depthFactor, 1.0F, MIN_COLOR_MULTIPLIER);
        dest.x *= colorMultiplier;
        dest.y *= colorMultiplier;
        dest.z *= colorMultiplier;
    }

}