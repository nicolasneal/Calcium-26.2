package net.nicolas.calcium.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static void initialize() {

        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "apple")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.APPLE));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "baked_potato")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.BAKED_POTATO));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "beef")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.BEEF));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "beetroot")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.BEETROOT));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "beetroot_soup")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.BEETROOT_SOUP));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "bread")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.BREAD));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "carrot")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.CARROT));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "chicken")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.CHICKEN));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "chorus_fruit")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.CHORUS_FRUIT));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "cod")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.COD));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "cooked_beef")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.COOKED_BEEF));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "cooked_chicken")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.COOKED_CHICKEN));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "cooked_cod")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.COOKED_COD));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "cooked_mutton")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.COOKED_MUTTON));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "cooked_porkchop")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.COOKED_PORKCHOP));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "cooked_rabbit")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.COOKED_RABBIT));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "cooked_salmon")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.COOKED_SALMON));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "cookie")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.COOKIE));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "dried_kelp")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.DRIED_KELP));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchanted_golden_apple")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.ENCHANTED_GOLDEN_APPLE));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "golden_apple")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.GOLDEN_APPLE));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "golden_carrot")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.GOLDEN_CARROT));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "honey_bottle")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.HONEY_BOTTLE));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "melon_slice")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.MELON_SLICE));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "mushroom_stew")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.MUSHROOM_STEW));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "mutton")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.MUTTON));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "poisonous_potato")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.POISONOUS_POTATO));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "porkchop")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.PORKCHOP));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "potato")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.POTATO));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "pufferfish")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.PUFFERFISH));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "pumpkin_pie")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.PUMPKIN_PIE));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "rabbit")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.RABBIT));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "rabbit_stew")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.RABBIT_STEW));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "rotten_flesh")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.ROTTEN_FLESH));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "salmon")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.SALMON));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "spider_eye")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.SPIDER_EYE));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "suspicious_stew")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.SUSPICIOUS_STEW));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "sweet_berries")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.SWEET_BERRIES));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "glow_berries")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.GLOW_BERRIES));
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "tropical_fish")), (builder, context, key) -> builder.set(DataComponents.FOOD, ModFoods.TROPICAL_FISH));

    }

    // Vanilla Overrides

    public static final FoodProperties APPLE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.3f).build();
    public static final FoodProperties BAKED_POTATO = new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build();
    public static final FoodProperties BEEF = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build();
    public static final FoodProperties BEETROOT = new FoodProperties.Builder().nutrition(1).saturationModifier(0.6f).build();
    public static final FoodProperties BEETROOT_SOUP = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6f).build();
    public static final FoodProperties BREAD = new FoodProperties.Builder().nutrition(6).saturationModifier(0.5f).build();
    public static final FoodProperties CARROT = new FoodProperties.Builder().nutrition(3).saturationModifier(0.6f).build();
    public static final FoodProperties CHICKEN = new FoodProperties.Builder().nutrition(2).saturationModifier(0.3f).build();
    public static final FoodProperties CHORUS_FRUIT = new FoodProperties.Builder().nutrition(4).saturationModifier(0.3f).alwaysEdible().build();
    public static final FoodProperties COD = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static final FoodProperties COOKED_BEEF = new FoodProperties.Builder().nutrition(8).saturationModifier(0.8f).build();
    public static final FoodProperties COOKED_CHICKEN = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6f).build();
    public static final FoodProperties COOKED_COD = new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build();
    public static final FoodProperties COOKED_MUTTON = new FoodProperties.Builder().nutrition(6).saturationModifier(0.8f).build();
    public static final FoodProperties COOKED_PORKCHOP = new FoodProperties.Builder().nutrition(8).saturationModifier(0.8f).build();
    public static final FoodProperties COOKED_RABBIT = new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build();
    public static final FoodProperties COOKED_SALMON = new FoodProperties.Builder().nutrition(6).saturationModifier(0.8f).build();
    public static final FoodProperties COOKIE = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6f).build();
    public static final FoodProperties DRIED_KELP = new FoodProperties.Builder().nutrition(1).saturationModifier(0.3f).build();
    public static final FoodProperties ENCHANTED_GOLDEN_APPLE = new FoodProperties.Builder().nutrition(4).saturationModifier(1.2f).alwaysEdible().build();
    public static final FoodProperties GOLDEN_APPLE = new FoodProperties.Builder().nutrition(4).saturationModifier(1.2f).alwaysEdible().build();
    public static final FoodProperties GOLDEN_CARROT = new FoodProperties.Builder().nutrition(6).saturationModifier(1.2f).build();
    public static final FoodProperties HONEY_BOTTLE = new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).alwaysEdible().build();
    public static final FoodProperties MELON_SLICE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.3f).build();
    public static final FoodProperties MUSHROOM_STEW = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6f).build();
    public static final FoodProperties MUTTON = new FoodProperties.Builder().nutrition(2).saturationModifier(0.3f).build();
    public static final FoodProperties POISONOUS_POTATO = new FoodProperties.Builder().nutrition(2).saturationModifier(0.3f).build();
    public static final FoodProperties PORKCHOP = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build();
    public static final FoodProperties POTATO = new FoodProperties.Builder().nutrition(1).saturationModifier(0.3f).build();
    public static final FoodProperties PUFFERFISH = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build();
    public static final FoodProperties PUMPKIN_PIE = new FoodProperties.Builder().nutrition(8).saturationModifier(0.8f).build();
    public static final FoodProperties RABBIT = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build();
    public static final FoodProperties RABBIT_STEW = new FoodProperties.Builder().nutrition(10).saturationModifier(0.6f).build();
    public static final FoodProperties ROTTEN_FLESH = new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build();
    public static final FoodProperties SALMON = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static final FoodProperties SPIDER_EYE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.8f).build();
    public static final FoodProperties SUSPICIOUS_STEW = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6f).alwaysEdible().build();
    public static final FoodProperties SWEET_BERRIES = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static final FoodProperties GLOW_BERRIES = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static final FoodProperties TROPICAL_FISH = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build();
    public static final int CAKE_SLICE_NUTRITION = 4;
    public static final float CAKE_SLICE_SATURATION = 0.5f;

    // Mod Foods

    public static final FoodProperties FISH = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static final FoodProperties COOKED_FISH = new FoodProperties.Builder().nutrition(6).saturationModifier(0.8f).build();
    public static final FoodProperties CHEVAL = new FoodProperties.Builder().nutrition(3).saturationModifier(0.2f).build();
    public static final FoodProperties COOKED_CHEVAL = new FoodProperties.Builder().nutrition(7).saturationModifier(0.8f).build();
    public static final FoodProperties BEAR = new FoodProperties.Builder().nutrition(3).saturationModifier(0.2f).build();
    public static final FoodProperties COOKED_BEAR = new FoodProperties.Builder().nutrition(8).saturationModifier(0.6f).build();
    public static final FoodProperties CAMEL = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build();
    public static final FoodProperties COOKED_CAMEL = new FoodProperties.Builder().nutrition(7).saturationModifier(0.8f).build();
    public static final FoodProperties CHEVON = new FoodProperties.Builder().nutrition(2).saturationModifier(0.3f).build();
    public static final FoodProperties COOKED_CHEVON = new FoodProperties.Builder().nutrition(6).saturationModifier(0.8f).build();
    public static final FoodProperties FROG = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build();
    public static final FoodProperties COOKED_FROG = new FoodProperties.Builder().nutrition(4).saturationModifier(1.0f).build();
    public static final FoodProperties TENTACLES = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static final FoodProperties COOKED_TENTACLES = new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build();
    public static final FoodProperties CHOCOLATE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.75f).build();
    public static final FoodProperties WATER_BOWL = new FoodProperties.Builder().nutrition(0).alwaysEdible().build();

}