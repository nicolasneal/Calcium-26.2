package net.nicolas.calcium.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.nicolas.calcium.fluid.ModFluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow protected boolean jumping;

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void calcium$ectoplasmPhysics(Vec3 movementInput, CallbackInfo ci) {

        LivingEntity entity = (LivingEntity) (Object) this;
        FluidState fluidState = entity.level().getFluidState(entity.blockPosition());

        if (fluidState.isEmpty()) {
            fluidState = entity.level().getFluidState(entity.blockPosition().above());
        }

        if (fluidState.getType() == ModFluids.ECTOPLASM_STILL || fluidState.getType() == ModFluids.ECTOPLASM_FLOWING) {

            entity.fallDistance = 0.0F;

            float speed = entity.isSprinting() ? 0.12F : 0.02F;
            float drag = 0.6F;
            double gravity = 0.015;

            double verticalPush = 0.0;
            if (this.jumping) {
                verticalPush = 0.03;
            } else if (movementInput.y < 0) {
                verticalPush = -0.03;
            }

            entity.moveRelative(speed, movementInput);

            if (verticalPush != 0.0) {
                entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, verticalPush, 0.0));
            }

            entity.move(net.minecraft.world.entity.MoverType.SELF, entity.getDeltaMovement());
            entity.setDeltaMovement(entity.getDeltaMovement().scale(drag));

            if (!entity.isNoGravity()) {
                entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, -gravity, 0.0));
            }

            entity.setSwimming(entity.isSprinting() && !entity.isPassenger());

            ci.cancel();

        }
    }

    @Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
    private void calcium$preventJumpInEctoplasm(CallbackInfo ci) {

        LivingEntity entity = (LivingEntity) (Object) this;
        FluidState fluidState = entity.level().getFluidState(entity.blockPosition());

        if (fluidState.isEmpty()) {
            fluidState = entity.level().getFluidState(entity.blockPosition().above());
        }

        if (fluidState.getType() == ModFluids.ECTOPLASM_STILL || fluidState.getType() == ModFluids.ECTOPLASM_FLOWING) {
            ci.cancel();
        }

    }

}