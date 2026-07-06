package net.nicolas.calcium.mixin.screens;

import net.minecraft.world.inventory.StonecutterMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(StonecutterMenu.class)
public class StonecutterScreenHandlerMixin {

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/Slot;<init>(Lnet/minecraft/world/Container;III)V"), index = 2)
    private int calcium$modifyInputSlotX(int x) {
        return 17;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/Slot;<init>(Lnet/minecraft/world/Container;III)V"), index = 3)
    private int calcium$modifyInputSlotY(int y) {
        return 35;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/StonecutterMenu$2;<init>(Lnet/minecraft/world/inventory/StonecutterMenu;Lnet/minecraft/world/Container;IIILnet/minecraft/world/inventory/ContainerLevelAccess;)V"), index = 4)
    private int calcium$modifyOutputSlotY(int y) {
        return 35;
    }

}