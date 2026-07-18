package net.nicolas.calcium.recipe;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModRecipes {

    public static void initialize() {}

    public static final RecipeType<EnchantingRecipe> ENCHANTING_TYPE = Registry.register(
        BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath("calcium", "enchanting"), new RecipeType<EnchantingRecipe>() {
            @Override public String toString() { return "calcium:enchanting"; }
        }
    );

    public static final RecipeSerializer<EnchantingRecipe> ENCHANTING_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath("calcium", "enchanting"), EnchantingRecipe.Serializer.INSTANCE);
    public static final RecipeBookCategory ENCHANTING_COMMON = Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath("calcium", "enchanting_common"), new RecipeBookCategory());
    public static final RecipeBookCategory ENCHANTING_SUPERIOR = Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath("calcium", "enchanting_superior"), new RecipeBookCategory());
    public static final RecipeBookCategory ENCHANTING_PARAGON = Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath("calcium", "enchanting_paragon"), new RecipeBookCategory());
    public static final RecipeDisplay.Type<EnchantingRecipeDisplay> ENCHANTING_DISPLAY_TYPE = Registry.register(BuiltInRegistries.RECIPE_DISPLAY, Identifier.fromNamespaceAndPath("calcium", "enchanting"), EnchantingRecipeDisplay.TYPE);

    public static final RecipeType<OvenRecipe> COOKING_TYPE = Registry.register(
        BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath("calcium", "cooking"), new RecipeType<OvenRecipe>() {
            @Override public String toString() { return "calcium:cooking"; }
        }
    );

    public static final RecipeSerializer<OvenRecipe> COOKING_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath("calcium", "cooking"), OvenRecipe.Serializer.INSTANCE);
    public static final RecipeBookCategory COOKING_SIMPLE = Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath("calcium", "cooking_simple"), new RecipeBookCategory());
    public static final RecipeBookCategory COOKING_COMPLEX = Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath("calcium", "cooking_complex"), new RecipeBookCategory());
    public static final RecipeDisplay.Type<OvenRecipeDisplay> COOKING_DISPLAY_TYPE = Registry.register(BuiltInRegistries.RECIPE_DISPLAY, Identifier.fromNamespaceAndPath("calcium", "cooking"), OvenRecipeDisplay.TYPE);

    private static final Map<String, RecipeBookCategory> ENCHANTMENT_TIERS = buildEnchantmentTiers();

    private static Map<String, RecipeBookCategory> buildEnchantmentTiers() {

        Map<String, RecipeBookCategory> tiers = new HashMap<>();

        // Common Enchantments

        for (String id : List.of("bane_of_arthropods", "blast_protection", "breach", "density", "efficiency", "fire_protection", "impaling", "lure", "piercing", "power", "projectile_protection", "protection", "quick_charge", "sharpness", "smite", "unbreaking")) {
            tiers.put(id, ENCHANTING_COMMON);
        }

        // Superior Enchantments

        for (String id : List.of("aqua_affinity", "depth_strider", "feather_falling", "fire_aspect", "flame", "fortune", "frost_walker", "knockback", "looting", "loyalty", "luck_of_the_sea", "multishot", "punch", "respiration", "silk_touch", "sweeping_edge", "thorns")) {
            tiers.put(id, ENCHANTING_SUPERIOR);
        }

        // Paragon Enchantments

        for (String id : List.of("channeling", "infinity", "mending", "riptide", "soul_speed", "swift_sneak", "wind_burst")) {
            tiers.put(id, ENCHANTING_PARAGON);
        }

        return Map.copyOf(tiers);

    }

    public static RecipeBookCategory getEnchantingCategory(Holder<Enchantment> enchantment) {
        String path = enchantment.unwrapKey().map(key -> key.identifier().getPath()).orElse("");
        return ENCHANTMENT_TIERS.getOrDefault(path, ENCHANTING_COMMON);
    }

}