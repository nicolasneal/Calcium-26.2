package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.Item;
import net.nicolas.calcium.core.util.MinecartDrops;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VehicleEntity.class)
public abstract class MinecartDropMixin {

    @WrapOperation(method = "destroy(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/VehicleEntity;destroy(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/Item;)V"))
    private void calcium$dropMinecartAndBlockSeparately(VehicleEntity instance, ServerLevel level, Item dropItem, Operation<Void> original) {
        MinecartDrops.dropSeparately(instance, level, dropItem, original);
    }

}
