package net.nicolas.calcium.mixin.screens;

import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EnchantmentMenu.class)
public class EnchantmentScreenHandlerMixin {

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/EnchantmentMenu$2;<init>(Lnet/minecraft/world/inventory/EnchantmentMenu;Lnet/minecraft/world/Container;III)V"), index = 3)
    private int calcium$modifyItemSlotX(int x) {
        return 12;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/EnchantmentMenu$2;<init>(Lnet/minecraft/world/inventory/EnchantmentMenu;Lnet/minecraft/world/Container;III)V"), index = 4)
    private int calcium$modifyItemSlotY(int y) {
        return 49;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/EnchantmentMenu$3;<init>(Lnet/minecraft/world/inventory/EnchantmentMenu;Lnet/minecraft/world/Container;III)V"), index = 3)
    private int calcium$modifyLapisSlotX(int x) {
        return 34;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/EnchantmentMenu$3;<init>(Lnet/minecraft/world/inventory/EnchantmentMenu;Lnet/minecraft/world/Container;III)V"), index = 4)
    private int calcium$modifyLapisSlotY(int y) {
        return 49;
    }

}