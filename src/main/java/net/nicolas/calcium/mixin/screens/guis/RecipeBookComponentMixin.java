package net.nicolas.calcium.mixin.screens.guis;

import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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