package net.nicolas.calcium.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.nicolas.calcium.core.client.particle.TranslucentParticle;
import net.nicolas.calcium.mixin.accessors.SingleQuadParticleAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({
    DripParticle.WaterFallProvider.class,
    DripParticle.WaterHangProvider.class,
    DripParticle.DripstoneWaterFallProvider.class,
    DripParticle.DripstoneWaterHangProvider.class
})
public abstract class DripParticleMixin {

    private static final float DRIP_PARTICLE_ALPHA = 0.4F;

    @Inject(
        method = "createParticle(Lnet/minecraft/core/particles/SimpleParticleType;Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDDLnet/minecraft/util/RandomSource;)Lnet/minecraft/client/particle/Particle;",
        at = @At("RETURN")
    )
    private void calcium$tintWithBiomeWaterColor(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random, CallbackInfoReturnable<Particle> cir) {
        if (cir.getReturnValue() instanceof DripParticle dripParticle) {
            int color = BiomeColors.getAverageWaterColor(level, BlockPos.containing(x, y, z));
            dripParticle.setColor(((color >> 16) & 0xFF) / 255.0F, ((color >> 8) & 0xFF) / 255.0F, (color & 0xFF) / 255.0F);
            ((SingleQuadParticleAccessor) dripParticle).setParticleAlpha(DRIP_PARTICLE_ALPHA);
            ((TranslucentParticle) dripParticle).calcium$setTranslucent(true);
        }
    }

}
