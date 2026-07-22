package net.nicolas.calcium.screen.oven;

import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.nicolas.calcium.core.Calcium;
import net.nicolas.calcium.block.entity.OvenBlockEntity;
import net.nicolas.calcium.core.recipe.oven.OvenRecipe;
import net.nicolas.calcium.core.recipe.oven.OvenRecipeInput;

import java.util.List;
import java.util.stream.Stream;

public class OvenScreenHandler extends RecipeBookMenu {

    public static final int SLOT_INGREDIENT_0 = 0;
    public static final int SLOT_INGREDIENT_1 = 1;
    public static final int SLOT_INGREDIENT_2 = 2;
    public static final int SLOT_INGREDIENT_3 = 3;
    public static final int SLOT_FUEL = 4;
    public static final int SLOT_RESULT = 5;
    private static final int INV_SLOT_START = 6;
    private static final int INV_SLOT_END = 33;
    private static final int USE_ROW_SLOT_END = 42;

    private final Container container;
    private final ContainerData data;
    private final Level level;

    public OvenScreenHandler(int containerId, Inventory inventory) {
        this(containerId, inventory, new SimpleContainer(OvenBlockEntity.CONTAINER_SIZE), new SimpleContainerData(OvenBlockEntity.NUM_DATA_VALUES));
    }

    public OvenScreenHandler(int containerId, Inventory inventory, Container container, ContainerData data) {

        super(Calcium.OVEN_MENU, containerId);
        checkContainerSize(container, OvenBlockEntity.CONTAINER_SIZE);
        checkContainerDataCount(data, OvenBlockEntity.NUM_DATA_VALUES);
        this.container = container;
        this.data = data;
        this.level = inventory.player.level();

        container.startOpen(inventory.player);

        this.addSlot(new Slot(container, SLOT_INGREDIENT_0, 21, 16));
        this.addSlot(new Slot(container, SLOT_INGREDIENT_1, 39, 16));
        this.addSlot(new Slot(container, SLOT_INGREDIENT_2, 57, 16));
        this.addSlot(new Slot(container, SLOT_INGREDIENT_3, 75, 16));
        this.addSlot(new OvenFuelSlot(this, container, SLOT_FUEL, 48, 54));
        this.addSlot(new OvenResultSlot(inventory.player, container, SLOT_RESULT, 136, 35));

        this.addStandardInventorySlots(inventory, 8, 84);
        this.addDataSlots(data);

    }

    @Override public void fillCraftSlotsStackedContents(StackedItemContents stackedContents) {
        if (this.container instanceof StackedContentsCompatible stackedContentsCompatible) {
            stackedContentsCompatible.fillStackedContents(stackedContents);
        }
    }

    public Slot getResultSlot() {
        return this.slots.get(SLOT_RESULT);
    }

    public List<Slot> getIngredientSlots() {
        return this.slots.subList(SLOT_INGREDIENT_0, SLOT_FUEL);
    }

