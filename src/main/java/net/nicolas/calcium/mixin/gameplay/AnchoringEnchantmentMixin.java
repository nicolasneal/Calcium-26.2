package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import net.nicolas.calcium.item.ModEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class AnchoringEnchantmentMixin extends Entity {

    @Unique private static final Identifier SPEED_MODIFIER_ID = Identifier.fromNamespaceAndPath("calcium", "anchoring_speed");
    @Unique private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(SPEED_MODIFIER_ID, -0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    @Unique private static final double FALL_GRAVITY_MULTIPLIER = 0.4;
    @Unique private static final double JUMP_SPEED_MULTIPLIER = 0.8;
    @Unique private static final double JUMP_GRAVITY_MULTIPLIER = JUMP_SPEED_MULTIPLIER * JUMP_SPEED_MULTIPLIER;
    @Unique private static final double SINK_SPEED_CAP = -0.25;

    @Unique private int calcium$anchoringCheckTick = -1;
    @Unique private boolean calcium$anchoringCached;

    private AnchoringEnchantmentMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow private void travelInAir(Vec3 input) {}

    @Unique private boolean calcium$hasAnchoring() {
        if (this.calcium$anchoringCheckTick != this.tickCount) {
            LivingEntity entity = (LivingEntity) (Object) this;
            this.calcium$anchoringCached = entity.isInWater() && ModEnchantments.getAnchoringLevel(entity) > 0;
            this.calcium$anchoringCheckTick = this.tickCount;
        }
        return this.calcium$anchoringCached;
    }

    @ModifyReturnValue(method = "getEffectiveGravity", at = @At("RETURN"))
    private double calcium$anchoringGravity(double original) {
        if (this.calcium$hasAnchoring()) {
            return this.getDeltaMovement().y <= 0.0 ? original * FALL_GRAVITY_MULTIPLIER : original * JUMP_GRAVITY_MULTIPLIER;
        }
        return original;
    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void calcium$anchoringTravel(Vec3 input, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (this.calcium$hasAnchoring()) {
            if (speed != null) {
                speed.addOrUpdateTransientModifier(SPEED_MODIFIER);
            }
            this.travelInAir(input);
            Vec3 movement = entity.getDeltaMovement();
            if (movement.y < SINK_SPEED_CAP) {
                entity.setDeltaMovement(movement.x, SINK_SPEED_CAP, movement.z);
            }
            ci.cancel();
        }
        else {
            if (speed != null) {
                speed.removeModifier(SPEED_MODIFIER_ID);
            }
        }
    }

    @Override public void updateSwimming() {
        if (this.calcium$hasAnchoring()) {
            this.setSwimming(false);
        }
        else {
            super.updateSwimming();
        }
    }

    @Override public double getFluidJumpThreshold() {
        if (this.calcium$hasAnchoring()) {
            return Double.MAX_VALUE;
        }
        return super.getFluidJumpThreshold();
    }

    @Inject(method = "jumpFromGround", at = @At("TAIL"))
    private void calcium$anchoringJumpSpeed(CallbackInfo ci) {
        if (this.calcium$hasAnchoring()) {
            Vec3 movement = this.getDeltaMovement();
            this.setDeltaMovement(movement.x, movement.y * JUMP_SPEED_MULTIPLIER, movement.z);
        }
    }

    @Inject(method = "jumpInLiquid", at = @At("HEAD"), cancellable = true)
    private void calcium$preventLiquidJump(TagKey<Fluid> type, CallbackInfo ci) {
        if (this.calcium$hasAnchoring()) {
            ci.cancel();
        }
    }

    @Override public boolean canSpawnSprintParticle() {
        if (this.calcium$hasAnchoring()) {
            return this.isSprinting() && !this.isSpectator() && !this.isCrouching() && !this.isInLava() && this.isAlive();
        }
        return super.canSpawnSprintParticle();
    }

}