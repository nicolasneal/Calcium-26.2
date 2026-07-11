package net.nicolas.calcium.screen;

import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.nicolas.calcium.mixin.screens.GhostSlotsAccessor;
import net.nicolas.calcium.recipe.EnchantingRecipeDisplay;
import net.nicolas.calcium.recipe.ModRecipes;

import java.util.List;
import java.util.Optional;

public class EnchantingRecipeBookComponent extends RecipeBookComponent<CustomEnchantingScreenHandler> {

    private static final WidgetSprites FILTER_BUTTON_SPRITES = new WidgetSprites(
        Identifier.withDefaultNamespace("recipe_book/filter_enabled"),
        Identifier.withDefaultNamespace("recipe_book/filter_disabled"),
        Identifier.withDefaultNamespace("recipe_book/filter_enabled_highlighted"),
        Identifier.withDefaultNamespace("recipe_book/filter_disabled_highlighted")
    );

    private static final Component ONLY_CRAFTABLES_TOOLTIP = Component.translatable("gui.recipebook.toggleRecipes.craftable");

    private static final List<TabInfo> TABS = List.of(
        new TabInfo(new ItemStack(Items.COMPASS), Optional.empty(), EnchantingSearchCategory.ALL),
        new TabInfo(Items.IRON_PICKAXE, ModRecipes.ENCHANTING_COMMON),
        new TabInfo(Items.DIAMOND_PICKAXE, ModRecipes.ENCHANTING_SUPERIOR),
        new TabInfo(Items.NETHERITE_PICKAXE, ModRecipes.ENCHANTING_PARAGON)
    );

    private GhostSlots ghostSlotsRef;

    public EnchantingRecipeBookComponent(CustomEnchantingScreenHandler menu) {
        super(menu, TABS);
    }

    @Override protected boolean isCraftingSlot(Slot slot) {
        int index = this.menu.slots.indexOf(slot);
        return index >= 36 && index <= 47;
    }

    public boolean hasGhostItem(Slot slot) {
        return this.ghostSlotsRef != null && ((GhostSlotsAccessor) this.ghostSlotsRef).calcium$getIngredients().containsKey(slot);
    }

    @Override protected void fillGhostRecipe(GhostSlots ghostSlots, RecipeDisplay recipe, ContextMap context) {
        this.ghostSlotsRef = ghostSlots;
        if (recipe instanceof EnchantingRecipeDisplay enchantingRecipe) {
            List<Slot> ingredientSlots = this.menu.getIngredientSlots();
            List<SlotDisplay> ingredients = enchantingRecipe.ingredients();
            int count = Math.min(ingredients.size(), ingredientSlots.size());
            for (int i = 0; i < count; i++) {
                ((GhostSlotsAccessor) ghostSlots).calcium$setInput(ingredientSlots.get(i), context, ingredients.get(i));
            }

            Slot lapisSlot = this.menu.slots.get(36);
            if (lapisSlot.getItem().isEmpty()) {
                ((GhostSlotsAccessor) ghostSlots).calcium$setInput(lapisSlot, context, new SlotDisplay.ItemSlotDisplay(Items.LAPIS_LAZULI));
            }

            Slot tabletSlot = this.menu.slots.get(37);
            if (tabletSlot.getItem().isEmpty()) {
                ((GhostSlotsAccessor) ghostSlots).calcium$setInput(tabletSlot, context, enchantingRecipe.tablet());
            }

            ((GhostSlotsAccessor) ghostSlots).calcium$setResult(this.menu.getResultSlot(), context, enchantingRecipe.result());
        }
    }

    @Override protected WidgetSprites getFilterButtonTextures() {
        return FILTER_BUTTON_SPRITES;
    }

    @Override protected Component getRecipeFilterName() {
        return ONLY_CRAFTABLES_TOOLTIP;
    }

    @Override protected void selectMatchingRecipes(RecipeCollection collection, StackedItemContents stackedContents) {
        collection.selectRecipes(stackedContents, display -> display instanceof EnchantingRecipeDisplay);
    }

}