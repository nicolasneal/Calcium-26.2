package net.nicolas.calcium.mixin.client.particles;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.nicolas.calcium.core.client.particle.TranslucentParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DripParticle.class)
public abstract class DripParticleLayerMixin implements TranslucentParticle {

    @Unique private boolean calcium$translucent = false;

    @Override @Unique
    public void calcium$setTranslucent(boolean translucent) {
        this.calcium$translucent = translucent;
    }

    @ModifyReturnValue(method = "getLayer", at = @At("RETURN"))
    private SingleQuadParticle.Layer calcium$overrideLayer(SingleQuadParticle.Layer original) {
        return this.calcium$translucent ? SingleQuadParticle.Layer.TRANSLUCENT : original;
    }

}
