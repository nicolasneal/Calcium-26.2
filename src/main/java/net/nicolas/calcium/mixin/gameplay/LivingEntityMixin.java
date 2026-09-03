package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    private static final TagKey<EntityType<?>> IGNORES_WITHER = TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("calcium", "ignores_wither"));

    @Inject(method = "canBeAffected", at = @At("HEAD"), cancellable = true)
    private void calcium$ignoreWither(final MobEffectInstance newEffect, final CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (newEffect.is(MobEffects.WITHER) && self.is(IGNORES_WITHER)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "igniteForTicks", at = @At("HEAD"), cancellable = true)
    private void calcium$preventFireResistantIgnition(final int numberOfTicks, final CallbackInfo ci) {
        if (((LivingEntity) (Object) this).hasEffect(MobEffects.FIRE_RESISTANCE)) {
            ci.cancel();
        }
    }

    @Inject(method = "onEffectAdded", at = @At("TAIL"))
    private void calcium$extinguishOnFireResistance(final MobEffectInstance effect, final @Nullable Entity source, final CallbackInfo ci) {
        if (effect.is(MobEffects.FIRE_RESISTANCE)) {
            ((LivingEntity) (Object) this).clearFire();
        }
    }

}