package net.nicolas.calcium.screen;

import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ArmorSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.nicolas.calcium.core.Calcium;
import org.jspecify.annotations.Nullable;

public class SnifferInventoryMenu extends AbstractContainerMenu {

    private static final Identifier SADDLE_SLOT_SPRITE = Identifier.withDefaultNamespace("container/slot/saddle");
    public static final int CHEST_COLUMNS = 5;
    public static final int CHEST_ROWS = 3;
    private static final int SLOT_SADDLE = 0;
    private static final int SLOT_CHEST_START = 1;

    private @Nullable Sniffer sniffer;
    private final boolean hasChest;
    private final int playerInventoryStart;

    public SnifferInventoryMenu(int containerId, Inventory inventory, boolean hasChest) {
        this(containerId, inventory, null, hasChest);
    }

    public SnifferInventoryMenu(int containerId, Inventory inventory, @Nullable Sniffer sniffer) {
        this(containerId, inventory, sniffer, sniffer != null && ((SnifferChestAccess) sniffer).calcium$hasChest());
    }

    private SnifferInventoryMenu(int containerId, Inventory inventory, @Nullable Sniffer sniffer, boolean hasChest) {
        super(hasChest ? Calcium.SNIFFER_INVENTORY_MENU_CHESTED : Calcium.SNIFFER_INVENTORY_MENU, containerId);
        this.sniffer = sniffer;
        this.hasChest = hasChest;
        this.playerInventoryStart = SLOT_CHEST_START + (hasChest ? CHEST_COLUMNS * CHEST_ROWS : 0);

        if (sniffer != null) {
            Container saddleContainer = sniffer.createEquipmentSlotContainer(EquipmentSlot.SADDLE);
            this.addSlot(new ArmorSlot(saddleContainer, sniffer, EquipmentSlot.SADDLE, SLOT_SADDLE, 8, 17, SADDLE_SLOT_SPRITE));
        } else {
            this.addSlot(new Slot(new SimpleContainer(1), SLOT_SADDLE, 8, 17));
        }

        if (hasChest) {
            Container chestContainer = sniffer != null ? ((SnifferChestAccess) sniffer).calcium$getInventory() : new SimpleContainer(CHEST_COLUMNS * CHEST_ROWS);
            for (int row = 0; row < CHEST_ROWS; row++) {
                for (int col = 0; col < CHEST_COLUMNS; col++) {
                    this.addSlot(new Slot(chestContainer, col + row * CHEST_COLUMNS, 80 + col * 18, 17 + row * 18));
                }
            }
        }

        this.addStandardInventorySlots(inventory, 8, 84);
    }

    public @Nullable Sniffer getSniffer() {
        return this.sniffer;
    }

    public boolean hasChest() {
        return this.hasChest;
    }

    public void setClientSniffer(Sniffer sniffer) {
        this.sniffer = sniffer;
    }

    @Override public boolean stillValid(Player player) {
        return this.sniffer == null || this.sniffer.isAlive() && player.isWithinEntityInteractionRange(this.sniffer, 4.0);
    }

    @Override public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack clicked = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            clicked = stack.copy();
            if (slotIndex < SLOT_CHEST_START) {
                if (!this.moveItemStackTo(stack, this.playerInventoryStart, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(SLOT_SADDLE).mayPlace(stack) && !this.getSlot(SLOT_SADDLE).hasItem()) {
                if (!this.moveItemStackTo(stack, SLOT_SADDLE, SLOT_CHEST_START, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.playerInventoryStart == SLOT_CHEST_START || !this.moveItemStackTo(stack, SLOT_CHEST_START, this.playerInventoryStart, false)) {
                int playerContainerEnd = this.playerInventoryStart + Inventory.INVENTORY_SIZE - Inventory.SELECTION_SIZE;
                int playerHotBarStart = playerContainerEnd;
                int playerHotBarEnd = playerHotBarStart + Inventory.SELECTION_SIZE;
                if (slotIndex >= playerHotBarStart && slotIndex < playerHotBarEnd) {
                    if (!this.moveItemStackTo(stack, this.playerInventoryStart, playerContainerEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= this.playerInventoryStart && slotIndex < playerContainerEnd) {
                    if (!this.moveItemStackTo(stack, playerHotBarStart, playerHotBarEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(stack, playerHotBarStart, playerContainerEnd, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return clicked;
    }

}