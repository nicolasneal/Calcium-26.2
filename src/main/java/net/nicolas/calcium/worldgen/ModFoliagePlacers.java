package net.nicolas.calcium.worldgen;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.nicolas.calcium.mixin.accessors.FoliagePlacerTypeInvoker;

public class ModFoliagePlacers {

    public static final FoliagePlacerType<SpruceFoliagePlacer> SPRUCE_FOLIAGE_PLACER = register("spruce_foliage_placer", SpruceFoliagePlacer.CODEC);

    private static <P extends net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer> FoliagePlacerType<P> register(String name, com.mojang.serialization.MapCodec<P> codec) {
        return Registry.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, Identifier.fromNamespaceAndPath("calcium", name), FoliagePlacerTypeInvoker.calcium$create(codec));
    }

    public static void initialize() {
    }

}