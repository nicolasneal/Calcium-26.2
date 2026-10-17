package net.nicolas.calcium.core.util;

import net.minecraft.core.BlockPos;

public final class CalciumTintVariation {

    private static final int OCTAVES = 3;
    private static final double PERSISTENCE = 0.8;
    private static final double SCALE = 0.1;
    private static final double NOISE_EXTREME = 0.75;
    private static final float MAX_HUE_SHIFT_DEGREES = 5.0F;
    private static final double MAX_EXPONENT_SHIFT = 0.125;

    public static final CalciumTintVariation GRASS = new CalciumTintVariation(7321L);
    public static final CalciumTintVariation FOLIAGE = new CalciumTintVariation(2917L);

    private final CalciumOctaveNoise noise;

    private CalciumTintVariation(long seed) {
        this.noise = new CalciumOctaveNoise(seed, OCTAVES, PERSISTENCE, SCALE, NOISE_EXTREME);
    }

    public int apply(BlockPos pos, int color) {
        float value = this.noise.sample(pos.getX() + 0.5, pos.getZ() + 0.5);
        return CalciumTintMath.raise(CalciumTintMath.shiftHue(color, value * MAX_HUE_SHIFT_DEGREES), 1.0 + value * MAX_EXPONENT_SHIFT);
    }

}