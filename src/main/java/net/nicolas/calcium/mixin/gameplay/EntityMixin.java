package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.nicolas.calcium.fluid.ModFluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow protected boolean firstTick;
    @Shadow @Final protected RandomSource random;

    @Shadow protected SoundEvent getSwimSplashSound() { return null; }
    @Shadow protected SoundEvent getSwimHighSpeedSplashSound() { return null; }

    @Unique private boolean calcium$wasTouchingEctoplasm;

    @Inject(method = "updateFluidInteraction", at = @At("RETURN"))
    private void calcium$ectoplasmSplash(CallbackInfoReturnable<Boolean> cir) {

        Entity self = (Entity) (Object) this;
        FluidState fluidState = self.level().getFluidState(self.blockPosition());
        boolean inEctoplasm = fluidState.getType() == ModFluids.ECTOPLASM_STILL || fluidState.getType() == ModFluids.ECTOPLASM_FLOWING;

        if (inEctoplasm && !this.calcium$wasTouchingEctoplasm && !this.firstTick) {
            this.calcium$ectoplasmSplashEffect(self);
        }

        this.calcium$wasTouchingEctoplasm = inEctoplasm;

    }

    @Unique private void calcium$ectoplasmSplashEffect(Entity self) {

        Entity entity = self.getControllingPassenger() != null ? self.getControllingPassenger() : self;
        float volumeModifier = entity == self ? 0.2F : 0.9F;
        Vec3 movement = entity.getDeltaMovement();
        float speed = Math.min(1.0F, (float) Math.sqrt(movement.x * movement.x * 0.2F + movement.y * movement.y + movement.z * movement.z * 0.2F) * volumeModifier);

        if (speed < 0.25F) {
            self.playSound(this.getSwimSplashSound(), speed, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
        } else {
            self.playSound(this.getSwimHighSpeedSplashSound(), speed, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
        }

        float yt = Mth.floor(self.getY());
        float width = self.getBbWidth();

        for (int i = 0; i < 2.0F + width * 40.0F; i++) {
            double xo = (this.random.nextDouble() * 2.0 - 1.0) * width;
            double zo = (this.random.nextDouble() * 2.0 - 1.0) * width;
            self.level().addParticle(ParticleTypes.SOUL, self.getX() + xo, yt + 1.0F, self.getZ() + zo, 0.0, 0.0, 0.0);
        }

        self.gameEvent(GameEvent.SPLASH);

    }

}