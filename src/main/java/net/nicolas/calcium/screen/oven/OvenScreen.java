package net.nicolas.calcium.screen.oven;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class OvenScreen extends AbstractRecipeBookScreen<OvenScreenHandler> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("calcium", "textures/gui/container/oven.png");
    private static final Identifier LIT_PROGRESS_SPRITE = Identifier.fromNamespaceAndPath("calcium", "container/oven/lit_progress");
    private static final Identifier BURN_PROGRESS_SPRITE = Identifier.fromNamespaceAndPath("calcium", "container/oven/burn_progress");

    public OvenScreen(OvenScreenHandler menu, Inventory inventory, Component title) {
        super(menu, new OvenRecipeBookComponent(menu), inventory, title);
    }

    @Override protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        graphics.text(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, -12566464, false);
    }

    @Override protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + 20, this.height / 2 - 49);
    }

    @Override public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);

        int xo = this.leftPos;
        int yo = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, xo, yo, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        if (this.menu.isLit()) {
            int litProgressHeight = Mth.ceil(this.menu.getLitProgress() * 11.0F) + 1;
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, LIT_PROGRESS_SPRITE, 16, 12, 0, 12 - litProgressHeight, xo + 48, yo + 37 + 12 - litProgressHeight, 16, litProgressHeight);
        }

        int burnProgressWidth = Mth.ceil(this.menu.getBurnProgress() * 24.0F);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BURN_PROGRESS_SPRITE, 24, 16, 0, 0, xo + 99, yo + 34, burnProgressWidth, 16);

    }

}