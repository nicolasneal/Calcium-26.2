package net.nicolas.calcium.mixin.screens;

import net.minecraft.world.inventory.AbstractFurnaceMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractFurnaceMenu.class)
public class AbstractFurnaceScreenHandlerMixin {

    @ModifyArg(method = "<init>(Lnet/minecraft/world/inventory/MenuType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/inventory/RecipeBookType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/Slot;<init>(Lnet/minecraft/world/Container;III)V"), index = 2)
    private int calcium$modifyInputSlotX(int x) {
        return 48;
    }

    @ModifyArg(method = "<init>(Lnet/minecraft/world/inventory/MenuType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/inventory/RecipeBookType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/FurnaceFuelSlot;<init>(Lnet/minecraft/world/inventory/AbstractFurnaceMenu;Lnet/minecraft/world/Container;III)V"), index = 3)
    private int calcium$modifyFuelSlotX(int x) {
        return 48;
    }

    @ModifyArg(method = "<init>(Lnet/minecraft/world/inventory/MenuType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/inventory/RecipeBookType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/FurnaceResultSlot;<init>(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/Container;III)V"), index = 3)
    private int calcium$modifyOutputSlotX(int x) {
        return 109;
    }

}