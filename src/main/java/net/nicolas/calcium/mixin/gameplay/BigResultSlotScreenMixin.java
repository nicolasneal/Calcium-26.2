package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.Slot;
import net.nicolas.calcium.screen.BigResultSlot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class BigResultSlotScreenMixin {

    @Unique private static final int SLOT_SIZE = 16;
    @Unique private static final int RESULT_SLOT_SIZE = 24;
    @Unique private static final int HIGHLIGHT_PADDING = 4;

    @Shadow protected Slot hoveredSlot;

    @Shadow @Final private static Identifier SLOT_HIGHLIGHT_BACK_SPRITE;
    @Shadow @Final private static Identifier SLOT_HIGHLIGHT_FRONT_SPRITE;

    @Shadow protected boolean isHovering(int left, int top, int w, int h, double xm, double ym) {
        throw new AssertionError();
    }

    @Unique public boolean isHovering(Slot slot, double xm, double ym) {
        int size = slot instanceof BigResultSlot ? RESULT_SLOT_SIZE : SLOT_SIZE;
        int inset = (size - SLOT_SIZE) / 2;
        return this.isHovering(slot.x - inset, slot.y - inset, size, size, xm, ym);
    }

    @Inject(method = "extractSlotHighlightBack", at = @At("HEAD"), cancellable = true)
    private void calcium$largeSlotHighlightBack(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if (this.hoveredSlot instanceof BigResultSlot && this.hoveredSlot.isHighlightable()) {
            int inset = (RESULT_SLOT_SIZE - SLOT_SIZE) / 2 + HIGHLIGHT_PADDING;
            int size = RESULT_SLOT_SIZE + HIGHLIGHT_PADDING * 2;
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_HIGHLIGHT_BACK_SPRITE, this.hoveredSlot.x - inset, this.hoveredSlot.y - inset, size, size);
            ci.cancel();
        }
    }

    @Inject(method = "extractSlotHighlightFront", at = @At("HEAD"), cancellable = true)
    private void calcium$largeSlotHighlightFront(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if (this.hoveredSlot instanceof BigResultSlot && this.hoveredSlot.isHighlightable()) {
            int inset = (RESULT_SLOT_SIZE - SLOT_SIZE) / 2 + HIGHLIGHT_PADDING;
            int size = RESULT_SLOT_SIZE + HIGHLIGHT_PADDING * 2;
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_HIGHLIGHT_FRONT_SPRITE, this.hoveredSlot.x - inset, this.hoveredSlot.y - inset, size, size);
            ci.cancel();
        }
    }

}