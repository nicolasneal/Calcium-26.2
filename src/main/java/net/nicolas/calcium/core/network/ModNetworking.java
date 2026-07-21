package net.nicolas.calcium.core.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.nicolas.calcium.block.custom.MonitorBlockEntity;
import net.nicolas.calcium.screen.ExtraSlotsAccess;
import net.nicolas.calcium.block.custom.ViewfinderBlockEntity;

public class ModNetworking {

    private static final double MAX_VIEWFINDER_ORIENTATION_DISTANCE_SQ = 64.0;

    public static void initialize() {
        PayloadTypeRegistry.serverboundPlay().register(RotateExtraSlotsPayload.TYPE, RotateExtraSlotsPayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(RotateExtraSlotsPayload.TYPE, (payload, context) -> rotateExtraSlots(context.player()));

        PayloadTypeRegistry.serverboundPlay().register(SetViewfinderOrientationPayload.TYPE, SetViewfinderOrientationPayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SetViewfinderOrientationPayload.TYPE, (payload, context) -> setViewfinderOrientation(context.player(), payload));

        PayloadTypeRegistry.clientboundPlay().register(OpenSnifferInventoryPayload.TYPE, OpenSnifferInventoryPayload.STREAM_CODEC);
    }

    private static void setViewfinderOrientation(ServerPlayer player, SetViewfinderOrientationPayload payload) {

        BlockPos referencePos = payload.controllerPos().orElse(payload.pos());
        if (player.position().distanceToSqr(Vec3.atCenterOf(referencePos)) > MAX_VIEWFINDER_ORIENTATION_DISTANCE_SQ) {
            return;
        }

        if (payload.controllerPos().isPresent()) {
            if (!(player.level().getBlockEntity(payload.controllerPos().get()) instanceof MonitorBlockEntity monitor)
                    || !monitor.getBlockState().getValue(BlockStateProperties.POWERED)) {
                return;
            }
            ViewfinderBlockEntity linked = monitor.getLinkedViewfinder();
            if (linked == null || !linked.getBlockPos().equals(payload.pos())) {
                return;
            }
        }

        if (player.level().getBlockEntity(payload.pos()) instanceof ViewfinderBlockEntity viewfinder) {
            viewfinder.setAim(payload.yaw(), payload.pitch(), payload.zoom());
        }

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