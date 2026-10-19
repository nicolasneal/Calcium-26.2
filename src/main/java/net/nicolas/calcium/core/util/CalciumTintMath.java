package net.nicolas.calcium.core.util;

public final class CalciumTintMath {

    public static int raise(int color, double exponent) {
        return (color & 0xFF000000)
            | channel(color >> 16, exponent) << 16
            | channel(color >> 8, exponent) << 8
            | channel(color, exponent);
    }

    public static int shiftHue(int color, float degrees) {

        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        float max = Math.max(r, Math.max(g, b));
        float min = Math.min(r, Math.min(g, b));
        float delta = max - min;

        if (delta == 0.0F) {
            return color;
        }

        float hue;

        if (max == r) {
            hue = ((g - b) / delta) % 6.0F;
        }
        else if (max == g) {
            hue = (b - r) / delta + 2.0F;
        }
        else {
            hue = (r - g) / delta + 4.0F;
        }

        hue = ((hue * 60.0F + degrees) % 360.0F + 360.0F) % 360.0F / 60.0F;

        float saturation = delta / max;
        float chroma = max * saturation;
        float secondary = chroma * (1.0F - Math.abs(hue % 2.0F - 1.0F));
        float offset = max - chroma;
        float nr;
        float ng;
        float nb;

        switch ((int) hue) {
            case 0 -> { nr = chroma; ng = secondary; nb = 0.0F; }
            case 1 -> { nr = secondary; ng = chroma; nb = 0.0F; }
            case 2 -> { nr = 0.0F; ng = chroma; nb = secondary; }
            case 3 -> { nr = 0.0F; ng = secondary; nb = chroma; }
            case 4 -> { nr = secondary; ng = 0.0F; nb = chroma; }
            default -> { nr = chroma; ng = 0.0F; nb = secondary; }
        }

        return (color & 0xFF000000)
            | Math.round((nr + offset) * 255.0F) << 16
            | Math.round((ng + offset) * 255.0F) << 8
            | Math.round((nb + offset) * 255.0F);
    }

    private static int channel(int value, double exponent) {
        return (int) Math.round(Math.pow((value & 0xFF) / 255.0, exponent) * 255.0);
    }

    private CalciumTintMath() {}

}
