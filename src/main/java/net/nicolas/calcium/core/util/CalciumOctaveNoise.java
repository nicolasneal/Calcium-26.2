package net.nicolas.calcium.core.util;

import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.Noise;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

public final class CalciumOctaveNoise {

    private static final long SEED_SPREAD = 0x9E3779B97F4A7C15L;

    private final Noise[] octaves;
    private final double[] amplitudes;
    private final double baseScale;
    private final double extreme;

    public CalciumOctaveNoise(long seed, int octaveCount, double persistence, double baseScale, double extreme) {
        this.octaves = new Noise[octaveCount];
        this.amplitudes = new double[octaveCount];
        this.baseScale = baseScale;
        this.extreme = extreme;

        double total = 0.0;

        for (int i = 0; i < octaveCount; i++) {
            this.octaves[i] = new SimplexNoise(new WorldgenRandom(new LegacyRandomSource(seed ^ (SEED_SPREAD * (i + 1)))), true);
            this.amplitudes[i] = Math.pow(persistence, i);
            total += this.amplitudes[i];
        }

        for (int i = 0; i < octaveCount; i++) {
            this.amplitudes[i] /= total;
        }
    }

    public float sample(double x, double z) {

        double scale = this.baseScale;
        double sum = 0.0;

        for (int i = 0; i < this.octaves.length; i++) {
            sum += this.octaves[i].get(x * scale, z * scale) * this.amplitudes[i];
            scale *= 2.0;
        }

        return Mth.clamp((float) (sum / this.extreme), -1.0F, 1.0F);
    }

}