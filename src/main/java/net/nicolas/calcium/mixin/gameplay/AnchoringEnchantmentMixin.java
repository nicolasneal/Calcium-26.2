package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

    @Unique private static final double SPEED_FACTOR = 0.85;
    @Unique private static final double THICK_FLUID_SPEED_FACTOR = 0.6;
    @Unique private static final int THICK_FLUID_LEVEL = 2;
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
            int level = ModEnchantments.getAnchoringLevel(entity);
            this.calcium$anchoringCached = level > 0 && (entity.isInWater() || (level >= THICK_FLUID_LEVEL && entity.isInLava()));
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
        if (this.calcium$hasAnchoring()) {
            LivingEntity entity = (LivingEntity) (Object) this;
            double speedFactor = entity.isInLava() ? THICK_FLUID_SPEED_FACTOR : SPEED_FACTOR;
            this.travelInAir(input.scale(speedFactor));
            Vec3 movement = entity.getDeltaMovement();
            if (movement.y < SINK_SPEED_CAP) {
                entity.setDeltaMovement(movement.x, SINK_SPEED_CAP, movement.z);
            }
            ci.cancel();
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