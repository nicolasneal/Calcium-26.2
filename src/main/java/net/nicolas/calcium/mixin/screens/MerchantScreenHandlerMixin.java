package net.nicolas.calcium.mixin.screens;

import net.minecraft.world.inventory.MerchantMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MerchantMenu.class)
public class MerchantScreenHandlerMixin {

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/trading/Merchant;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/Slot;<init>(Lnet/minecraft/world/Container;III)V", ordinal = 0), index = 2)
    private int calcium$modifyInput1X(int x) {
        return 142;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/trading/Merchant;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/Slot;<init>(Lnet/minecraft/world/Container;III)V", ordinal = 0), index = 3)
    private int calcium$modifyInput1Y(int y) {
        return 44;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/trading/Merchant;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/Slot;<init>(Lnet/minecraft/world/Container;III)V", ordinal = 1), index = 2)
    private int calcium$modifyInput2X(int x) {
        return 164;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/trading/Merchant;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/Slot;<init>(Lnet/minecraft/world/Container;III)V", ordinal = 1), index = 3)
    private int calcium$modifyInput2Y(int y) {
        return 44;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/trading/Merchant;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/MerchantResultSlot;<init>(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/trading/Merchant;Lnet/minecraft/world/inventory/MerchantContainer;III)V"), index = 4)
    private int calcium$modifyOutputX(int x) {
        return 222;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/trading/Merchant;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/MerchantResultSlot;<init>(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/trading/Merchant;Lnet/minecraft/world/inventory/MerchantContainer;III)V"), index = 5)
    private int calcium$modifyOutputY(int y) {
        return 44;
    }

    @ModifyArg(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/trading/Merchant;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/MerchantMenu;addStandardInventorySlots(Lnet/minecraft/world/Container;II)V"), index = 1)
    private int calcium$modifyPlayerSlotsX(int x) {
        return 112;
    }

}