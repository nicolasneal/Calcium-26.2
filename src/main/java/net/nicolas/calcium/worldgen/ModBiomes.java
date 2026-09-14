package net.nicolas.calcium.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

    // This class stores the mod's biome registrations.

public class ModBiomes {

    public static final String MOD_ID = "calcium";

    public static final ResourceKey<Biome> TUNDRA = register("tundra");
    public static final ResourceKey<Biome> AUTUMNAL_FOREST = register("autumnal_forest");
    public static final ResourceKey<Biome> BOG = register("bog");
    public static final ResourceKey<Biome> PRAIRIE = register("prairie");
    public static final ResourceKey<Biome> WOODLAND = register("woodland");
    public static final ResourceKey<Biome> MESA = register("mesa");
    public static final ResourceKey<Biome> OASIS = register("oasis");
    public static final ResourceKey<Biome> DUNES = register("dunes");
    public static final ResourceKey<Biome> BLASTED_DUNES = register("blasted_dunes");
    public static final ResourceKey<Biome> DESERT_MOUNTAINS = register("desert_mountains");

    private static ResourceKey<Biome> register(final String name) {
        return ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MOD_ID, name));
    }

}