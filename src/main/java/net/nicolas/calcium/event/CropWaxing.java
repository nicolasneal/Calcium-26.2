package net.nicolas.calcium.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;

    // This class encodes Crop Waxing, the feature where right-clicking a crop with a honeycomb freezes it in
    // its current growth stage and disables harvesting in the case of crops like Sweet Berry Bush or Azureberry Vines.

public class CropWaxing {

    public interface Waxable {

        BooleanProperty WAXED = BooleanProperty.create("waxed");

        default void onWaxed(ServerLevel level, BlockPos pos, BlockState waxedState) {}

    }

    public static void registerEvents() {

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);
            ItemStack itemStack = player.getItemInHand(hand);

            if (state.getBlock() instanceof Waxable && !state.getValue(Waxable.WAXED) && itemStack.is(Items.HONEYCOMB)) {
                if (!world.isClientSide() && world instanceof ServerLevel serverLevel) {
                    waxBlock(serverLevel, player, pos, state, itemStack);
                }
                world.levelEvent(player, LevelEvent.PARTICLES_AND_SOUND_WAX_ON, pos, 0);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;

        });

    }

    private static void waxBlock(ServerLevel level, Player player, BlockPos pos, BlockState state, ItemStack itemStack) {

        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, itemStack);
        }

        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        BlockState waxedState = state.setValue(Waxable.WAXED, true);
        level.setBlock(pos, waxedState, 11);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, waxedState));
        ((Waxable) waxedState.getBlock()).onWaxed(level, pos, waxedState);

    }

}