package net.nicolas.calcium.core.util;

import net.minecraft.core.BlockPos;

public final class CalciumTintVariation {

    private static final int OCTAVES = 3;
    private static final double PERSISTENCE = 0.8;
    private static final double SCALE = 0.1;
    private static final double NOISE_EXTREME = 0.75;
    private static final float MAX_HUE_SHIFT_DEGREES = 2.0F;
    private static final double MAX_EXPONENT_SHIFT = 0.125;
    private static final long HUE_SEED_SALT = 0x2545F4914F6CDD1DL;

    public static final CalciumTintVariation GRASS = new CalciumTintVariation(7321L);
    public static final CalciumTintVariation FOLIAGE = new CalciumTintVariation(2917L);

    private final CalciumOctaveNoise brightnessNoise;
    private final CalciumOctaveNoise hueNoise;

    private CalciumTintVariation(long seed) {
        this.brightnessNoise = new CalciumOctaveNoise(seed, OCTAVES, PERSISTENCE, SCALE, NOISE_EXTREME);
        this.hueNoise = new CalciumOctaveNoise(seed ^ HUE_SEED_SALT, OCTAVES, PERSISTENCE, SCALE, NOISE_EXTREME);
    }

    public int apply(BlockPos pos, int color) {
        double x = pos.getX() + 0.5;
        double z = pos.getZ() + 0.5;
        float hueShift = this.hueNoise.sample(x, z) * MAX_HUE_SHIFT_DEGREES;
        double exponent = 1.0 + this.brightnessNoise.sample(x, z) * MAX_EXPONENT_SHIFT;
        return CalciumTintMath.raise(CalciumTintMath.shiftHue(color, hueShift), exponent);
    }

}