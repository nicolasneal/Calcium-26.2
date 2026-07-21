package net.nicolas.calcium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.core.client.MonitorStaticSoundHandler;
import net.nicolas.calcium.item.ModItems;
import net.nicolas.calcium.sound.ModSoundGroups;
import org.jspecify.annotations.Nullable;

public class MonitorBlockEntity extends ItemDisplayTile {

    public MonitorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.MONITOR_BLOCK_ENTITY, pos, state);
    }

    @Override public boolean canPlaceItem(int index, ItemStack stack) {
        return this.isEmpty() && stack.is(ModItems.SIGNAL_CARD);
    }

    @Override public SoundEvent getAddItemSound() {
        return ModSoundGroups.SIGNAL_CARD_INSERT;
    }

    @Override public @Nullable SoundEvent getRemoveItemSound() {
        return ModSoundGroups.SIGNAL_CARD_EJECT;
    }

    @Nullable public GlobalPos getLinkedPos() {
        return this.getDisplayedItem().get(ModItems.LINKED_FEED);
    }

    @Nullable public ViewfinderBlockEntity getLinkedViewfinder() {
        GlobalPos linked = getLinkedPos();
        if (linked == null || this.level == null || linked.dimension() != this.level.dimension()) {
            return null;
        }
        if (this.level.getBlockEntity(linked.pos()) instanceof ViewfinderBlockEntity viewfinder) {
            return viewfinder;
        }
        return null;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MonitorBlockEntity monitor) {
        if (level.isClientSide()) {
            MonitorStaticSoundHandler.tick(monitor);
        } else {
            boolean connected = monitor.getLinkedViewfinder() != null;
            if (connected != state.getValue(MonitorBlock.CONNECTED)) {
                level.setBlock(pos, state.setValue(MonitorBlock.CONNECTED, connected), Block.UPDATE_CLIENTS);
            }
        }
    }

}