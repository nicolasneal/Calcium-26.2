package net.nicolas.calcium.mixin.screens;

import net.minecraft.world.inventory.HorseInventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HorseInventoryMenu.class)
public class HorseScreenHandlerMixin {

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/HorseInventoryMenu$1;<init>(Lnet/minecraft/world/inventory/HorseInventoryMenu;Lnet/minecraft/world/Container;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;IIILnet/minecraft/resources/Identifier;Lnet/minecraft/world/entity/animal/equine/AbstractHorse;)V"), index = 6)
    private int calcium$modifySaddleSlotY(int y) {
        return 17;
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/HorseInventoryMenu$2;<init>(Lnet/minecraft/world/inventory/HorseInventoryMenu;Lnet/minecraft/world/Container;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;IIILnet/minecraft/resources/Identifier;Lnet/minecraft/world/entity/animal/equine/AbstractHorse;Z)V"), index = 6)
    private int calcium$modifyArmorSlotY(int y) {
        return 35;
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/Slot;<init>(Lnet/minecraft/world/Container;III)V"), index = 3)
    private int calcium$modifyChestSlotY(int y) {
        return y - 1;
    }

}