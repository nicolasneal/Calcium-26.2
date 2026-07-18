package net.nicolas.calcium.worldgen;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

public enum ZPositionDensityFunction implements DensityFunction.SimpleFunction {

    INSTANCE;

    public static final KeyDispatchDataCodec<DensityFunction> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(INSTANCE));

    @Override public double compute(FunctionContext context) {
        return context.blockZ();
    }

    @Override public double minValue() {
        return -30000000.0;
    }

    @Override public double maxValue() {
        return 30000000.0;
    }

    @Override public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }

}