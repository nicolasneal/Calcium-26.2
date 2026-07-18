package net.nicolas.calcium.moonlight;

import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.stream.IntStream;

public abstract class ItemDisplayTile extends RandomizableContainerBlockEntity implements WorldlyContainer {

    private NonNullList<ItemStack> stacks;

    protected ItemDisplayTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        this(type, pos, state, 1);
    }

    protected ItemDisplayTile(BlockEntityType<?> type, BlockPos pos, BlockState state, int slots) {
        super(type, pos, state);
        this.stacks = NonNullList.withSize(slots, ItemStack.EMPTY);
    }

    @Override public void setChanged() {
        if (this.level == null || level.isClientSide()) return;
        this.serverSideUpdateWhenChanged(this.level.registryAccess());
        if (this.needsToUpdateClientWhenChanged()) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
        }
        super.setChanged();
    }

    public boolean needsToUpdateClientWhenChanged() {
        return true;
    }

    public void clientSideUpdateWhenChanged(HolderLookup.Provider registries) {
    }

    public void serverSideUpdateWhenChanged(HolderLookup.Provider registries) {
    }

    public ItemStack getDisplayedItem() {
        return this.getItem(0);
    }

    public void setDisplayedItem(ItemStack stack) {
        this.setItem(0, stack);
    }

    public InteractionResult interactWithPlayerItem(Player player, InteractionHand handIn, ItemStack stack) {
        return this.interactWithPlayerItem(player, handIn, stack, 0);
    }

    public InteractionResult interactWithPlayerItem(Player player, InteractionHand handIn, ItemStack handItem, int slot) {
        if (handIn == InteractionHand.MAIN_HAND) {
            if (handItem.isEmpty()) {
                ItemStack it = this.removeItemNoUpdate(slot);
                if (!it.isEmpty()) {
                    onItemRemoved(player, it, slot);
                    if (!this.level.isClientSide()) {
                        if (!player.addItem(it)) {
                            player.drop(it, false);
                        }
                        SoundEvent removeSound = this.getRemoveItemSound();
                        if (removeSound != null) {
                            this.level.playSound(null, this.worldPosition, removeSound, SoundSource.BLOCKS, 1.0F, this.level.getRandom().nextFloat() * 0.10F + 0.95F);
                        }
                        this.setChanged();
                    } else {
                        this.clientSideUpdateWhenChanged(this.level.registryAccess());
                    }
                    return this.level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
                }
            } else if (this.canPlaceItem(slot, handItem)) {
                ItemStack it = handItem.copy();
                it.setCount(1);
                this.setItem(slot, it);
                handItem.consume(1, player);
                onItemAdded(player, it, slot);
                if (!this.level.isClientSide()) {
                    this.level.playSound(null, this.worldPosition, this.getAddItemSound(), SoundSource.BLOCKS, 1.0F, this.level.getRandom().nextFloat() * 0.10F + 0.95F);
                } else {
                    this.clientSideUpdateWhenChanged(this.level.registryAccess());
                }
                return this.level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
            }
        }
        return InteractionResult.PASS;
    }

    public void onItemRemoved(Player player, ItemStack stack, int slot) {
        level.gameEvent(GameEvent.BLOCK_CHANGE, worldPosition, GameEvent.Context.of(player, getBlockState()));
    }

    public void onItemAdded(Player player, ItemStack stack, int slot) {
        level.gameEvent(GameEvent.BLOCK_CHANGE, worldPosition, GameEvent.Context.of(player, getBlockState()));
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, worldPosition, stack);
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        }
    }

    public SoundEvent getAddItemSound() {
        return SoundEvents.ITEM_FRAME_ADD_ITEM;
    }

    public @Nullable SoundEvent getRemoveItemSound() {
        return null;
    }

    @Override protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        if (!this.tryLoadLootTable(input)) {
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
            ContainerHelper.loadAllItems(input, this.stacks);
        }
        if (this.level != null) {
            if (this.level.isClientSide()) this.clientSideUpdateWhenChanged(level.registryAccess());
            else this.serverSideUpdateWhenChanged(level.registryAccess());
        }
    }

    @Override protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (!this.trySaveLootTable(output)) {
            ContainerHelper.saveAllItems(output, this.stacks);
        }
    }

    @Override public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override public int getContainerSize() {
        return stacks.size();
    }

    @Override public int getMaxStackSize() {
        return 1;
    }

    @Nullable @Override public AbstractContainerMenu createMenu(int id, Inventory player) {
        return null;
    }

    @Override protected Component getDefaultName() {
        return this.getBlockState().getBlock().getName();
    }

    @Override protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override public void setItems(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    @Override public boolean canPlaceItem(int index, ItemStack stack) {
        return this.isEmpty();
    }

    @Override public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return false;
    }

    @Override public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return false;
    }

    @Override public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

}