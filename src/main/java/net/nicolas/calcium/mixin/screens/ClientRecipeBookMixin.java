package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.world.item.crafting.ExtendedRecipeBookCategory;
import net.nicolas.calcium.core.recipe.ModRecipes;
import net.nicolas.calcium.screen.EnchantingSearchCategory;
import net.nicolas.calcium.screen.OvenSearchCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

// Mirrors how vanilla's ClientRecipeBook.rebuildCollections() merges SearchRecipeBookCategory tabs
// (e.g. the crafting book's "everything" tab) from their sub-categories - except SearchRecipeBookCategory
// is a closed vanilla enum we can't add a 5th value to, so EnchantingSearchCategory.ALL is merged here
// instead, right before the same map gets frozen via Map.copyOf.
@Mixin(ClientRecipeBook.class)
public abstract class ClientRecipeBookMixin {

    @ModifyArg(method = "rebuildCollections", at = @At(value = "INVOKE", target = "Ljava/util/Map;copyOf(Ljava/util/Map;)Ljava/util/Map;"))
    private Map<ExtendedRecipeBookCategory, List<RecipeCollection>> calcium$addEnchantingAllTab(Map<ExtendedRecipeBookCategory, List<RecipeCollection>> byCategory) {
        List<RecipeCollection> merged = Stream.of(ModRecipes.ENCHANTING_COMMON, ModRecipes.ENCHANTING_SUPERIOR, ModRecipes.ENCHANTING_PARAGON)
            .flatMap(category -> byCategory.getOrDefault(category, List.of()).stream())
            .toList();
        byCategory.put(EnchantingSearchCategory.ALL, merged);

        List<RecipeCollection> mergedOven = Stream.of(ModRecipes.COOKING_SIMPLE, ModRecipes.COOKING_COMPLEX, ModRecipes.COOKING_SUSPICIOUS_STEW)
            .flatMap(category -> byCategory.getOrDefault(category, List.of()).stream())
            .toList();
        byCategory.put(OvenSearchCategory.ALL, mergedOven);

        return byCategory;
    }

}
