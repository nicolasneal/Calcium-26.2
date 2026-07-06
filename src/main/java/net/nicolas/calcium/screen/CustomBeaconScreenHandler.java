package net.nicolas.calcium.screen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.nicolas.calcium.Calcium;
import org.jspecify.annotations.Nullable;

public class CustomBeaconScreenHandler extends AbstractContainerMenu {
    private final ContainerLevelAccess context;
    private final ContainerData propertyDelegate;

    public CustomBeaconScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainerData(3), ContainerLevelAccess.NULL);
    }

    public CustomBeaconScreenHandler(int syncId, Inventory inventory, ContainerData propertyDelegate, ContainerLevelAccess context) {
        super(Calcium.CUSTOM_BEACON_SCREEN_HANDLER, syncId);

        checkContainerDataCount(propertyDelegate, 3);
        this.propertyDelegate = propertyDelegate;
        this.context = context;
        this.addDataSlots(propertyDelegate);

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 106 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 164));
        }

    }

    @Override public boolean stillValid(Player player) {
        return stillValid(this.context, player, net.minecraft.world.level.block.Blocks.BEACON);
    }

    public int getProperties() {
        return this.propertyDelegate.get(0);
    }

    public @Nullable Holder<MobEffect> getPrimaryEffect() {
        return getStatusEffectForRawId(this.propertyDelegate.get(1));
    }

    public @Nullable Holder<MobEffect> getSecondaryEffect() {
        return getStatusEffectForRawId(this.propertyDelegate.get(2));
    }

    private static @Nullable Holder<MobEffect> getStatusEffectForRawId(int id) {
        return id == 0 ? null : BuiltInRegistries.MOB_EFFECT.asHolderIdMap().byId(id - 1);
    }

    @Override public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();

            if (slotIndex >= 0 && slotIndex < 27) {
                if (!this.moveItemStackTo(originalStack, 27, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotIndex >= 27 && slotIndex < 36) {
                if (!this.moveItemStackTo(originalStack, 0, 27, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (originalStack.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, originalStack);

        }

        return newStack;

    }

    public void setEffects(java.util.Optional<Holder<MobEffect>> primary, java.util.Optional<Holder<MobEffect>> secondary) {

        if (primary.isPresent()) {
            this.propertyDelegate.set(1, BuiltInRegistries.MOB_EFFECT.getId(primary.get().value()));
        } else {
            this.propertyDelegate.set(1, 0);
        }

        if (secondary.isPresent()) {
            this.propertyDelegate.set(2, BuiltInRegistries.MOB_EFFECT.getId(secondary.get().value()));
        } else {
            this.propertyDelegate.set(2, 0);
        }

        this.context.execute(net.minecraft.world.level.Level::blockEntityChanged);
        this.broadcastChanges();

    }

}