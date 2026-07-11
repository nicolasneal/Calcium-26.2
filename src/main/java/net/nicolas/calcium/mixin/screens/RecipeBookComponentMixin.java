package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Vanilla only clears a recipe book's ghost preview when the player directly clicks the previewed
// slot (RecipeBookComponent.slotClicked -> isCraftingSlot). A shift-click from the player's own
// inventory reports the source inventory slot to that check, not the destination, so the ghost
// preview is left showing even after the real item lands in the previewed slot. Polling every tick
// catches every insertion method uniformly, matching the "preview disappears once satisfied" behavior
// players expect from vanilla recipe books.
@Mixin(RecipeBookComponent.class)
public abstract class RecipeBookComponentMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void calcium$clearGhostOnRealItem(CallbackInfo ci) {
        GhostSlots ghostSlots = ((RecipeBookComponentAccessor) (Object) this).calcium$getGhostSlots();
        for (Slot slot : ((GhostSlotsAccessor) ghostSlots).calcium$getIngredients().keySet()) {
            if (slot.hasItem()) {
                ghostSlots.clear();
                break;
            }
        }
    }

}
