package net.nicolas.calcium.core.client.environment;

import net.minecraft.util.Mth;

public final class HighAltitude {

    private static final double FADE_START_Y = 384.0;
    private static final double FADE_END_Y = 512.0;

    private HighAltitude() {
    }

    public static float computeFactor(double y) {
        return Mth.clamp((float) ((y - FADE_START_Y) / (FADE_END_Y - FADE_START_Y)), 0.0F, 1.0F);
    }

}