    @Override public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override public ItemStack quickMoveStack(Player player, int slotIndex) {

        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        if (slot != null && slot.hasItem()) {

            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();

            if (slotIndex == SLOT_RESULT) {
                if (!this.moveItemStackTo(originalStack, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(originalStack, newStack);
            } else if (slotIndex <= SLOT_FUEL) {
                if (!this.moveItemStackTo(originalStack, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (this.isFuel(originalStack)) {
                    if (!this.moveItemStackTo(originalStack, SLOT_FUEL, SLOT_FUEL + 1, false)) {
                        if (!this.moveItemStackTo(originalStack, SLOT_INGREDIENT_0, SLOT_FUEL, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                } else if (!this.moveItemStackTo(originalStack, SLOT_INGREDIENT_0, SLOT_FUEL, false)) {
                    if (slotIndex < INV_SLOT_END) {
                        if (!this.moveItemStackTo(originalStack, INV_SLOT_END, USE_ROW_SLOT_END, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(originalStack, INV_SLOT_START, INV_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (originalStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (originalStack.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, originalStack);

        }

        return newStack;

    }

    protected boolean isFuel(ItemStack itemStack) {
        return this.level.fuelValues().isFuel(itemStack);
    }

    public float getBurnProgress() {
        int current = this.data.get(OvenBlockEntity.DATA_COOKING_PROGRESS);
        int total = this.data.get(OvenBlockEntity.DATA_COOKING_TOTAL_TIME);
        return total != 0 && current != 0 ? Mth.clamp((float) current / total, 0.0F, 1.0F) : 0.0F;
    }

    public float getLitProgress() {
        int litDuration = this.data.get(OvenBlockEntity.DATA_LIT_DURATION);
        if (litDuration == 0) {
            litDuration = 200;
        }
        return Mth.clamp((float) this.data.get(OvenBlockEntity.DATA_LIT_TIME) / litDuration, 0.0F, 1.0F);
    }

    public boolean isLit() {
        return this.data.get(OvenBlockEntity.DATA_LIT_TIME) > 0;
    }

    @Override public RecipeBookType getRecipeBookType() {
        return RecipeBookType.BLAST_FURNACE;
    }

    @Override public RecipeBookMenu.PostPlaceAction handlePlacement(boolean useMaxItems, boolean allowDroppingItemsToClear, RecipeHolder<?> recipe, ServerLevel level, Inventory inventory) {

        List<Slot> ingredientSlots = this.getIngredientSlots();
        List<Slot> slotsToClear = Stream.concat(ingredientSlots.stream(), Stream.of(this.getResultSlot())).toList();
        RecipeHolder<OvenRecipe> ovenRecipe = (RecipeHolder<OvenRecipe>) recipe;

        return ServerPlaceRecipe.placeRecipe(
            new ServerPlaceRecipe.CraftingMenuAccess<OvenRecipe>() {

                @Override public void fillCraftSlotsStackedContents(StackedItemContents stackedContents) {
                    OvenScreenHandler.this.fillCraftSlotsStackedContents(stackedContents);
                }

                @Override public void clearCraftingContent() {
                    slotsToClear.forEach(slot -> slot.set(ItemStack.EMPTY));
                }

                @Override public boolean recipeMatches(RecipeHolder<OvenRecipe> recipe) {
                    List<ItemStack> currentIngredients = ingredientSlots.stream().map(Slot::getItem).toList();
                    return recipe.value().matches(new OvenRecipeInput(currentIngredients), level);
                }

            },
            ingredientSlots.size(), 1,
            ingredientSlots,
            slotsToClear,
            inventory,
            ovenRecipe,
            useMaxItems,
            allowDroppingItemsToClear
        );

    }

    private static class OvenFuelSlot extends Slot {

        private final OvenScreenHandler menu;

        public OvenFuelSlot(OvenScreenHandler menu, Container container, int slot, int x, int y) {
            super(container, slot, x, y);
            this.menu = menu;
        }

        @Override public boolean mayPlace(ItemStack itemStack) {
            return this.menu.isFuel(itemStack);
        }

    }

    private static class OvenResultSlot extends Slot {

        private final Player player;
        private int removeCount;

        public OvenResultSlot(Player player, Container container, int slot, int x, int y) {
            super(container, slot, x, y);
            this.player = player;
        }

        @Override public boolean mayPlace(ItemStack itemStack) {
            return false;
        }

        @Override public ItemStack remove(int amount) {
            if (this.hasItem()) {
                this.removeCount += Math.min(amount, this.getItem().getCount());
            }
            return super.remove(amount);
        }

        @Override public void onTake(Player player, ItemStack carried) {
            this.checkTakeAchievements(carried);
            super.onTake(player, carried);
        }

        @Override protected void onQuickCraft(ItemStack picked, int count) {
            this.removeCount += count;
            this.checkTakeAchievements(picked);
        }

        @Override protected void checkTakeAchievements(ItemStack carried) {
            carried.onCraftedBy(this.player, this.removeCount);
            if (this.player instanceof ServerPlayer serverPlayer && this.container instanceof OvenBlockEntity ovenBlockEntity) {
                ovenBlockEntity.awardUsedRecipesAndPopExperience(serverPlayer);
            }
            this.removeCount = 0;
        }

    }

}