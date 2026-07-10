package net.nicolas.calcium.mixin.screens;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractFurnaceScreen.class)
public abstract class AbstractFurnaceScreenMixin extends AbstractRecipeBookScreen<AbstractFurnaceMenu> {

    public AbstractFurnaceScreenMixin(AbstractFurnaceMenu handler, net.minecraft.client.gui.screens.recipebook.RecipeBookComponent recipeBook, net.minecraft.world.entity.player.Inventory inventory, net.minecraft.network.chat.Component title) {
        super(handler, recipeBook, inventory, title);
    }

    @Redirect(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIII)V"))
    private void calcium$redirectFurnaceOverlays(GuiGraphicsExtractor instance, RenderPipeline pipeline, Identifier texture, int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height) {

        if (textureWidth == 14 && textureHeight == 14) {
            int newX = 48;
            int baseNewY = 36;
            instance.blitSprite(pipeline, texture, textureWidth, textureHeight, u, v, this.leftPos + newX, this.topPos + baseNewY + (14 - height), width, height);
        }

        else if (textureWidth == 24 && textureHeight == 16) {
            int newX = 72;
            int newY = 34;
            instance.blitSprite(pipeline, texture, textureWidth, textureHeight, u, v, this.leftPos + newX, this.topPos + newY, width, height);
        }

    }

}
