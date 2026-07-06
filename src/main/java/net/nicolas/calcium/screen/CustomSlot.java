package net.nicolas.calcium.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class CustomSlot extends Slot {

    private final SlotConfig.ItemMode itemMode;
    private final SlotConfig.StackMode stackMode;
    private final Item fixedItem;
    private final TagKey<Item> allowedTag;
    private final Component tooltip;

    private CustomSlot(Container inventory, int index, int x, int y, SlotConfig.ItemMode itemMode, SlotConfig.StackMode stackMode, Item fixedItem, TagKey<Item> allowedTag, RecipeType<?> recipeType, Component tooltip) {
        super(inventory, index, x, y);
        this.itemMode = itemMode;
        this.stackMode = stackMode;
        this.fixedItem = fixedItem;
        this.allowedTag = allowedTag;
        this.tooltip = tooltip;
    }

    @Override public boolean mayPlace(ItemStack stack) {
        return switch (itemMode) {
            case ALL -> true;
            case FIXED -> stack.getItem() == fixedItem;
            case TAG -> stack.is(allowedTag);
            case RECIPE -> false;
        };
    }

    @Override public int getMaxStackSize(ItemStack stack) {
        return (stackMode == SlotConfig.StackMode.SINGLE) ? 1 : super.getMaxStackSize(stack);
    }

    public Component getTooltip() {
        return tooltip;
    }

    public static class Builder {

        private final Container inventory;
        private final int index;
        private final int x;
        private final int y;

        private SlotConfig.ItemMode itemMode = SlotConfig.ItemMode.ALL;
        private SlotConfig.StackMode stackMode = SlotConfig.StackMode.STACK;
        private Item fixedItem = null;
        private TagKey<Item> allowedTag = null;
        private RecipeType<?> recipeType = null;
        private Component tooltip = null;

        public Builder(Container inventory, int index, int x, int y) {
            this.inventory = inventory;
            this.index = index;
            this.x = x;
            this.y = y;
        }

        public Builder itemMode(SlotConfig.ItemMode itemMode) {
            this.itemMode = itemMode;
            return this;
        }

        public Builder stackMode(SlotConfig.StackMode stackMode) {
            this.stackMode = stackMode;
            return this;
        }

        public Builder fixedItem(Item fixedItem) {
            this.fixedItem = fixedItem;
            return this;
        }

        public Builder allowedTag(TagKey<Item> allowedTag) {
            this.allowedTag = allowedTag;
            return this;
        }

        public Builder recipeType(RecipeType<?> recipeType) {
            this.recipeType = recipeType;
            return this;
        }

        public Builder tooltip(Component tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public CustomSlot build() {
            return new CustomSlot(inventory, index, x, y, itemMode, stackMode, fixedItem, allowedTag, recipeType, tooltip);
        }

    }

}