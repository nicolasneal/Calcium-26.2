package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.nicolas.calcium.screen.ExtraSlotsAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Set;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {

    @Shadow @Final protected AbstractContainerMenu menu;

    @Shadow protected abstract void slotClicked(Slot slot, int slotId, int buttonNum, ContainerInput containerInput);

    @Shadow private Slot getHoveredSlot(double x, double y) {
        throw new UnsupportedOperationException();
    }

    @Shadow @Final protected Set<Slot> quickCraftSlots;

    @Unique private final Set<Slot> calcium$dragSlots = new HashSet<>();

    @Inject(method = "mouseClicked", at = @At("HEAD"))
    private void calcium$resetDragSlots(MouseButtonEvent event, boolean doubleClick, CallbackInfoReturnable<Boolean> cir) {
        this.calcium$dragSlots.clear();
    }

    @Inject(method = "mouseDragged", at = @At("HEAD"))
    private void calcium$dragInteract(MouseButtonEvent event, double dx, double dy, CallbackInfoReturnable<Boolean> cir) {
        ItemStack carried = this.menu.getCarried();
        Slot slot = this.getHoveredSlot(event.x(), event.y());
        if (slot == null || carried.isEmpty()) {
            return;
        }
        boolean isBundle = carried.getItem() instanceof BundleItem;
        if (event.button() == 0 && isBundle && slot.hasItem()
            && !ItemStack.isSameItemSameComponents(slot.getItem(), carried) && this.calcium$dragSlots.add(slot)) {
            this.slotClicked(slot, slot.index, 0, ContainerInput.PICKUP);
        } else if (event.button() == 1 && !slot.hasItem() && !isBundle && this.calcium$dragSlots.add(slot)) {
            Player player = Minecraft.getInstance().player;
            if (player != null && slot.container == ((ExtraSlotsAccess) player).calcium$getExtraSlots()) {
                this.slotClicked(slot, slot.index, 1, ContainerInput.PICKUP);
            }
        }
    }

    @Inject(method = "mouseDragged", at = @At("TAIL"))
    private void calcium$suppressBundleQuickCraft(MouseButtonEvent event, double dx, double dy, CallbackInfoReturnable<Boolean> cir) {
        if (event.button() == 0 && this.menu.getCarried().getItem() instanceof BundleItem) {
            this.quickCraftSlots.clear();
        }
    }

    @ModifyExpressionValue(method = "mouseReleased", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", ordinal = 1))
    private boolean calcium$keepBundleGrabbedAfterDrag(boolean isEmpty) {
        if (!isEmpty && this.menu.getCarried().getItem() instanceof BundleItem && !this.calcium$dragSlots.isEmpty()) {
            this.calcium$dragSlots.clear();
            return true;
        }
        return isEmpty;
    }

}
