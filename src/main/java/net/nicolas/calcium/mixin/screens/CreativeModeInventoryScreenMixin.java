package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends AbstractContainerScreen<CreativeModeInventoryScreen.ItemPickerMenu> {

    public CreativeModeInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    // Shave the leftmost pixel off the Creative screen (195 -> 194 wide). leftPos = (width - imageWidth) / 2
    // keeps it centered automatically, so the box's right edge stays put and the left edge moves in by 1px.
    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 195))
    private static int calcium$shrinkCreativeWidth(int imageWidth) {
        return imageWidth - 1;
    }

    @ModifyArgs(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen$SlotWrapper;-><init>(Lnet/minecraft/world/inventory/Slot;III)V"))
    private void calcium$modifyCreativeArmorSlotPositions(Args args) {
        int slotIndex = args.get(1);
        if (slotIndex >= 5 && slotIndex < 9) {
            args.set(2, calcium$armorSlotX(slotIndex - 5));
            args.set(3, calcium$armorSlotY());
        } else if (slotIndex == 45) {
            args.set(2, calcium$secondRowSlotX(3));
            args.set(3, calcium$secondRowSlotY());
        } else if (slotIndex >= 46 && slotIndex <= 48) {
            args.set(2, calcium$secondRowSlotX(slotIndex - 46));
            args.set(3, calcium$secondRowSlotY());
        } else {
            // Every other slot follows the screen 1px to the left.
            args.set(2, (int) args.get(2) - 1);
        }
    }

    @Unique
    private int calcium$armorSlotX(int pos) {
        return 8 + pos * 18;
    }

    @Unique
    private int calcium$armorSlotY() {
        return 8;
    }

    @Unique
    private int calcium$secondRowSlotX(int pos) {
        return 8 + pos * 18;
    }

    @Unique
    private int calcium$secondRowSlotY() {
        return 32;
    }

    @ModifyArg(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/Slot;-><init>(Lnet/minecraft/world/Container;III)V"), index = 2)
    private int calcium$modifyCreativeTrashSlotX(int x) {
        return 152;
    }

    // The scrollbar (leftPos + 175) follows the interior 1px to the left in both its render and hit-test.
    @ModifyConstant(method = {"insideScrollbar", "extractBackground"}, constant = @Constant(intValue = 175))
    private int calcium$shiftScrollbarX(int x) {
        return x - 1;
    }

    @ModifyArg(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/Slot;-><init>(Lnet/minecraft/world/Container;III)V"), index = 3)
    private int calcium$modifyCreativeTrashSlotY(int y) {
        return 32;
    }

    @ModifyArg(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;extractEntityInInventoryFollowsMouse(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIIIIFFFLnet/minecraft/world/entity/LivingEntity;)V"), index = 1)
    private int calcium$modifyCreativeEntityViewportX0(int x0) {
        return this.leftPos + calcium$entityViewportX();
    }

    @ModifyArg(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;extractEntityInInventoryFollowsMouse(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIIIIFFFLnet/minecraft/world/entity/LivingEntity;)V"), index = 2)
    private int calcium$modifyCreativeEntityViewportY0(int y0) {
        return this.topPos + calcium$entityViewportY();
    }

    @ModifyArg(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;extractEntityInInventoryFollowsMouse(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIIIIFFFLnet/minecraft/world/entity/LivingEntity;)V"), index = 3)
    private int calcium$modifyCreativeEntityViewportX1(int x1) {
        return this.leftPos + calcium$entityViewportX() + calcium$entityViewportWidth();
    }

    @ModifyArg(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;extractEntityInInventoryFollowsMouse(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIIIIFFFLnet/minecraft/world/entity/LivingEntity;)V"), index = 4)
    private int calcium$modifyCreativeEntityViewportY1(int y1) {
        return this.topPos + calcium$entityViewportY() + calcium$entityViewportHeight();
    }

    @Unique
    private int calcium$entityViewportX() {
        return 84;
    }

    @Unique
    private int calcium$entityViewportY() {
        return 8;
    }

    @Unique
    private int calcium$entityViewportWidth() {
        return 28;
    }

    @Unique
    private int calcium$entityViewportHeight() {
        return 40;
    }

    @ModifyArg(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;extractEntityInInventoryFollowsMouse(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIIIIFFFLnet/minecraft/world/entity/LivingEntity;)V"), index = 5)
    private int calcium$modifyCreativeEntityViewportSize(int size) {
        return calcium$entityViewportSize();
    }

    @Unique
    private int calcium$entityViewportSize() {
        return 18;
    }

    @ModifyArg(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;extractEntityInInventoryFollowsMouse(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIIIIFFFLnet/minecraft/world/entity/LivingEntity;)V"), index = 6)
    private float calcium$modifyCreativeEntityViewportOffsetY(float offsetY) {
        return calcium$entityViewportOffsetY();
    }

    @Unique
    private float calcium$entityViewportOffsetY() {
        return 0.05F;
    }

    @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/EditBox;<init>(Lnet/minecraft/client/gui/Font;IIIILnet/minecraft/network/chat/Component;)V"), index = 1)
    private int calcium$modifySearchBoxX(int x) {
        return x - 1;
    }

}