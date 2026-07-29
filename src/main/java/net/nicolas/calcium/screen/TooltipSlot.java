package net.nicolas.calcium.screen;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;

public interface TooltipSlot {

    Component getTooltip();

    static void extractTooltip(Slot hoveredSlot, GuiGraphicsExtractor graphics, Font font, int mouseX, int mouseY) {
        if (hoveredSlot instanceof TooltipSlot tooltipSlot && !hoveredSlot.hasItem()) {
            graphics.setTooltipForNextFrame(font, tooltipSlot.getTooltip(), mouseX, mouseY);
        }
    }

}
