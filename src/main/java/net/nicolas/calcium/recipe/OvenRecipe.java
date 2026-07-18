package net.nicolas.calcium.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import net.nicolas.calcium.block.ModBlocks;

import java.util.ArrayList;
import java.util.List;

public class OvenRecipe implements Recipe<OvenRecipeInput> {

    private final NonNullList<Ingredient> ingredients;
    private final ItemStackTemplate result;
    private final float experience;
    private final int cookingTime;

    public OvenRecipe(NonNullList<Ingredient> ingredients, ItemStackTemplate result, float experience, int cookingTime) {
        this.ingredients = ingredients;
        this.result = result;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    public NonNullList<Ingredient> ingredients() {
        return this.ingredients;
    }

    public float experience() {
        return this.experience;
    }

    public int cookingTime() {
        return this.cookingTime;
    }

    @Override public boolean matches(OvenRecipeInput input, Level level) {

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

        return true;

    }

    @Override public ItemStack assemble(OvenRecipeInput input) {
        return this.result.create();
    }

    @Override public RecipeSerializer<? extends Recipe<OvenRecipeInput>> getSerializer() {
        return ModRecipes.COOKING_SERIALIZER;
    }

    @Override public RecipeType<? extends Recipe<OvenRecipeInput>> getType() {
        return ModRecipes.COOKING_TYPE;
    }

    @Override public PlacementInfo placementInfo() {
        return PlacementInfo.create(this.ingredients);
    }

    @Override public RecipeBookCategory recipeBookCategory() {
        return this.ingredients.size() == 1 ? ModRecipes.COOKING_SIMPLE : ModRecipes.COOKING_COMPLEX;
    }

    @Override public List<RecipeDisplay> display() {
        return List.of(new OvenRecipeDisplay(
            this.ingredients.stream().map(Ingredient::display).toList(),
            SlotDisplay.AnyFuel.INSTANCE,
            new SlotDisplay.ItemStackSlotDisplay(this.result),
            new SlotDisplay.ItemSlotDisplay(ModBlocks.OVEN.asItem())
        ));
    }

    @Override public String group() {
        return "";
    }

    @Override public boolean showNotification() {
        return true;
    }

    public static class Serializer {

        public static final MapCodec<OvenRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(r -> r.ingredients),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(r -> r.result),
            Codec.FLOAT.optionalFieldOf("experience", 0.0F).forGetter(r -> r.experience),
            Codec.INT.optionalFieldOf("cookingtime", 200).forGetter(r -> r.cookingTime)
        ).apply(instance, (ingredients, result, experience, cookingTime) -> {
            NonNullList<Ingredient> list = NonNullList.create();
            list.addAll(ingredients);
            return new OvenRecipe(list, result, experience, cookingTime);
        }));

        public static final StreamCodec<RegistryFriendlyByteBuf, OvenRecipe> PACKET_CODEC = StreamCodec.of(
            OvenRecipe.Serializer::write, OvenRecipe.Serializer::read
        );

        public static final RecipeSerializer<OvenRecipe> INSTANCE = new RecipeSerializer<>(CODEC, PACKET_CODEC);

        private static OvenRecipe read(RegistryFriendlyByteBuf buf) {

            int size = buf.readInt();
            NonNullList<Ingredient> ingredients = NonNullList.create();

            for (int i = 0; i < size; i++) {
                ingredients.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            }

            ItemStackTemplate result = ItemStackTemplate.STREAM_CODEC.decode(buf);
            float experience = buf.readFloat();
            int cookingTime = buf.readInt();

            return new OvenRecipe(ingredients, result, experience, cookingTime);

        }

        private static void write(RegistryFriendlyByteBuf buf, OvenRecipe recipe) {
            buf.writeInt(recipe.ingredients.size());
            for (Ingredient ingredient : recipe.ingredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }
            ItemStackTemplate.STREAM_CODEC.encode(buf, recipe.result);
            buf.writeFloat(recipe.experience);
            buf.writeInt(recipe.cookingTime);
        }

    }

}
