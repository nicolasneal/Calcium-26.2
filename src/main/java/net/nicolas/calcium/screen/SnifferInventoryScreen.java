package net.nicolas.calcium.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.player.Inventory;
import org.jspecify.annotations.Nullable;

public class SnifferInventoryScreen extends AbstractContainerScreen<SnifferInventoryMenu> {

    private static final Identifier BACKGROUND = Identifier.withDefaultNamespace("textures/gui/container/horse.png");
    private static final Identifier CHEST_SLOTS_SPRITE = Identifier.withDefaultNamespace("container/horse/chest_slots");

    private float xMouse;
    private float yMouse;

    public SnifferInventoryScreen(SnifferInventoryMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        this.xMouse = mouseX;
        this.yMouse = mouseY;
        super.extractRenderState(graphics, mouseX, mouseY, partialTicks);
    }

    @Override public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        super.extractBackground(graphics, mouseX, mouseY, partialTicks);
        int xo = (this.width - this.imageWidth) / 2;
        int yo = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, xo, yo, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        if (this.getMenu().hasChest()) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, CHEST_SLOTS_SPRITE, 90, 54, 0, 0, xo + 79, yo + 16, SnifferInventoryMenu.CHEST_COLUMNS * 18, SnifferInventoryMenu.CHEST_ROWS * 18);
        }
        Sniffer sniffer = this.getMenu().getSniffer();
        if (sniffer != null) {
            InventoryScreen.extractEntityInInventoryFollowsMouse(graphics, xo + 26, yo + 17, xo + 78, yo + 69, 17, 0.25F, this.xMouse, this.yMouse, sniffer);
        }
    }

}