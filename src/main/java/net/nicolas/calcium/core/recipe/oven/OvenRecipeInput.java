package net.nicolas.calcium.core.recipe.oven;

import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record OvenRecipeInput(List<ItemStack> ingredients) implements RecipeInput {

    @Override public ItemStack getItem(int slot) {
        if (slot < ingredients.size()) return ingredients.get(slot);
        return ItemStack.EMPTY;
    }

    @Override public int size() {
        return ingredients.size();
    }

}
