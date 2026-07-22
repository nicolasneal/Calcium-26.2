package net.nicolas.calcium.screen.oven;

import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.nicolas.calcium.mixin.screens.GhostSlotsAccessor;
import net.nicolas.calcium.core.recipe.ModRecipes;
import net.nicolas.calcium.core.recipe.oven.OvenRecipeDisplay;

import java.util.List;
import java.util.Optional;

public class OvenRecipeBookComponent extends RecipeBookComponent<OvenScreenHandler> {

    private static final WidgetSprites FILTER_BUTTON_SPRITES = new WidgetSprites(
        Identifier.withDefaultNamespace("recipe_book/furnace_filter_enabled"),
        Identifier.withDefaultNamespace("recipe_book/furnace_filter_disabled"),
        Identifier.withDefaultNamespace("recipe_book/furnace_filter_enabled_highlighted"),
        Identifier.withDefaultNamespace("recipe_book/furnace_filter_disabled_highlighted")
    );

    private static final Component ONLY_CRAFTABLES_TOOLTIP = Component.translatable("gui.recipebook.toggleRecipes.craftable");

    private static final List<TabInfo> TABS = List.of(
        new TabInfo(new ItemStack(Items.COMPASS), Optional.empty(), OvenSearchCategory.ALL),
        new TabInfo(Items.COOKED_BEEF, ModRecipes.COOKING_SIMPLE),
        new TabInfo(Items.CAKE, ModRecipes.COOKING_COMPLEX),
        new TabInfo(Items.SUSPICIOUS_STEW, ModRecipes.COOKING_SUSPICIOUS_STEW)
    );

    public OvenRecipeBookComponent(OvenScreenHandler menu) {
        super(menu, TABS);
    }

    @Override protected boolean isCraftingSlot(Slot slot) {
        int index = this.menu.slots.indexOf(slot);
        return index >= OvenScreenHandler.SLOT_INGREDIENT_0 && index <= OvenScreenHandler.SLOT_FUEL;
    }

    @Override protected void fillGhostRecipe(GhostSlots ghostSlots, RecipeDisplay recipe, ContextMap context) {

        if (recipe instanceof OvenRecipeDisplay ovenRecipe) {

            List<Slot> ingredientSlots = this.menu.getIngredientSlots();
            List<SlotDisplay> ingredients = ovenRecipe.ingredients();
            int count = Math.min(ingredients.size(), ingredientSlots.size());
            for (int i = 0; i < count; i++) {
                ((GhostSlotsAccessor) ghostSlots).calcium$setInput(ingredientSlots.get(i), context, ingredients.get(i));
            }

            Slot fuelSlot = this.menu.slots.get(OvenScreenHandler.SLOT_FUEL);
            if (fuelSlot.getItem().isEmpty()) {
                ((GhostSlotsAccessor) ghostSlots).calcium$setInput(fuelSlot, context, ovenRecipe.fuel());
            }

            ((GhostSlotsAccessor) ghostSlots).calcium$setResult(this.menu.getResultSlot(), context, ovenRecipe.result());
        }
    }

    @Override protected WidgetSprites getFilterButtonTextures() {
        return FILTER_BUTTON_SPRITES;
    }

    @Override protected Component getRecipeFilterName() {
        return ONLY_CRAFTABLES_TOOLTIP;
    }

    @Override protected void selectMatchingRecipes(RecipeCollection collection, StackedItemContents stackedContents) {
        collection.selectRecipes(stackedContents, display -> display instanceof OvenRecipeDisplay);
    }

}