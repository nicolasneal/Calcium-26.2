package net.nicolas.calcium.core.util;

import net.minecraft.world.level.biome.BiomeSpecialEffects;

public final class CalciumSandColors {

    public static final int DEFAULT_SAND_COLOR = 0xFAF4CB;

    private static final CalciumBiomeColorMap COLORS = new CalciumBiomeColorMap(DEFAULT_SAND_COLOR);

    public static void put(BiomeSpecialEffects effects, int color) {
        COLORS.put(effects, color);
    }

    public static int get(BiomeSpecialEffects effects) {
        return COLORS.get(effects);
    }

    private CalciumSandColors() {}

}
