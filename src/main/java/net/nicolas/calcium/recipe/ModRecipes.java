package net.nicolas.calcium.recipe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes {

    public static void initialize() {}

    public static final RecipeType<EnchantingRecipe> ENCHANTING_TYPE = Registry.register(
        BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath("calcium", "enchanting"), new RecipeType<EnchantingRecipe>() {
            @Override public String toString() { return "calcium:enchanting"; }
        }
    );

    public static final RecipeSerializer<EnchantingRecipe> ENCHANTING_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath("calcium", "enchanting"), EnchantingRecipe.Serializer.INSTANCE);

}