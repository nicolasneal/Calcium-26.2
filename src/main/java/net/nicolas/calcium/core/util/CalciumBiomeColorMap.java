package net.nicolas.calcium.core.util;

import net.minecraft.world.level.biome.BiomeSpecialEffects;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class CalciumBiomeColorMap {

    private final int defaultColor;
    private final Map<BiomeSpecialEffects, Integer> colors = new IdentityHashMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public CalciumBiomeColorMap(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    public void put(BiomeSpecialEffects effects, int color) {
        lock.writeLock().lock();
        try {
            colors.put(effects, color);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int get(BiomeSpecialEffects effects) {
        lock.readLock().lock();
        try {
            Integer color = colors.get(effects);
            return color != null ? color : defaultColor;
        } finally {
            lock.readLock().unlock();
        }
    }

}
