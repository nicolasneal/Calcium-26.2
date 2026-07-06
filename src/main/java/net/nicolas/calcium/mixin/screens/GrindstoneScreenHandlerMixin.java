package net.nicolas.calcium.mixin.screens;

import net.minecraft.world.inventory.GrindstoneMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GrindstoneMenu.class)
public class GrindstoneScreenHandlerMixin {

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/GrindstoneMenu$2;<init>(Lnet/minecraft/world/inventory/GrindstoneMenu;Lnet/minecraft/world/Container;III)V"), index = 3)
    private int calcium$modifyInput1X(int x) {
        return 48;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/GrindstoneMenu$2;<init>(Lnet/minecraft/world/inventory/GrindstoneMenu;Lnet/minecraft/world/Container;III)V"), index = 4)
    private int calcium$modifyInput1Y(int y) {
        return 24;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/GrindstoneMenu$3;<init>(Lnet/minecraft/world/inventory/GrindstoneMenu;Lnet/minecraft/world/Container;III)V"), index = 3)
    private int calcium$modifyInput2X(int x) {
        return 48;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/GrindstoneMenu$3;<init>(Lnet/minecraft/world/inventory/GrindstoneMenu;Lnet/minecraft/world/Container;III)V"), index = 4)
    private int calcium$modifyInput2Y(int y) {
        return 46;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/GrindstoneMenu$4;<init>(Lnet/minecraft/world/inventory/GrindstoneMenu;Lnet/minecraft/world/Container;IIILnet/minecraft/world/inventory/ContainerLevelAccess;)V"), index = 3)
    private int calcium$modifyResultX(int x) {
        return 107;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/GrindstoneMenu$4;<init>(Lnet/minecraft/world/inventory/GrindstoneMenu;Lnet/minecraft/world/Container;IIILnet/minecraft/world/inventory/ContainerLevelAccess;)V"), index = 4)
    private int calcium$modifyResultY(int y) {
        return 35;
    }

}