package net.nicolas.calcium.mixin.screens;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.BrewingStandScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.BrewingStandMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BrewingStandScreen.class)
public abstract class BrewingStandScreenMixin extends AbstractContainerScreen<BrewingStandMenu> {

    public BrewingStandScreenMixin(BrewingStandMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Redirect(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIII)V"))
    private void calcium$redirectBrewingTextures(GuiGraphicsExtractor instance, RenderPipeline pipeline, Identifier texture, int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height) {
        String path = texture.getPath();
        if (path.contains("fuel_length")) {
            instance.blitSprite(pipeline, texture, textureWidth, textureHeight, u, v, this.leftPos + 61, this.topPos + 42, width, height);
        }
        else if (path.contains("brew_progress")) {
            instance.blitSprite(pipeline, texture, textureWidth, textureHeight, u, v, this.leftPos + 98, this.topPos + 14, width, height);
        }
        else if (path.contains("bubbles")) {
            instance.blitSprite(pipeline, texture, textureWidth, textureHeight, u, v, this.leftPos + 63, this.topPos + 12 + (textureHeight - height), width, height);
        }
        else {
            instance.blitSprite(pipeline, texture, textureWidth, textureHeight, u, v, x, y, width, height);
        }
    }

}
