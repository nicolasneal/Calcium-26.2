package net.nicolas.calcium.mixin.screens;

import net.minecraft.world.inventory.LoomMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LoomMenu.class)
public class LoomScreenHandlerMixin {

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/LoomMenu$3;<init>(Lnet/minecraft/world/inventory/LoomMenu;Lnet/minecraft/world/Container;III)V"), index = 3)
    private int calcium$modifyBannerSlotX(int x) {
        return 12;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/LoomMenu$3;<init>(Lnet/minecraft/world/inventory/LoomMenu;Lnet/minecraft/world/Container;III)V"), index = 4)
    private int calcium$modifyBannerSlotY(int y) {
        return 25;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/LoomMenu$4;<init>(Lnet/minecraft/world/inventory/LoomMenu;Lnet/minecraft/world/Container;III)V"), index = 3)
    private int calcium$modifyDyeSlotX(int x) {
        return 32;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/LoomMenu$4;<init>(Lnet/minecraft/world/inventory/LoomMenu;Lnet/minecraft/world/Container;III)V"), index = 4)
    private int calcium$modifyDyeSlotY(int y) {
        return 25;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/LoomMenu$5;<init>(Lnet/minecraft/world/inventory/LoomMenu;Lnet/minecraft/world/Container;III)V"), index = 3)
    private int calcium$modifyPatternSlotX(int x) {
        return 22;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/LoomMenu$6;<init>(Lnet/minecraft/world/inventory/LoomMenu;Lnet/minecraft/world/Container;IIILnet/minecraft/world/inventory/ContainerLevelAccess;)V"), index = 3)
    private int calcium$modifyOutputSlotX(int x) {
        return 144;
    }

}