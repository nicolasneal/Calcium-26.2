package net.nicolas.calcium.core.util;

import net.minecraft.world.level.biome.BiomeSpecialEffects;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

public final class CalciumDirtColors {

    public static final int DEFAULT_DIRT_COLOR = 0xBE885E;

    private static final Map<BiomeSpecialEffects, Integer> DIRT_COLORS = Collections.synchronizedMap(new IdentityHashMap<>());

    public static void put(BiomeSpecialEffects effects, int color) {
        DIRT_COLORS.put(effects, color);
    }

    public static int get(BiomeSpecialEffects effects) {
        Integer color = DIRT_COLORS.get(effects);
        return color != null ? color : DEFAULT_DIRT_COLOR;
    }

    private CalciumDirtColors() {}

}
