package net.nicolas.calcium.item;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties CHEVAL = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties COOKED_CHEVAL = new FoodProperties.Builder().nutrition(7).saturationModifier(0.75f).build();
    public static final FoodProperties BEAR = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodProperties COOKED_BEAR = new FoodProperties.Builder().nutrition(8).saturationModifier(0.65f).build();
    public static final FoodProperties CAMEL = new FoodProperties.Builder().nutrition(3).saturationModifier(0.30f).build();
    public static final FoodProperties COOKED_CAMEL = new FoodProperties.Builder().nutrition(7).saturationModifier(0.75f).build();
    public static final FoodProperties CHEVON = new FoodProperties.Builder().nutrition(2).saturationModifier(0.30f).build();
    public static final FoodProperties COOKED_CHEVON = new FoodProperties.Builder().nutrition(6).saturationModifier(0.80f).build();
    public static final FoodProperties FROG = new FoodProperties.Builder().nutrition(2).saturationModifier(0.20f).build();
    public static final FoodProperties COOKED_FROG = new FoodProperties.Builder().nutrition(4).saturationModifier(1.0f).build();
    public static final FoodProperties TENTACLES = new FoodProperties.Builder().nutrition(2).saturationModifier(0.12f).build();
    public static final FoodProperties COOKED_TENTACLES = new FoodProperties.Builder().nutrition(5).saturationModifier(0.60f).build();
    public static final FoodProperties CHOCOLATE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.75f).build();
    public static final FoodProperties WATER_BOWL = new FoodProperties.Builder().nutrition(0).saturationModifier(0.00f).alwaysEdible().build();

}