package net.nicolas.calcium.core.util;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.entity.vehicle.minecart.MinecartCommandBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gamerules.GameRules;
import org.jspecify.annotations.Nullable;

public class MinecartDrops {

    public static void dropSeparately(VehicleEntity instance, ServerLevel level, Item dropItem, Operation<Void> original) {
        Item block = blockFor(instance, dropItem);
        if (block == null) {
            original.call(instance, level, dropItem);
            return;
        }
        original.call(instance, level, Items.MINECART);
        if (level.getGameRules().get(GameRules.ENTITY_DROPS)) {
            instance.spawnAtLocation(level, new ItemStack(block));
        }
    }

    private static @Nullable Item blockFor(VehicleEntity vehicle, Item dropItem) {
        if (vehicle instanceof MinecartCommandBlock) {
            return Items.COMMAND_BLOCK;
        }
        if (dropItem == Items.CHEST_MINECART) {
            return Items.CHEST;
        }
        if (dropItem == Items.FURNACE_MINECART) {
            return Items.FURNACE;
        }
        if (dropItem == Items.HOPPER_MINECART) {
            return Items.HOPPER;
        }
        return dropItem == Items.TNT_MINECART ? Items.TNT : null;
    }

}
