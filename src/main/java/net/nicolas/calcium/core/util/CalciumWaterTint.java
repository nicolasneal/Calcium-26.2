package net.nicolas.calcium.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;

import java.util.Random;

public final class CalciumWaterTint {

    private static final int SIZE = 128;
    private static final int FIRST_OCTAVE_CELLS = 8;
    private static final int LAST_OCTAVE_CELLS = 64;
    private static final double PERSISTENCE = 0.8;
    private static final double TABLE_MEAN = 0.5652;
    private static final double TABLE_DEVIATION = 0.1423;
    private static final double POSITION_SCALE = 0.024;
    private static final double HEIGHT_OFFSET = 2.0;
    private static final double TABLE_COORD_SCALE = 0.25;
    private static final double EXPONENT_RANGE = 0.25;
    private static final double TEXELS_PER_BLOCK = POSITION_SCALE * TABLE_COORD_SCALE * SIZE;
    private static final float[] TABLE = generateTable(new Random(98L));

    public static int apply(BlockPos pos, int color) {
        double offset = pos.getY() * HEIGHT_OFFSET;
        double u = (pos.getX() + 0.5 + offset) * TEXELS_PER_BLOCK;
        double v = (pos.getZ() + 0.5 + offset) * TEXELS_PER_BLOCK;
        double exponent = 1.0 + (sample(u, v) - 0.5) * EXPONENT_RANGE;

        return CalciumTintMath.raise(color, exponent);
    }

    private static double sample(double u, double v) {
        double x = u - 0.5;
        double y = v - 0.5;
        int x0 = (int) Math.floor(x);
        int y0 = (int) Math.floor(y);
        double tx = x - x0;
        double ty = y - y0;
        int x1 = Math.floorMod(x0 + 1, SIZE);
        int y1 = Math.floorMod(y0 + 1, SIZE);
        x0 = Math.floorMod(x0, SIZE);
        y0 = Math.floorMod(y0, SIZE);

        double top = Mth.lerp(tx, TABLE[y0 * SIZE + x0], TABLE[y0 * SIZE + x1]);
        double bottom = Mth.lerp(tx, TABLE[y1 * SIZE + x0], TABLE[y1 * SIZE + x1]);

        return Mth.lerp(ty, top, bottom);
    }

    private static float[] generateTable(Random random) {

        double[] sum = new double[SIZE * SIZE];
        double amplitude = 1.0;

        for (int cells = FIRST_OCTAVE_CELLS; cells <= LAST_OCTAVE_CELLS; cells *= 2) {
            addOctave(sum, cells, amplitude, random);
            amplitude *= PERSISTENCE;
        }

        double mean = 0.0;

        for (double value : sum) {
            mean += value;
        }

        mean /= sum.length;
        double variance = 0.0;

        for (double value : sum) {
            variance += (value - mean) * (value - mean);
        }

        double deviation = Math.sqrt(variance / sum.length);
        float[] table = new float[sum.length];

        for (int i = 0; i < table.length; i++) {
            table[i] = (float) (TABLE_MEAN + (sum[i] - mean) / deviation * TABLE_DEVIATION);
        }

        return table;
    }

    private static void addOctave(double[] sum, int cells, double amplitude, Random random) {

        double[] lattice = new double[cells * cells];

        for (int i = 0; i < lattice.length; i++) {
            lattice[i] = random.nextDouble() * 2.0 - 1.0;
        }

        double step = (double) cells / SIZE;

        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                double fx = x * step;
                double fy = y * step;
                int x0 = (int) fx;
                int y0 = (int) fy;
                double tx = fade(fx - x0);
                double ty = fade(fy - y0);
                int x1 = (x0 + 1) % cells;
                int y1 = (y0 + 1) % cells;

                double top = Mth.lerp(tx, lattice[y0 * cells + x0], lattice[y0 * cells + x1]);
                double bottom = Mth.lerp(tx, lattice[y1 * cells + x0], lattice[y1 * cells + x1]);

                sum[y * SIZE + x] += Mth.lerp(ty, top, bottom) * amplitude;
            }
        }
    }

    private static double fade(double t) {
        return t * t * t * (t * (t * 6.0 - 15.0) + 10.0);
    }

    private CalciumWaterTint() {}

}