package net.nicolas.calcium.core.util;

import net.minecraft.world.level.biome.BiomeSpecialEffects;

public final class CalciumDirtColors {

    public static final int DEFAULT_DIRT_COLOR = 0xBE885E;

    private static final CalciumBiomeColorMap COLORS = new CalciumBiomeColorMap(DEFAULT_DIRT_COLOR);

    public static void put(BiomeSpecialEffects effects, int color) {
        COLORS.put(effects, color);
    }

    public static int get(BiomeSpecialEffects effects) {
        return COLORS.get(effects);
    }

    private CalciumDirtColors() {}

}
