package net.nicolas.calcium.core.recipe;

import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record EnchantingRecipeInput(ItemStack tablet, List<ItemStack> ingredients) implements RecipeInput {

    @Override public ItemStack getItem(int slot) {
        if (slot == 0) return tablet;
        if (slot - 1 < ingredients.size()) return ingredients.get(slot - 1);
        return ItemStack.EMPTY;
    }

    @Override public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return tablet.isEmpty() && ingredients.isEmpty();
    }

}