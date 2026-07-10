package net.nicolas.calcium.mixin.screens;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractCraftingMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CraftingMenu.class)
public abstract class CraftingScreenHandlerMixin extends AbstractCraftingMenu {

    public CraftingScreenHandlerMixin(MenuType<?> type, int syncId, int width, int height) {
        super(type, syncId, width, height);
    }

    @Redirect(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/CraftingMenu;addCraftingGridSlots(II)V"))
    private void calcium$redirectAddInputSlots(CraftingMenu instance, int x, int y) {
        super.addCraftingGridSlots(31, 17);
    }

    @Redirect(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/CraftingMenu;addResultSlot(Lnet/minecraft/world/entity/player/Player;II)Lnet/minecraft/world/inventory/Slot;"))
    private Slot calcium$redirectAddResultSlot(CraftingMenu instance, Player player, int x, int y) {
        return super.addResultSlot(player, 125, 35);
    }

}