package net.nicolas.calcium.mixin.client.particles;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.nicolas.calcium.core.client.environment.UnderwaterDepth;
import net.nicolas.calcium.core.client.particle.TranslucentParticle;
import net.nicolas.calcium.mixin.accessors.SingleQuadParticleAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.client.particle.SuspendedParticle$UnderwaterProvider")
public abstract class UnderwaterParticleMixin {

    private static final float UNDERWATER_PARTICLE_ALPHA = 0.4F;
    private static final float MIN_COLOR_MULTIPLIER = 0.05F;

    @Inject(
        method = "createParticle(Lnet/minecraft/core/particles/SimpleParticleType;Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDDLnet/minecraft/util/RandomSource;)Lnet/minecraft/client/particle/Particle;",
        at = @At("RETURN")
    )
    private void calcium$tintByWaterFogColor(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random, CallbackInfoReturnable<Particle> cir) {
        Particle particle = cir.getReturnValue();
        if (particle instanceof SingleQuadParticle quadParticle) {
            Camera camera = Minecraft.getInstance().gameRenderer.mainCamera();
            float partialTicks = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);
            int fogColor = camera.attributeProbe().getValue(EnvironmentAttributes.WATER_FOG_COLOR, partialTicks);
            float depthFactor = UnderwaterDepth.computeFactor(camera, level);
            float colorMultiplier = Mth.lerp(depthFactor, 1.0F, MIN_COLOR_MULTIPLIER);
            quadParticle.setColor(ARGB.redFloat(fogColor) * colorMultiplier, ARGB.greenFloat(fogColor) * colorMultiplier, ARGB.blueFloat(fogColor) * colorMultiplier);
            ((SingleQuadParticleAccessor) quadParticle).setParticleAlpha(UNDERWATER_PARTICLE_ALPHA);
            ((TranslucentParticle) quadParticle).calcium$setTranslucent(true);
        }
    }

}