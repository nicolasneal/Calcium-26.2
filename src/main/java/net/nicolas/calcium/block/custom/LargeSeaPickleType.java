package net.nicolas.calcium.block.custom;

import net.minecraft.util.StringRepresentable;

public enum LargeSeaPickleType implements StringRepresentable {

    BOTTOM("bottom"),
    SINGLE("single"),
    DOUBLE("double");

    private final String name;

    LargeSeaPickleType(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

}