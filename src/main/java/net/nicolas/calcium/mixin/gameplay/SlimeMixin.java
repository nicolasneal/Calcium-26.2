package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.monster.cubemob.Slime;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slime.class)
public abstract class SlimeMixin {

    @Unique
    private static ParticleOptions calcium$slimeBlockParticle;

    @Inject(method = "getParticleType", at = @At("HEAD"), cancellable = true)
    private void calcium$useSlimeBlockParticle(final CallbackInfoReturnable<ParticleOptions> cir) {
        if (calcium$slimeBlockParticle == null) {
            calcium$slimeBlockParticle = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SLIME_BLOCK.defaultBlockState());
        }
        cir.setReturnValue(calcium$slimeBlockParticle);
    }
}