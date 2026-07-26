package net.nicolas.calcium.entity.custom;

import net.minecraft.util.ByIdMap;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.IntFunction;

public enum SunfishVariant {

    EARL(0),
    EVE(1),
    NOON(2),
    STARRY(3);

    private static final IntFunction<SunfishVariant> BY_ID = ByIdMap.sparse(SunfishVariant::getId, values(), EARL);
    private final int id;

    SunfishVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static SunfishVariant byId(int id) {
        return BY_ID.apply(id);
    }

    public String variantName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static SunfishVariant byName(String name) {
        return Arrays.stream(values()).filter(variant -> variant.variantName().equals(name)).findFirst().orElse(EARL);
    }

}