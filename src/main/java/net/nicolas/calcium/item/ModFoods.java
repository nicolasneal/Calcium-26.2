package net.nicolas.calcium.item;

import net.minecraft.component.type.FoodComponent;

public class ModFoods {

    public static final FoodComponent CHEVAL = new FoodComponent.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodComponent COOKED_CHEVAL = new FoodComponent.Builder().nutrition(7).saturationModifier(0.75f).build();
    public static final FoodComponent BEAR = new FoodComponent.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodComponent COOKED_BEAR = new FoodComponent.Builder().nutrition(8).saturationModifier(0.65f).build();
    public static final FoodComponent CAMEL = new FoodComponent.Builder().nutrition(3).saturationModifier(0.30f).build();
    public static final FoodComponent COOKED_CAMEL = new FoodComponent.Builder().nutrition(7).saturationModifier(0.75f).build();
    public static final FoodComponent CHEVON = new FoodComponent.Builder().nutrition(2).saturationModifier(0.30f).build();
    public static final FoodComponent COOKED_CHEVON = new FoodComponent.Builder().nutrition(6).saturationModifier(0.80f).build();
    public static final FoodComponent FROG = new FoodComponent.Builder().nutrition(2).saturationModifier(0.20f).build();
    public static final FoodComponent COOKED_FROG = new FoodComponent.Builder().nutrition(4).saturationModifier(1.0f).build();
    public static final FoodComponent TENTACLES = new FoodComponent.Builder().nutrition(2).saturationModifier(0.12f).build();
    public static final FoodComponent COOKED_TENTACLES = new FoodComponent.Builder().nutrition(5).saturationModifier(0.60f).build();
    public static final FoodComponent CHOCOLATE = new FoodComponent.Builder().nutrition(4).saturationModifier(0.75f).build();
    public static final FoodComponent WATER_BOWL = new FoodComponent.Builder().nutrition(0).saturationModifier(0.00f).alwaysEdible().build();

}