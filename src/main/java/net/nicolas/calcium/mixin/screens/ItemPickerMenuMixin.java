package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CreativeModeInventoryScreen.ItemPickerMenu.class)
public class ItemPickerMenuMixin {

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen$CustomCreativeSlot;-><init>(Lnet/minecraft/world/Container;III)V"), index = 2
    )
    private int calcium$shiftItemSlotX(int x) {
        return x - 1;
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "addInventoryHotbarSlots(Lnet/minecraft/world/Container;II)V"), index = 1)
    private int calcium$shiftHotbarX(int x) {
        return x - 1;
    }

}