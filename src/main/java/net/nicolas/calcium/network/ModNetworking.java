package net.nicolas.calcium.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.nicolas.calcium.player.ExtraSlotsAccess;

public class ModNetworking {

    public static void initialize() {
        PayloadTypeRegistry.serverboundPlay().register(RotateExtraSlotsPayload.TYPE, RotateExtraSlotsPayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(RotateExtraSlotsPayload.TYPE, (payload, context) -> rotateExtraSlots(context.player()));
    }

    private static void rotateExtraSlots(ServerPlayer player) {
        Container extraSlots = ((ExtraSlotsAccess) player).calcium$getExtraSlots();
        Inventory inventory = player.getInventory();
        ItemStack offhand = inventory.getItem(Inventory.SLOT_OFFHAND);
        ItemStack slot0 = extraSlots.getItem(0);
        ItemStack slot1 = extraSlots.getItem(1);
        ItemStack slot2 = extraSlots.getItem(2);
        inventory.setItem(Inventory.SLOT_OFFHAND, slot2);
        extraSlots.setItem(0, offhand);
        extraSlots.setItem(1, slot0);
        extraSlots.setItem(2, slot1);
    }

}