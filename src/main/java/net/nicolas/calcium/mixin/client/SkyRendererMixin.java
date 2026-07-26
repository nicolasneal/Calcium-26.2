package net.nicolas.calcium.mixin.client;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.state.level.SkyRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.level.dimension.DimensionType;
import net.nicolas.calcium.client.HighAltitude;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkyRenderer.class)
public abstract class SkyRendererMixin {

    @Unique private static final float MIN_SKY_COLOR_MULTIPLIER = 0.0F;

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void calcium$applyHighAltitudeSkyEffects(ClientLevel level, float partialTicks, Camera camera, SkyRenderState state, CallbackInfo ci) {
        if (state.skybox == DimensionType.Skybox.NONE || state.skybox == DimensionType.Skybox.END) {
            return;
        }
        float altitudeFactor = HighAltitude.computeFactor(camera.position().y);
        if (altitudeFactor <= 0.0F) {
            return;
        }
        float colorMultiplier = Mth.lerp(altitudeFactor, 1.0F, MIN_SKY_COLOR_MULTIPLIER);
        state.skyColor = ARGB.scaleRGB(state.skyColor, colorMultiplier);
        state.starBrightness = Mth.lerp(altitudeFactor, state.starBrightness, 1.0F);
    }

}