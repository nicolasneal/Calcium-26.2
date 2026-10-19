package net.nicolas.calcium.event;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class MinecartBlockConversion {

    private record Conversion(Item item, EntityType<? extends AbstractMinecart> type) {}

    private static final List<Conversion> CONVERSIONS = List.of(
        new Conversion(Items.CHEST, EntityTypes.CHEST_MINECART),
        new Conversion(Items.FURNACE, EntityTypes.FURNACE_MINECART),
        new Conversion(Items.HOPPER, EntityTypes.HOPPER_MINECART),
        new Conversion(Items.TNT, EntityTypes.TNT_MINECART),
        new Conversion(Items.COMMAND_BLOCK, EntityTypes.COMMAND_BLOCK_MINECART)
    );

    public static void registerEvents() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player.isSpectator() || entity.getType() != EntityTypes.MINECART || !(entity instanceof AbstractMinecart minecart) || minecart.isVehicle()) {
                return InteractionResult.PASS;
            }

            ItemStack stack = player.getItemInHand(hand);
            Conversion conversion = CONVERSIONS.stream().filter(candidate -> stack.is(candidate.item())).findFirst().orElse(null);
            if (conversion == null) {
                return InteractionResult.PASS;
            }

            if (world instanceof ServerLevel level && conversion.type().create(level, EntitySpawnReason.CONVERSION) instanceof AbstractMinecart converted) {
                converted.snapTo(minecart.getX(), minecart.getY(), minecart.getZ(), minecart.getYRot(), minecart.getXRot());
                converted.setDeltaMovement(minecart.getDeltaMovement());
                converted.setCustomName(minecart.getCustomName());
                converted.setCustomNameVisible(minecart.isCustomNameVisible());
                minecart.discard();
                level.addFreshEntity(converted);
                level.playSound(null, converted.blockPosition(), SoundEvents.LLAMA_CHEST, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) {
                    stack.consume(1, player);
                }
            }
            return InteractionResult.SUCCESS;
        });
    }

}
