package net.nicolas.calcium.mixin.screens;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CartographyTableScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CartographyTableScreen.class)
public abstract class CartographyTableScreenMixin extends AbstractContainerScreen<CartographyTableMenu> {

    public CartographyTableScreenMixin(CartographyTableMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Redirect(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"))
    private void calcium$redirectErrorTexture(GuiGraphicsExtractor instance, RenderPipeline pipeline, Identifier texture, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, texture, this.leftPos + 31, this.topPos + 32, width, height);
    }

    @Shadow private void extractMap(GuiGraphicsExtractor context, MapId mapId, MapItemSavedData mapState, int x, int y, float scale) {}

    @Redirect(method = "extractResultingMap(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/level/saveddata/maps/MapId;Lnet/minecraft/world/level/saveddata/maps/MapItemSavedData;ZZZZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"))
    private void calcium$redirectMapOverlays(GuiGraphicsExtractor instance, RenderPipeline pipeline, Identifier texture, int x, int y, int width, int height) {
        String path = texture.getPath();

        if (path.contains("scaled_map")) {
            instance.blitSprite(pipeline, texture, this.leftPos + 64, this.topPos + 10, width, height);
        } else if (path.contains("duplicated_map")) {
            if (x == this.leftPos + 83) {
                instance.blitSprite(pipeline, texture, this.leftPos + 80, this.topPos + 10, width, height);
            }
            else {
                instance.blitSprite(pipeline, texture, this.leftPos + 64, this.topPos + 26, width, height);
            }
        } else if (path.contains("map")) { // Standard map background
            instance.blitSprite(pipeline, texture, this.leftPos + 64, this.topPos + 10, width, height);
        } else if (path.contains("locked")) { // Lock icon
            instance.blitSprite(pipeline, texture, this.leftPos + 115, this.topPos + 57, width, height);
        } else {
            instance.blitSprite(pipeline, texture, x, y, width, height);
        }
    }

    @Redirect(method = "extractResultingMap(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/level/saveddata/maps/MapId;Lnet/minecraft/world/level/saveddata/maps/MapItemSavedData;ZZZZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/CartographyTableScreen;extractMap(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/level/saveddata/maps/MapId;Lnet/minecraft/world/level/saveddata/maps/MapItemSavedData;IIF)V"))
    private void calcium$redirectMapContent(CartographyTableScreen instance, GuiGraphicsExtractor context, MapId mapId, MapItemSavedData mapState, int x, int y, float scale) {
        if (scale == 0.226F) {
            this.extractMap(context, mapId, mapState, this.leftPos + 82, this.topPos + 28, scale);
        }
        else if (scale == 0.34F) {
            if (y == this.topPos + 16) {
                this.extractMap(context, mapId, mapState, this.leftPos + 83, this.topPos + 13, scale);
            }
            else {
                this.extractMap(context, mapId, mapState, this.leftPos + 67, this.topPos + 29, scale);
            }
        }
        else {
            this.extractMap(context, mapId, mapState, this.leftPos + 68, this.topPos + 14, scale);
        }
    }

}
