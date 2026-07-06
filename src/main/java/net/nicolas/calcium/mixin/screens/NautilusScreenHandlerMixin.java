package net.nicolas.calcium.mixin.screens;

import net.minecraft.world.inventory.NautilusInventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(NautilusInventoryMenu.class)
public class NautilusScreenHandlerMixin {

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/NautilusInventoryMenu$1;<init>(Lnet/minecraft/world/inventory/NautilusInventoryMenu;Lnet/minecraft/world/Container;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;IIILnet/minecraft/resources/Identifier;Lnet/minecraft/world/entity/animal/nautilus/AbstractNautilus;)V"), index = 6)
    private int calcium$modifySaddleSlotY(int y) {
        return 17;
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/NautilusInventoryMenu$2;<init>(Lnet/minecraft/world/inventory/NautilusInventoryMenu;Lnet/minecraft/world/Container;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;IIILnet/minecraft/resources/Identifier;Lnet/minecraft/world/entity/animal/nautilus/AbstractNautilus;)V"), index = 6)
    private int calcium$modifyArmorSlotY(int y) {
        return 35;
    }

}