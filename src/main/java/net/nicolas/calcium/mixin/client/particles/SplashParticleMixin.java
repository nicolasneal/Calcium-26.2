package net.nicolas.calcium.mixin.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashParticle.class)
public abstract class SplashParticleMixin extends WaterDropParticle {

    private static final float SPLASH_PARTICLE_ALPHA = 0.4F;

    protected SplashParticleMixin(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
        super(level, x, y, z, sprite);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void calcium$tintWithBiomeWaterColor(ClientLevel level, double x, double y, double z, double xa, double ya, double za, TextureAtlasSprite sprite, CallbackInfo ci) {
        int color = BiomeColors.getAverageWaterColor(level, BlockPos.containing(x, y, z));
        this.rCol = ((color >> 16) & 0xFF) / 255.0F;
        this.gCol = ((color >> 8) & 0xFF) / 255.0F;
        this.bCol = (color & 0xFF) / 255.0F;
        this.setAlpha(SPLASH_PARTICLE_ALPHA);
    }

}