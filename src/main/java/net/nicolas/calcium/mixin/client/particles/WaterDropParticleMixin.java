package net.nicolas.calcium.mixin.client.particles;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WaterDropParticle.class)
public abstract class WaterDropParticleMixin extends SingleQuadParticle {

    private static final float RAIN_SPLASH_PARTICLE_ALPHA = 0.4F;

    protected WaterDropParticleMixin(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
        super(level, x, y, z, sprite);
    }

    @ModifyReturnValue(method = "getLayer", at = @At("RETURN"))
    private SingleQuadParticle.Layer calcium$overrideLayer(SingleQuadParticle.Layer original) {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void calcium$matchSplashTransparency(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite, CallbackInfo ci) {
        this.setAlpha(RAIN_SPLASH_PARTICLE_ALPHA);
    }

}
