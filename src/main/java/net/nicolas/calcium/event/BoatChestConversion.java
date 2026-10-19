package net.nicolas.calcium.event;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.AbstractChestBoat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class BoatChestConversion {

    public static void registerEvents() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            ItemStack stack = player.getItemInHand(hand);
            if (!stack.is(Items.CHEST) || player.isSpectator() || !(entity instanceof AbstractBoat boat) || boat instanceof AbstractChestBoat || boat.getPassengers().size() > 1) {
                return InteractionResult.PASS;
            }

            EntityType<?> chestType = chestVariant(boat.getType());
            if (chestType == null) {
                return InteractionResult.PASS;
            }

            if (world instanceof ServerLevel level && chestType.create(level, EntitySpawnReason.CONVERSION) instanceof AbstractChestBoat chestBoat) {
                convert(level, boat, chestBoat);
                level.playSound(null, boat.blockPosition(), SoundEvents.LLAMA_CHEST, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) {
                    stack.consume(1, player);
                }
            }
            return InteractionResult.SUCCESS;
        });
    }

    private static EntityType<?> chestVariant(EntityType<?> boatType) {
        Identifier key = EntityType.getKey(boatType);
        String path = key.getPath();
        String chestPath;
        if (path.endsWith("_boat")) {
            chestPath = path.substring(0, path.length() - "_boat".length()) + "_chest_boat";
        } else if (path.endsWith("_raft")) {
            chestPath = path.substring(0, path.length() - "_raft".length()) + "_chest_raft";
        } else {
            return null;
        }
        return BuiltInRegistries.ENTITY_TYPE.getOptional(Identifier.fromNamespaceAndPath(key.getNamespace(), chestPath)).orElse(null);
    }

    private static void convert(ServerLevel level, AbstractBoat boat, AbstractChestBoat chestBoat) {
        List<Entity> passengers = List.copyOf(boat.getPassengers());
        Entity leashHolder = boat.getLeashHolder();

        chestBoat.snapTo(boat.getX(), boat.getY(), boat.getZ(), boat.getYRot(), boat.getXRot());
        chestBoat.setDeltaMovement(boat.getDeltaMovement());
        chestBoat.setCustomName(boat.getCustomName());
        chestBoat.setCustomNameVisible(boat.isCustomNameVisible());

        boat.ejectPassengers();
        if (leashHolder != null) {
            boat.removeLeash();
        }
        boat.discard();

        level.addFreshEntity(chestBoat);
        if (leashHolder != null) {
            chestBoat.setLeashedTo(leashHolder, true);
        }
        for (Entity passenger : passengers) {
            passenger.startRiding(chestBoat, true, false);
        }
    }

}
