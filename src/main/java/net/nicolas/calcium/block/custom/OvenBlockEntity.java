package net.nicolas.calcium.block.custom;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Reference2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.recipe.ModRecipes;
import net.nicolas.calcium.recipe.OvenRecipe;
import net.nicolas.calcium.recipe.OvenRecipeInput;
import net.nicolas.calcium.screen.OvenMenu;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class OvenBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible, RecipeCraftingHolder {

    public static final int SLOT_INGREDIENT_0 = 0;
    public static final int SLOT_INGREDIENT_1 = 1;
    public static final int SLOT_INGREDIENT_2 = 2;
    public static final int SLOT_INGREDIENT_3 = 3;
    public static final int SLOT_FUEL = 4;
    public static final int SLOT_RESULT = 5;
    public static final int CONTAINER_SIZE = 6;

    public static final int DATA_LIT_TIME = 0;
    public static final int DATA_LIT_DURATION = 1;
    public static final int DATA_COOKING_PROGRESS = 2;
    public static final int DATA_COOKING_TOTAL_TIME = 3;
    public static final int NUM_DATA_VALUES = 4;

    private static final Component DEFAULT_NAME = Component.translatable("container.calcium.oven");
    private static final Codec<Map<ResourceKey<Recipe<?>>, Integer>> RECIPES_USED_CODEC = Codec.unboundedMap(Recipe.KEY_CODEC, Codec.INT);
    private static final int[] SLOTS_FOR_UP = new int[]{SLOT_INGREDIENT_0, SLOT_INGREDIENT_1, SLOT_INGREDIENT_2, SLOT_INGREDIENT_3};
    private static final int[] SLOTS_FOR_DOWN = new int[]{SLOT_RESULT, SLOT_FUEL};
    private static final int[] SLOTS_FOR_SIDES = new int[]{SLOT_FUEL};

    private NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    private int litTimeRemaining;
    private int litTotalTime;
    private int cookingTimer;
    private int cookingTotalTime;
    private final Reference2IntOpenHashMap<ResourceKey<Recipe<?>>> recipesUsed = new Reference2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<OvenRecipeInput, OvenRecipe> quickCheck = RecipeManager.createCheck(ModRecipes.COOKING_TYPE);

    protected final ContainerData dataAccess = new ContainerData() {
        @Override public int get(int dataId) {
            return switch (dataId) {
                case DATA_LIT_TIME -> OvenBlockEntity.this.litTimeRemaining;
                case DATA_LIT_DURATION -> OvenBlockEntity.this.litTotalTime;
                case DATA_COOKING_PROGRESS -> OvenBlockEntity.this.cookingTimer;
                case DATA_COOKING_TOTAL_TIME -> OvenBlockEntity.this.cookingTotalTime;
                default -> 0;
            };
        }

        @Override public void set(int dataId, int value) {
            switch (dataId) {
                case DATA_LIT_TIME -> OvenBlockEntity.this.litTimeRemaining = value;
                case DATA_LIT_DURATION -> OvenBlockEntity.this.litTotalTime = value;
                case DATA_COOKING_PROGRESS -> OvenBlockEntity.this.cookingTimer = value;
                case DATA_COOKING_TOTAL_TIME -> OvenBlockEntity.this.cookingTotalTime = value;
                default -> {}
            }
        }

        @Override public int getCount() {
            return NUM_DATA_VALUES;
        }
    };

    public OvenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.OVEN_BLOCK_ENTITY, pos, state);
    }

    @Override protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new OvenMenu(containerId, inventory, this, this.dataAccess);
    }

    @Override protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);
        this.cookingTimer = input.getShortOr("cooking_time_spent", (short) 0);
        this.cookingTotalTime = input.getShortOr("cooking_total_time", (short) 0);
        this.litTimeRemaining = input.getShortOr("lit_time_remaining", (short) 0);
        this.litTotalTime = input.getShortOr("lit_total_time", (short) 0);
        this.recipesUsed.clear();
        this.recipesUsed.putAll(input.read("RecipesUsed", RECIPES_USED_CODEC).orElse(Map.of()));
    }

    @Override protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putShort("cooking_time_spent", (short) this.cookingTimer);
        output.putShort("cooking_total_time", (short) this.cookingTotalTime);
        output.putShort("lit_time_remaining", (short) this.litTimeRemaining);
        output.putShort("lit_total_time", (short) this.litTotalTime);
        ContainerHelper.saveAllItems(output, this.items);
        output.store("RecipesUsed", RECIPES_USED_CODEC, this.recipesUsed);
    }

    public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, OvenBlockEntity entity) {

        boolean changed = false;
        boolean wasLit;
        boolean isLit;

        if (entity.litTimeRemaining > 0) {
            wasLit = true;
            entity.litTimeRemaining--;
            isLit = entity.litTimeRemaining > 0;
        } else {
            wasLit = false;
            isLit = false;
        }

        ItemStack fuel = entity.items.get(SLOT_FUEL);
        OvenRecipeInput input = entity.getRecipeInput();
        boolean hasIngredients = input.ingredients().stream().anyMatch(stack -> !stack.isEmpty());
        boolean hasFuel = !fuel.isEmpty();

        if (isLit || hasFuel && hasIngredients) {
            if (hasIngredients) {
                RecipeHolder<OvenRecipe> recipe = entity.quickCheck.getRecipeFor(input, level).orElse(null);
                if (recipe != null) {
                    int maxStackSize = entity.getMaxStackSize();
                    ItemStack bakeResult = recipe.value().assemble(input);
                    if (!bakeResult.isEmpty() && canBake(entity.items, maxStackSize, bakeResult)) {
                        if (!isLit) {
                            int newLitTime = level.fuelValues().burnDuration(fuel);
                            entity.litTimeRemaining = newLitTime;
                            entity.litTotalTime = newLitTime;
                            if (newLitTime > 0) {
                                consumeFuel(entity.items, fuel);
                                isLit = true;
                                changed = true;
                            }
                        }

                        if (isLit) {
                            entity.cookingTimer++;
                            if (entity.cookingTimer == entity.cookingTotalTime) {
                                entity.cookingTimer = 0;
                                entity.cookingTotalTime = recipe.value().cookingTime();
                                bake(entity.items, recipe.value(), bakeResult);
                                entity.setRecipeUsed(recipe);
                                changed = true;
                            }
                        } else {
                            entity.cookingTimer = 0;
                        }
                    } else {
                        entity.cookingTimer = 0;
                    }
                } else {
                    entity.cookingTimer = 0;
                }
            } else {
                entity.cookingTimer = 0;
            }
        } else if (entity.cookingTimer > 0) {
            entity.cookingTimer = Mth.clamp(entity.cookingTimer - 2, 0, entity.cookingTotalTime);
        }

        if (wasLit != isLit) {
            changed = true;
            state = state.setValue(BlockStateProperties.LIT, isLit);
            level.setBlock(pos, state, 3);
        }

        if (changed) {
            setChanged(level, pos, state);
        }

    }

    private OvenRecipeInput getRecipeInput() {
        List<ItemStack> ingredients = List.of(
            this.items.get(SLOT_INGREDIENT_0),
            this.items.get(SLOT_INGREDIENT_1),
            this.items.get(SLOT_INGREDIENT_2),
            this.items.get(SLOT_INGREDIENT_3)
        );
        return new OvenRecipeInput(ingredients);
    }

    private static void consumeFuel(NonNullList<ItemStack> items, ItemStack fuel) {
        Item fuelItem = fuel.getItem();
        fuel.shrink(1);
        if (fuel.isEmpty()) {
            ItemStackTemplate remainder = fuelItem.getCraftingRemainder();
            items.set(SLOT_FUEL, remainder != null ? remainder.create() : ItemStack.EMPTY);
        }
    }

    private static boolean canBake(NonNullList<ItemStack> items, int maxStackSize, ItemStack bakeResult) {
        ItemStack resultStack = items.get(SLOT_RESULT);
        if (resultStack.isEmpty()) {
            return true;
        }

        if (!ItemStack.isSameItemSameComponents(resultStack, bakeResult)) {
            return false;
        }

        int resultCount = resultStack.getCount() + bakeResult.getCount();
        int maxResultCount = Math.min(maxStackSize, bakeResult.getMaxStackSize());
        return resultCount <= maxResultCount;
    }

    private static void bake(NonNullList<ItemStack> items, OvenRecipe recipe, ItemStack result) {

        ItemStack resultStack = items.get(SLOT_RESULT);
        if (resultStack.isEmpty()) {
            items.set(SLOT_RESULT, result.copy());
        } else {
            resultStack.grow(result.getCount());
        }

        boolean[] consumedSlots = new boolean[4];
        for (Ingredient ingredient : recipe.ingredients()) {
            for (int slot = SLOT_INGREDIENT_0; slot <= SLOT_INGREDIENT_3; slot++) {
                if (!consumedSlots[slot] && !items.get(slot).isEmpty() && ingredient.test(items.get(slot))) {
                    items.get(slot).shrink(1);
                    consumedSlots[slot] = true;
                    break;
                }
            }
        }

    }

    @Override public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) return SLOTS_FOR_DOWN;
        if (direction == Direction.UP) return SLOTS_FOR_UP;
        return SLOTS_FOR_SIDES;
    }

    @Override public boolean canPlaceItemThroughFace(int slot, ItemStack itemStack, @Nullable Direction direction) {
        return this.canPlaceItem(slot, itemStack);
    }

    @Override public boolean canTakeItemThroughFace(int slot, ItemStack itemStack, Direction direction) {
        return direction == Direction.DOWN ? slot == SLOT_RESULT : true;
    }

    @Override public int getContainerSize() {
        return this.items.size();
    }

    @Override protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override public void setItem(int slot, ItemStack itemStack) {
        ItemStack oldStack = this.items.get(slot);
        boolean same = !itemStack.isEmpty() && ItemStack.isSameItemSameComponents(oldStack, itemStack);
        this.items.set(slot, itemStack);
        itemStack.limitSize(this.getMaxStackSize(itemStack));
        if (slot >= SLOT_INGREDIENT_0 && slot <= SLOT_INGREDIENT_3 && !same && this.level instanceof ServerLevel serverLevel) {
            this.cookingTotalTime = getTotalCookTime(serverLevel, this);
            this.cookingTimer = 0;
            this.setChanged();
        }
    }

    private static int getTotalCookTime(ServerLevel level, OvenBlockEntity entity) {
        return entity.quickCheck.getRecipeFor(entity.getRecipeInput(), level).map(recipeHolder -> recipeHolder.value().cookingTime()).orElse(200);
    }

    @Override public boolean canPlaceItem(int slot, ItemStack itemStack) {
        if (slot == SLOT_RESULT) return false;
        if (slot != SLOT_FUEL) return true;
        return this.level.fuelValues().isFuel(itemStack);
    }

    @Override public void setRecipeUsed(@Nullable RecipeHolder<?> recipeUsed) {
        if (recipeUsed != null) {
            this.recipesUsed.addTo(recipeUsed.id(), 1);
        }
    }

    @Override public @Nullable RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    @Override public void awardUsedRecipes(Player player, List<ItemStack> itemStacks) {
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        List<RecipeHolder<?>> recipesToAward = this.getRecipesToAwardAndPopExperience(player.level(), player.position());
        player.awardRecipes(recipesToAward);

        for (RecipeHolder<?> recipe : recipesToAward) {
            player.triggerRecipeCrafted(recipe, this.items);
        }

        this.recipesUsed.clear();
    }

    public List<RecipeHolder<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 position) {
        List<RecipeHolder<?>> recipesToAward = Lists.newArrayList();

        for (Entry<ResourceKey<Recipe<?>>> entry : this.recipesUsed.reference2IntEntrySet()) {
            level.recipeAccess().byKey(entry.getKey()).ifPresent(recipe -> {
                recipesToAward.add((RecipeHolder<?>) recipe);
                createExperience(level, position, entry.getIntValue(), ((OvenRecipe) recipe.value()).experience());
            });
        }

        return recipesToAward;
    }

    private static void createExperience(ServerLevel level, Vec3 position, int amount, float value) {
        int xpReward = Mth.floor(amount * value);
        float xpFraction = Mth.frac(amount * value);
        if (xpFraction != 0.0F && level.getRandom().nextFloat() < xpFraction) {
            xpReward++;
        }

        ExperienceOrb.award(level, position, xpReward);
    }

    @Override public void fillStackedContents(StackedItemContents contents) {
        for (ItemStack itemStack : this.items) {
            contents.accountStack(itemStack);
        }
    }

    @Override public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);
        if (this.level instanceof ServerLevel serverLevel) {
            this.getRecipesToAwardAndPopExperience(serverLevel, Vec3.atCenterOf(pos));
        }
    }

}