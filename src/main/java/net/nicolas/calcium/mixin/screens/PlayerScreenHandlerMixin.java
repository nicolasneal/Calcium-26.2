package net.nicolas.calcium.mixin.screens;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.nicolas.calcium.player.ExtraSlotsAccess;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryMenu.class)
public abstract class PlayerScreenHandlerMixin extends AbstractContainerMenu {

    protected PlayerScreenHandlerMixin(@Nullable MenuType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void calcium$addExtraSlots(Inventory inventory, boolean active, Player owner, CallbackInfo ci) {
        Container extraSlots = ((ExtraSlotsAccess) owner).calcium$getExtraSlots();
        for (int i = 0; i < 3; i++) {
            this.addSlot(new Slot(extraSlots, i, calcium$extraSlotX(), calcium$extraSlotY(i)));
        }
    }

    @Unique
    private int calcium$extraSlotX() {
        return calcium$offhandSlotX();
    }

    @Unique
    private int calcium$extraSlotY(int index) {
        return calcium$offhandSlotY() - 18 - index * 18;
    }

    @Unique
    private int calcium$armorSlotX() {
        return 8;
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ArmorSlot;-><init>(Lnet/minecraft/world/Container;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;IIILnet/minecraft/resources/Identifier;)V"), index = 4)
    private int calcium$modifyArmorSlotsX(int x) {
        return calcium$armorSlotX();
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ArmorSlot;-><init>(Lnet/minecraft/world/Container;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;IIILnet/minecraft/resources/Identifier;)V"), index = 5)
    private int calcium$modifyArmorSlotsY(int y) {
        return y;
    }

    @Unique
    private int calcium$offhandSlotX() {
        return 77;
    }

    @Unique
    private int calcium$offhandSlotY() {
        return 62;
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu$1;-><init>(Lnet/minecraft/world/inventory/InventoryMenu;Lnet/minecraft/world/Container;IIILnet/minecraft/world/entity/player/Player;)V"), index = 3)
    private int calcium$modifyOffhandSlotX(int x) {
        return calcium$offhandSlotX();
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu$1;-><init>(Lnet/minecraft/world/inventory/InventoryMenu;Lnet/minecraft/world/Container;IIILnet/minecraft/world/entity/player/Player;)V"), index = 4)
    private int calcium$modifyOffhandSlotY(int y) {
        return calcium$offhandSlotY();
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu;addCraftingGridSlots(II)V"), index = 0)
    private int calcium$modifyInputSlotsX(int x) {
        return 117;
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu;addCraftingGridSlots(II)V"), index = 1)
    private int calcium$modifyInputSlotsY(int y) {
        return 11;
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu;addResultSlot(Lnet/minecraft/world/entity/player/Player;II)Lnet/minecraft/world/inventory/Slot;"), index = 1)
    private int calcium$modifyResultSlotX(int x) {
        return 126;
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu;addResultSlot(Lnet/minecraft/world/entity/player/Player;II)Lnet/minecraft/world/inventory/Slot;"), index = 2)
    private int calcium$modifyResultSlotY(int y) {
        return 58;
    }

}