package net.nicolas.calcium.mixin.screens.guis;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.nicolas.calcium.screen.BigResultSlot;
import net.nicolas.calcium.screen.CustomSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GhostSlots.class)
public abstract class GhostSlotsMixin {

    @Redirect(method = "lambda$extractRenderState$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(IIIII)V", ordinal = 2))
    private void calcium$resizeResultSlotTint(GuiGraphicsExtractor graphics, int x0, int y0, int x1, int y1, int col, @Local(argsOnly = true, name = "slot") Slot slot) {
        if (slot instanceof BigResultSlot) {
            graphics.fill(x0 - 4, y0 - 4, x1 + 4, y1 + 4, col);
        } else {
            graphics.fill(x0, y0, x1, y1, col);
        }
    }

    @Redirect(method = "lambda$extractRenderState$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fakeItem(Lnet/minecraft/world/item/ItemStack;II)V"))
    private void calcium$decorateQuantityGhostSlot(GuiGraphicsExtractor graphics, ItemStack itemStack, int x, int y, @Local(argsOnly = true, name = "slot") Slot slot, @Local(argsOnly = true, name = "minecraft") Minecraft minecraft) {
        graphics.fakeItem(itemStack, x, y);
        if (slot instanceof CustomSlot customSlot && customSlot.showsGhostQuantity()) {
            graphics.itemDecorations(minecraft.font, itemStack, x, y);
        }
    }

}