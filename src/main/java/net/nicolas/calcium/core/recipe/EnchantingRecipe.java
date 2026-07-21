package net.nicolas.calcium.core.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnchantingRecipe implements Recipe<EnchantingRecipeInput> {

    private final NonNullList<Ingredient> ingredients;
    private final Holder<Enchantment> resultEnchantment;
    private final int resultLevel;
    private final Optional<Holder<Enchantment>> requiredEnchantment;
    private final int requiredLevel;

    public EnchantingRecipe(NonNullList<Ingredient> ingredients, Holder<Enchantment> resultEnchantment, int resultLevel, Optional<Holder<Enchantment>> requiredEnchantment, int requiredLevel) {
        this.ingredients = ingredients;
        this.resultEnchantment = resultEnchantment;
        this.resultLevel = resultLevel;
        this.requiredEnchantment = requiredEnchantment;
        this.requiredLevel = requiredLevel;
    }

    @Override public boolean matches(EnchantingRecipeInput input, Level world) {

        List<ItemStack> inputsToCheck = new ArrayList<>(input.ingredients());
        inputsToCheck.removeIf(ItemStack::isEmpty);

        if (inputsToCheck.size() != this.ingredients.size()) {
            return false;
        }

        for (Ingredient ingredient : this.ingredients) {
            boolean found = false;
            for (int i = 0; i < inputsToCheck.size(); i++) {
                if (ingredient.test(inputsToCheck.get(i))) {
                    inputsToCheck.remove(i);
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }

        if (requiredEnchantment.isPresent()) {
            ItemStack tablet = input.tablet();
            var componentType = tablet.is(net.minecraft.world.item.Items.ENCHANTED_BOOK)
                ? DataComponents.STORED_ENCHANTMENTS
                : DataComponents.ENCHANTMENTS;
            ItemEnchantments enchantments = tablet.get(componentType);
            if (enchantments == null) return false;
            int currentLevel = enchantments.getLevel(requiredEnchantment.get());
            if (currentLevel < requiredLevel) return false;
        }

        ItemStack tablet = input.tablet();
        Enchantment enchantment = this.resultEnchantment.value();

        boolean isBook = tablet.is(net.minecraft.world.item.Items.BOOK) || tablet.is(net.minecraft.world.item.Items.ENCHANTED_BOOK);
        if (!isBook && !enchantment.isSupportedItem(tablet)) {
            return false;
        }

        var componentType = tablet.is(net.minecraft.world.item.Items.ENCHANTED_BOOK)
            ? DataComponents.STORED_ENCHANTMENTS
            : DataComponents.ENCHANTMENTS;

        ItemEnchantments existingEnchants = tablet.get(componentType);

        int currentLevel = existingEnchants != null ? existingEnchants.getLevel(this.resultEnchantment) : 0;
        if (currentLevel != this.resultLevel - 1) {
            return false;
        }

        if (existingEnchants != null) {
            for (Holder<Enchantment> entry : existingEnchants.keySet()) {
                if (entry.equals(this.resultEnchantment)) continue;
                if (!Enchantment.areCompatible(entry, this.resultEnchantment)) {
                    return false;
                }
            }
        }

        return true;

    }

    @Override public ItemStack assemble(EnchantingRecipeInput input) {

        ItemStack inputStack = input.tablet();
        ItemStack result;

        if (inputStack.is(net.minecraft.world.item.Items.BOOK)) {
            result = new ItemStack(net.minecraft.world.item.Items.ENCHANTED_BOOK, inputStack.getCount());
            if (inputStack.has(DataComponents.CUSTOM_NAME)) {
                result.set(DataComponents.CUSTOM_NAME, inputStack.get(DataComponents.CUSTOM_NAME));
            }
            if (inputStack.has(DataComponents.LORE)) {
                result.set(DataComponents.LORE, inputStack.get(DataComponents.LORE));
            }
        }
        else {
            result = inputStack.copy();
        }

        var componentType = result.is(net.minecraft.world.item.Items.ENCHANTED_BOOK)
            ? DataComponents.STORED_ENCHANTMENTS
            : DataComponents.ENCHANTMENTS;

        ItemEnchantments existingEnchantments = result.get(componentType);

        if (existingEnchantments == null) {
            existingEnchantments = ItemEnchantments.EMPTY;
        }

        ItemEnchantments.Mutable builder = new ItemEnchantments.Mutable(existingEnchantments);
        builder.set(resultEnchantment, resultLevel);
        result.set(componentType, builder.toImmutable());

        return result;
        
    }

    @Override public RecipeSerializer<? extends Recipe<EnchantingRecipeInput>> getSerializer() {
        return ModRecipes.ENCHANTING_SERIALIZER;
    }

    @Override public RecipeType<? extends Recipe<EnchantingRecipeInput>> getType() {
        return ModRecipes.ENCHANTING_TYPE;
    }

    @Override public PlacementInfo placementInfo() {
        return PlacementInfo.create(this.ingredients);
    }

    @Override public RecipeBookCategory recipeBookCategory() {
        return ModRecipes.getEnchantingCategory(this.resultEnchantment);
    }

    @Override public List<RecipeDisplay> display() {

        HolderSet<Item> supportedItems = this.resultEnchantment.value().getSupportedItems();

        SlotDisplay tabletDisplay = this.requiredEnchantment.isPresent()
            ? buildEnchantedItemCycle(supportedItems, this.requiredEnchantment.get(), this.requiredLevel, false)
            : buildEnchantedItemCycle(supportedItems, null, 0, true);

        SlotDisplay resultDisplay = buildEnchantedItemCycle(supportedItems, this.resultEnchantment, this.resultLevel, false);

        return List.of(new EnchantingRecipeDisplay(
            this.ingredients.stream().map(Ingredient::display).toList(),
            tabletDisplay,
            resultDisplay,
            new SlotDisplay.ItemSlotDisplay(Items.ENCHANTING_TABLE)
        ));

    }

    // Cycles through every item the enchantment supports (per its supported_items tag) plus a
    // book-form entry, since enchanting - unlike crafting/smelting - can apply the same enchantment
    // to many unrelated items. When plainBookForm is true, everything (including the book slot) is
    // shown unenchanted (the "no prerequisite tier" case); otherwise every entry - including the book,
    // now an Enchanted Book - carries the given enchantment/level.
    private static SlotDisplay buildEnchantedItemCycle(HolderSet<Item> supportedItems, Holder<Enchantment> enchantment, int level, boolean plainBookForm) {

        List<SlotDisplay> entries = new ArrayList<>();
        supportedItems.forEach(holder -> entries.add(enchantedItemDisplay(holder.value(), enchantment, level)));

        if (plainBookForm) {
            entries.add(enchantedItemDisplay(Items.BOOK, null, 0));
        } else {
            entries.add(enchantedItemDisplay(Items.ENCHANTED_BOOK, enchantment, level));
        }

        return new SlotDisplay.Composite(entries);

    }

    private static SlotDisplay enchantedItemDisplay(Item item, Holder<Enchantment> enchantment, int level) {

        if (enchantment == null) {
            return new SlotDisplay.ItemSlotDisplay(item);
        }

        ItemStack stack = new ItemStack(item);
        var componentType = item == Items.ENCHANTED_BOOK ? DataComponents.STORED_ENCHANTMENTS : DataComponents.ENCHANTMENTS;
        ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        enchantments.set(enchantment, level);
        stack.set(componentType, enchantments.toImmutable());

        return new SlotDisplay.ItemStackSlotDisplay(ItemStackTemplate.fromStack(stack));

    }

    @Override public String group() {
        return "";
    }

    @Override public boolean showNotification() {
        return true;
    }

    public static class Serializer {

        public static final MapCodec<EnchantingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(r -> r.ingredients),
            Enchantment.CODEC.fieldOf("result_enchantment").forGetter(r -> r.resultEnchantment),
            Codec.INT.fieldOf("result_level").forGetter(r -> r.resultLevel),
            Enchantment.CODEC.optionalFieldOf("required_enchantment").forGetter(r -> r.requiredEnchantment),
            Codec.INT.optionalFieldOf("required_level", 0).forGetter(r -> r.requiredLevel)
        ).apply(instance, (ingredients, resEnch, resLvl, reqEnch, reqLvl) -> {
            NonNullList<Ingredient> list = NonNullList.create();
            list.addAll(ingredients);
            return new EnchantingRecipe(list, resEnch, resLvl, reqEnch, reqLvl);
        }));

        public static final StreamCodec<RegistryFriendlyByteBuf, EnchantingRecipe> PACKET_CODEC = StreamCodec.of(
                EnchantingRecipe.Serializer::write, EnchantingRecipe.Serializer::read
        );

        public static final RecipeSerializer<EnchantingRecipe> INSTANCE = new RecipeSerializer<>(CODEC, PACKET_CODEC);

        private static EnchantingRecipe read(RegistryFriendlyByteBuf buf) {

            int size = buf.readInt();
            NonNullList<Ingredient> ingredients = NonNullList.create();

            for(int i = 0; i < size; i++) {
                ingredients.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            }

            Holder<Enchantment> resEnch = Enchantment.STREAM_CODEC.decode(buf);
            int resLvl = buf.readInt();

            Optional<Holder<Enchantment>> reqEnch = Optional.empty();
            if (buf.readBoolean()) {
                reqEnch = Optional.of(Enchantment.STREAM_CODEC.decode(buf));
            }
            int reqLvl = buf.readInt();

            return new EnchantingRecipe(ingredients, resEnch, resLvl, reqEnch, reqLvl);

        }

        private static void write(RegistryFriendlyByteBuf buf, EnchantingRecipe recipe) {
            buf.writeInt(recipe.ingredients.size());
            for (Ingredient ingredient : recipe.ingredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }
            Enchantment.STREAM_CODEC.encode(buf, recipe.resultEnchantment);
            buf.writeInt(recipe.resultLevel);
            buf.writeBoolean(recipe.requiredEnchantment.isPresent());
            recipe.requiredEnchantment.ifPresent(e -> Enchantment.STREAM_CODEC.encode(buf, e));
            buf.writeInt(recipe.requiredLevel);
        }

    }

}