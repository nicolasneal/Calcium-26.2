package net.nicolas.calcium.core.util;

import net.minecraft.world.level.biome.BiomeSpecialEffects;

import java.util.IdentityHashMap;
import java.util.Map;

public final class CalciumBiomeColorMap {

    private final int defaultColor;
    private final Map<BiomeSpecialEffects, Integer> colors = new IdentityHashMap<>();

    public CalciumBiomeColorMap(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    public void put(BiomeSpecialEffects effects, int color) {
        colors.put(effects, color);
    }

    public int get(BiomeSpecialEffects effects) {
        Integer color = colors.get(effects);
        return color != null ? color : defaultColor;
    }

}
