package net.nicolas.calcium.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.item.Item;
import net.nicolas.calcium.entity.custom.GiantClam;
import net.nicolas.calcium.entity.custom.SeaCow;
import net.nicolas.calcium.entity.custom.Sunfish;
import net.nicolas.calcium.item.ModItems;

import java.util.function.Supplier;

    // This class stores the mod's entity registrations.

public class ModEntities {

    public static final String MOD_ID = "calcium";

    // ENTITIES

    public static final EntityType<GiantClam> GIANT_CLAM = register("giant_clam", EntityType.Builder.of(GiantClam::new, MobCategory.WATER_CREATURE).sized(1.0F, 0.875F).eyeHeight(0.4375F).clientTrackingRange(10));
    public static final EntityType<SeaCow> SEA_COW = register("sea_cow", EntityType.Builder.of(SeaCow::new, MobCategory.WATER_CREATURE).sized(1.6F, 1.05F).eyeHeight(0.65F).clientTrackingRange(10));
    public static final EntityType<Sunfish> SUNFISH = register("sunfish", EntityType.Builder.of(Sunfish::new, MobCategory.WATER_CREATURE).sized(0.3F, 0.3F).clientTrackingRange(10));
    public static final EntityType<Boat> WILLOW_BOAT = register("willow_boat", EntityType.Builder.of(boatFactory(() -> ModItems.WILLOW_BOAT), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));
    public static final EntityType<ChestBoat> WILLOW_CHEST_BOAT = register("willow_chest_boat", EntityType.Builder.of(chestBoatFactory(() -> ModItems.WILLOW_CHEST_BOAT), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));
    public static final EntityType<Boat> CHORUS_BOAT = register("chorus_boat", EntityType.Builder.of(boatFactory(() -> ModItems.CHORUS_BOAT), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));
    public static final EntityType<ChestBoat> CHORUS_CHEST_BOAT = register("chorus_chest_boat", EntityType.Builder.of(chestBoatFactory(() -> ModItems.CHORUS_CHEST_BOAT), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));

    public static void initialize() {

        FabricDefaultAttributeRegistry.register(GIANT_CLAM, GiantClam.createAttributes());
        FabricDefaultAttributeRegistry.register(SEA_COW, SeaCow.createAttributes());
        FabricDefaultAttributeRegistry.register(SUNFISH, Sunfish.createAttributes());

    }

    private static <T extends net.minecraft.world.entity.Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    private static EntityType.EntityFactory<Boat> boatFactory(Supplier<Item> boatItem) {
        return (entityType, level) -> new Boat(entityType, level, boatItem);
    }

    private static EntityType.EntityFactory<ChestBoat> chestBoatFactory(Supplier<Item> dropItem) {
        return (entityType, level) -> new ChestBoat(entityType, level, dropItem);
    }

}