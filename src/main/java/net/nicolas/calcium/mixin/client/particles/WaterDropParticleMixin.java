package net.nicolas.calcium.mixin.client.particles;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.WaterDropParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WaterDropParticle.class)
public abstract class WaterDropParticleMixin {

    @ModifyReturnValue(method = "getLayer", at = @At("RETURN"))
    private SingleQuadParticle.Layer calcium$overrideLayer(SingleQuadParticle.Layer original) {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

}
