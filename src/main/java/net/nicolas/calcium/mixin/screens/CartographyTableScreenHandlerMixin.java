package net.nicolas.calcium.mixin.screens;

import net.minecraft.world.inventory.CartographyTableMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CartographyTableMenu.class)
public class CartographyTableScreenHandlerMixin {

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/CartographyTableMenu$3;<init>(Lnet/minecraft/world/inventory/CartographyTableMenu;Lnet/minecraft/world/Container;III)V"), index = 3)
    private int calcium$modifyMapSlotX(int x) {
        return 12;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/CartographyTableMenu$4;<init>(Lnet/minecraft/world/inventory/CartographyTableMenu;Lnet/minecraft/world/Container;III)V"), index = 3)
    private int calcium$modifyMaterialSlotX(int x) {
        return 12;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/CartographyTableMenu$4;<init>(Lnet/minecraft/world/inventory/CartographyTableMenu;Lnet/minecraft/world/Container;III)V"), index = 4)
    private int calcium$modifyMaterialSlotY(int y) {
        return 55;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/CartographyTableMenu$5;<init>(Lnet/minecraft/world/inventory/CartographyTableMenu;Lnet/minecraft/world/Container;IIILnet/minecraft/world/inventory/ContainerLevelAccess;)V"), index = 3)
    private int calcium$modifyResultSlotX(int x) {
        return 144;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/CartographyTableMenu$5;<init>(Lnet/minecraft/world/inventory/CartographyTableMenu;Lnet/minecraft/world/Container;IIILnet/minecraft/world/inventory/ContainerLevelAccess;)V"), index = 4)
    private int calcium$modifyResultSlotY(int y) {
        return 35;
    }

}