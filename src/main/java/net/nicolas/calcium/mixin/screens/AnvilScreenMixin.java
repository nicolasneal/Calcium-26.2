package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends AbstractContainerScreen<AnvilMenu> {

    public AnvilScreenMixin(AnvilMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @ModifyArg(method = "subInit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/EditBox;<init>(Lnet/minecraft/client/gui/Font;IIIILnet/minecraft/network/chat/Component;)V"), index = 1)
    private int calcium$modifyTextFieldWidgetX(int x) {
        return this.leftPos + 38;
    }

    @ModifyArg(method = "subInit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/EditBox;<init>(Lnet/minecraft/client/gui/Font;IIIILnet/minecraft/network/chat/Component;)V"), index = 2)
    private int calcium$modifyTextFieldWidgetY(int y) {
        return this.topPos + 21;
    }

    @ModifyArg(method = "extractErrorIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"), index = 2)
    private int calcium$modifyErrorTextureX(int x) {
        return this.leftPos + 94;
    }

    @Redirect(method = "extractLabels", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(IIIII)V"))
    private void calcium$cancelOriginalBox(GuiGraphicsExtractor instance, int x1, int y1, int x2, int y2, int color) {
    }

    @Redirect(method = "extractLabels", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;text(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"))
    private void calcium$drawLeftAlignedCost(GuiGraphicsExtractor context, Font textRenderer, Component text, int x, int y, int color) {
        context.fill(7, 68, 11 + textRenderer.width(text), 80, 1325400064);
        context.text(textRenderer, text, 9, 70, color);
    }

}
