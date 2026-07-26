package net.nicolas.calcium.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.nicolas.calcium.entity.custom.GiantClam;
import net.nicolas.calcium.entity.custom.SeaCow;
import net.nicolas.calcium.entity.custom.Sunfish;

public class ModEntityTypes {

    public static final String MOD_ID = "calcium";

    public static final EntityType<GiantClam> GIANT_CLAM = register("giant_clam", EntityType.Builder.of(GiantClam::new, MobCategory.WATER_AMBIENT).sized(1.0F, 0.875F).eyeHeight(0.4375F).clientTrackingRange(10));
    public static final EntityType<SeaCow> SEA_COW = register("sea_cow", EntityType.Builder.of(SeaCow::new, MobCategory.WATER_AMBIENT).sized(1.6F, 1.05F).eyeHeight(0.65F).clientTrackingRange(10));
    public static final EntityType<Sunfish> SUNFISH = register("sunfish", EntityType.Builder.of(Sunfish::new, MobCategory.WATER_CREATURE).sized(0.3F, 0.3F).clientTrackingRange(10));

    public static void initialize() {

        FabricDefaultAttributeRegistry.register(GIANT_CLAM, GiantClam.createAttributes());

        FabricDefaultAttributeRegistry.register(SEA_COW, SeaCow.createAttributes());

        FabricDefaultAttributeRegistry.register(SUNFISH, Sunfish.createAttributes());

    }

    private static <T extends net.minecraft.world.entity.Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

}