package net.nicolas.calcium.entity;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.nicolas.calcium.entity.custom.GiantClam;
import net.nicolas.calcium.entity.custom.SeaCow;

public class ModEntityTypes {

    public static final String MOD_ID = "calcium";

    public static final EntityType<GiantClam> GIANT_CLAM = register("giant_clam", EntityType.Builder.of(GiantClam::new, MobCategory.WATER_AMBIENT).sized(1.0F, 0.875F).eyeHeight(0.4375F).clientTrackingRange(10));
    public static final EntityType<SeaCow> SEA_COW = register("sea_cow", EntityType.Builder.of(SeaCow::new, MobCategory.WATER_AMBIENT).sized(1.6F, 1.05F).eyeHeight(0.65F).clientTrackingRange(10));

    public static void initialize() {

        FabricDefaultAttributeRegistry.register(GIANT_CLAM, GiantClam.createAttributes());
        SpawnPlacements.register(GIANT_CLAM, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GiantClam::checkSpawnRules);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.WARM_OCEAN), MobCategory.WATER_AMBIENT, GIANT_CLAM, 5, 1, 2);

        FabricDefaultAttributeRegistry.register(SEA_COW, SeaCow.createAttributes());

    }

    private static <T extends net.minecraft.world.entity.Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

}