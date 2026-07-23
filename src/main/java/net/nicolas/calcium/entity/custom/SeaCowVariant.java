package net.nicolas.calcium.entity.custom;

import net.minecraft.util.ByIdMap;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.IntFunction;

public enum SeaCowVariant {

    COLD(0),
    TEMPERATE(1),
    WARM(2);

    private static final IntFunction<SeaCowVariant> BY_ID = ByIdMap.sparse(SeaCowVariant::getId, values(), TEMPERATE);
    private final int id;

    SeaCowVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static SeaCowVariant byId(int id) {
        return BY_ID.apply(id);
    }

    public String variantName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static SeaCowVariant byName(String name) {
        return Arrays.stream(values()).filter(variant -> variant.variantName().equals(name)).findFirst().orElse(TEMPERATE);
    }

}
