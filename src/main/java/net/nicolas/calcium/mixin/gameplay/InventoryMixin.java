package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import java.util.function.Predicate;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.nicolas.calcium.player.ExtraSlotsAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Inventory.class)
public abstract class InventoryMixin {

    @Shadow @Final public Player player;

    @ModifyReturnValue(method = "clearOrCountMatchingItems", at = @At("RETURN"))
    private int calcium$clearExtraSlots(int original, Predicate<ItemStack> predicate, int amountToRemove, Container craftSlots) {
        boolean countingOnly = amountToRemove == 0;
        Container extraSlots = ((ExtraSlotsAccess) this.player).calcium$getExtraSlots();
        int cleared = ContainerHelper.clearOrCountMatchingItems(extraSlots, predicate, amountToRemove - original, countingOnly);
        // Only needed when a different menu (e.g. the Creative "Survival Inventory" tab) is open;
        // vanilla already broadcasts player.inventoryMenu itself when it's the one currently active.
        if (cleared > 0 && !countingOnly && this.player.containerMenu != this.player.inventoryMenu) {
            this.player.inventoryMenu.broadcastChanges();
        }
        return original + cleared;
    }

}