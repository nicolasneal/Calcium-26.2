package net.nicolas.calcium.screen.enchanting;

import net.minecraft.network.chat.Component;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.nicolas.calcium.core.Calcium;
import net.nicolas.calcium.core.recipe.enchanting.EnchantingRecipe;
import net.nicolas.calcium.core.recipe.enchanting.EnchantingRecipeInput;
import net.nicolas.calcium.core.recipe.ModRecipes;
import net.nicolas.calcium.item.tag.ModTags;
import net.nicolas.calcium.screen.CustomSlot;
import net.nicolas.calcium.screen.SlotConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomEnchantingScreenHandler extends RecipeBookMenu {

    private final Container inventory;
    private final ResultContainer outputInventory = new ResultContainer();
    private final Player player;
    public final DataSlot levelCost = DataSlot.standalone();

    public CustomEnchantingScreenHandler(int syncId, Inventory playerInventory) {

        super(Calcium.CUSTOM_ENCHANTING_SCREEN_HANDLER, syncId);
        this.inventory = new MenuAwareContainer(11);
        checkContainerSize(this.inventory, 11);
        this.player = playerInventory.player;
        this.inventory.startOpen(playerInventory.player);
        this.addDataSlot(this.levelCost);

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new CustomSlot.Builder(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18).build());
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new CustomSlot.Builder(playerInventory, i, 8 + i * 18, 142).build());
        }

        this.addSlot(new CustomSlot.Builder(this.inventory, 0, 12, 35).itemMode(SlotConfig.ItemMode.FIXED).fixedItem(Items.LAPIS_LAZULI).stackMode(SlotConfig.StackMode.STACK).tooltip(Component.translatable("tooltip.calcium.enchanting_table_fuel")).build());

        this.addSlot(new CustomSlot.Builder(this.inventory, 1, 71, 44).itemMode(SlotConfig.ItemMode.TAG).allowedTag(ModTags.ENCHANTABLE).stackMode(SlotConfig.StackMode.SINGLE).tooltip(Component.translatable("tooltip.calcium.enchanting_table_item")).build());

        int[][] slotPositions = {{35, 53}, {35, 35}, {35, 17}, {53, 17}, {71, 17}, {89, 17}, {107, 17}, {107, 35}, {107, 53}};
        for (int i = 0; i < 9; i++) {
            this.addSlot(new CustomSlot.Builder(this.inventory, i + 2, slotPositions[i][0], slotPositions[i][1]).itemMode(SlotConfig.ItemMode.ALL).stackMode(SlotConfig.StackMode.SINGLE).build());
        }

        this.addSlot(new Slot(this.outputInventory, 0, 144, 35) {

            @Override public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override public boolean mayPickup(Player player) {

                if (player.getAbilities().instabuild) return true;

                int ingredientCount = 0;
                for (int i = 2; i < 11; i++) {
                    if (!CustomEnchantingScreenHandler.this.inventory.getItem(i).isEmpty()) {
                        ingredientCount++;
                    }
                }

                int xpCost = CustomEnchantingScreenHandler.this.getXpCost(ingredientCount);
                int lapisCost = ingredientCount;
                ItemStack lapis = CustomEnchantingScreenHandler.this.inventory.getItem(0);
                return player.experienceLevel >= xpCost && !lapis.isEmpty() && lapis.getCount() >= lapisCost;

            }

            @Override public void onTake(Player player, ItemStack stack) {

                player.level().playSound(null, player.blockPosition(), net.minecraft.sounds.SoundEvents.ENCHANTMENT_TABLE_USE, net.minecraft.sounds.SoundSource.BLOCKS, 1.0F, player.level().getRandom().nextFloat() * 0.1F + 0.9F);

                int ingredientCount = 0;
                for (int i = 2; i < 11; i++) {
                    if (!CustomEnchantingScreenHandler.this.inventory.getItem(i).isEmpty()) {
                        ingredientCount++;
                    }
                }

                int xpCost = CustomEnchantingScreenHandler.this.getXpCost(ingredientCount);
                int lapisCost = ingredientCount;

                if (!player.getAbilities().instabuild) {
                    player.giveExperienceLevels(-xpCost);
                }

                CustomEnchantingScreenHandler.this.inventory.removeItem(0, lapisCost);
                CustomEnchantingScreenHandler.this.inventory.setItem(1, ItemStack.EMPTY);

                for (int i = 2; i < 11; ++i) {
                    ItemStack ingredientStack = CustomEnchantingScreenHandler.this.inventory.getItem(i);
                    if (!ingredientStack.isEmpty()) {
                        net.minecraft.world.item.ItemStackTemplate remainderTemplate = ingredientStack.getItem().getCraftingRemainder();
                        ItemStack remainder = remainderTemplate != null ? remainderTemplate.create() : ItemStack.EMPTY;

                        ingredientStack.shrink(1);

                        if (!remainder.isEmpty()) {
                            if (ingredientStack.isEmpty()) {
                                CustomEnchantingScreenHandler.this.inventory.setItem(i, remainder);
                            } else {
                                if (!player.getInventory().add(remainder)) {
                                    player.drop(remainder, false);
                                }
                            }
                        }
                    }
                }

            }

        });

    }

    private int getXpCost(int ingredientCount) {
        if (ingredientCount <= 3) return 1;
        if (ingredientCount <= 6) return 2;
        return 3;
    }

    @Override public void slotsChanged(Container inventory) {
        super.slotsChanged(inventory);
        if (inventory == this.inventory) {
            this.updateResult(this.player.level());
        }
    }

    private List<ItemStack> getCurrentIngredients() {
        List<ItemStack> ingredients = new ArrayList<>();
        for (int i = 2; i < 11; i++) {
            ItemStack stack = this.inventory.getItem(i);
            if (!stack.isEmpty()) {
                ingredients.add(stack);
            }
        }
        return ingredients;
    }

    private void updateResult(Level world) {

        if (!world.isClientSide() && world instanceof net.minecraft.server.level.ServerLevel serverWorld) {

            ItemStack tablet = this.inventory.getItem(1);
            List<ItemStack> ingredients = this.getCurrentIngredients();

            EnchantingRecipeInput input = new EnchantingRecipeInput(tablet, ingredients);
            assert serverWorld.getServer() != null;
            Optional<RecipeHolder<EnchantingRecipe>> match = serverWorld.getServer()
                .getRecipeManager()
                .getRecipeFor(ModRecipes.ENCHANTING_TYPE, input, world, (ResourceKey<Recipe<?>>) null);

            if (match.isPresent()) {
                int ingredientCount = ingredients.size();
                int xpCost = this.getXpCost(ingredientCount);

                this.levelCost.set(xpCost);

                ItemStack lapis = this.inventory.getItem(0);
                boolean enoughLevels = this.player.getAbilities().instabuild || this.player.experienceLevel >= xpCost;
                boolean enoughLapis = !lapis.isEmpty() && lapis.is(Items.LAPIS_LAZULI) && lapis.getCount() >= ingredientCount;

                if (enoughLevels && enoughLapis) {
                    ItemStack result = match.get().value().assemble(input);
                    this.outputInventory.setItem(0, result);
                } else {
                    this.outputInventory.setItem(0, ItemStack.EMPTY);
                }

            }
            else {
                this.outputInventory.setItem(0, ItemStack.EMPTY);
                this.levelCost.set(0);
            }

        }

    }

    @Override public ItemStack quickMoveStack(Player player, int invSlot) {

        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();

            if (invSlot == 47) {
                if (!this.moveItemStackTo(originalStack, 0, 36, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(originalStack, newStack);
            }
            else if (invSlot < 36) {
                if (originalStack.is(Items.LAPIS_LAZULI)) {
                    if (!this.moveItemStackTo(originalStack, 36, 37, false)) {
                        if (!this.moveItemStackTo(originalStack, 37, 47, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                } else {
                    if (!this.moveItemStackTo(originalStack, 37, 47, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if (!this.moveItemStackTo(originalStack, 0, 36, false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }
            else {
                slot.setChanged();
            }
            if (originalStack.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, originalStack);
        }

        return newStack;

    }

    @Override public boolean stillValid(Player player) {
        return true;
    }

    @Override public void removed(Player player) {
        super.removed(player);
        this.outputInventory.removeItemNoUpdate(0);
        this.clearContainer(player, this.inventory);
    }

    public int getLevelCost() {
        return this.levelCost.get();
    }

    public List<Slot> getIngredientSlots() {
        return this.slots.subList(38, 47);
    }

    public Slot getResultSlot() {
        return this.slots.get(47);
    }

    @Override public RecipeBookType getRecipeBookType() {
        return RecipeBookType.SMOKER;
    }

    @Override public void fillCraftSlotsStackedContents(StackedItemContents stackedContents) {
        for (int i = 2; i < 11; i++) {
            stackedContents.accountSimpleStack(this.inventory.getItem(i));
        }
    }

    @Override public RecipeBookMenu.PostPlaceAction handlePlacement(boolean useMaxItems, boolean allowDroppingItemsToClear, RecipeHolder<?> recipe, ServerLevel level, Inventory inventory) {

        RecipeHolder<EnchantingRecipe> enchantingRecipe = (RecipeHolder<EnchantingRecipe>) recipe;
        List<Slot> ingredientSlots = this.getIngredientSlots();

        return ServerPlaceRecipe.placeRecipe(
            new ServerPlaceRecipe.CraftingMenuAccess<EnchantingRecipe>() {

                @Override public void fillCraftSlotsStackedContents(StackedItemContents stackedContents) {
                    CustomEnchantingScreenHandler.this.fillCraftSlotsStackedContents(stackedContents);
                }

                @Override public void clearCraftingContent() {
                    for (Slot slot : ingredientSlots) {
                        slot.set(ItemStack.EMPTY);
                    }
                }

                @Override public boolean recipeMatches(RecipeHolder<EnchantingRecipe> recipe) {
                    ItemStack tablet = CustomEnchantingScreenHandler.this.inventory.getItem(1);
                    List<ItemStack> currentIngredients = CustomEnchantingScreenHandler.this.getCurrentIngredients();
                    return recipe.value().matches(new EnchantingRecipeInput(tablet, currentIngredients), level);
                }

            },
            ingredientSlots.size(), 1,
            ingredientSlots,
            ingredientSlots,
            inventory,
            enchantingRecipe,
            useMaxItems,
            allowDroppingItemsToClear
        );

    }

    private final class MenuAwareContainer extends SimpleContainer {
        MenuAwareContainer(int size) {
            super(size);
        }

        @Override
        public void setChanged() {
            super.setChanged();
            CustomEnchantingScreenHandler.this.slotsChanged(this);
        }
    }

}