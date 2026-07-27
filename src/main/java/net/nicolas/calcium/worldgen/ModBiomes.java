package net.nicolas.calcium.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

    // This class stores the mod's biome registrations.

public class ModBiomes {

    public static final String MOD_ID = "calcium";

    // OCEAN BIOMES

    // Seagrass Meadow
    // Kelp Forest
    // Hadal Trench

    private static ResourceKey<Biome> register(final String name) {
        return ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MOD_ID, name));
    }

}