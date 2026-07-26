package net.nicolas.calcium.mixin.client;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.nicolas.calcium.core.client.viewfinder.ViewfinderController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public class HudMixin {

    // Hide Crosshair in Pause Menu

    @Inject(method = "extractCrosshair", at = @At("HEAD"), cancellable = true)
    private void calcium$hideCrosshairInMenus(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        Screen screen = Minecraft.getInstance().gui.screen();
        if (screen instanceof PauseScreen || screen instanceof OptionsScreen || screen instanceof OptionsSubScreen) {
            ci.cancel();
        }
    }

    // Sub-Pixel Centered Crosshair

    @Redirect(method = "extractCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 0))
    private void calcium$centerCrosshair(GuiGraphicsExtractor instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height) {
        float guiScale = (float) Minecraft.getInstance().getWindow().getGuiScale();
        float centerX = Minecraft.getInstance().getWindow().getWidth() / guiScale / 2.0F;
        float centerY = Minecraft.getInstance().getWindow().getHeight() / guiScale / 2.0F;
        float preciseX = Math.round((centerX - width / 2.0F) * 4.0F) / 4.0F;
        float preciseY = Math.round((centerY - height / 2.0F) * 4.0F) / 4.0F;
        int baseX = Mth.floor(preciseX);
        int baseY = Mth.floor(preciseY);
        instance.pose().pushMatrix();
        instance.pose().translate(preciseX - baseX, preciseY - baseY);
        instance.blitSprite(pipeline, sprite, baseX, baseY, width, height);
        instance.pose().popMatrix();
    }

    @Inject(method = "extractHotbarAndDecorations", at = @At("HEAD"), cancellable = true)
    private void calcium$hideHotbarInViewfinder(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (ViewfinderController.isActive()) {
            ci.cancel();
        }
    }

    private static final Identifier VIEWFINDER_SCOPE_LOCATION = Identifier.fromNamespaceAndPath("calcium", "textures/misc/viewfinder_scope.png");

    @Inject(method = "extractCameraOverlays", at = @At("TAIL"))
    private void calcium$viewfinderScopeOverlay(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!ViewfinderController.isActive()) {
            return;
        }
        int guiWidth = graphics.guiWidth();
        int guiHeight = graphics.guiHeight();
        int width = Mth.floor(Math.min(guiWidth, guiHeight) * 1.125F);
        int height = width;
        int left = (guiWidth - width) / 2;
        int top = (guiHeight - height) / 2;
        int right = left + width;
        int bottom = top + height;
        graphics.blit(RenderPipelines.GUI_TEXTURED, VIEWFINDER_SCOPE_LOCATION, left, top, 0.0F, 0.0F, width, height, width, height);
        graphics.fill(RenderPipelines.GUI, 0, bottom, guiWidth, guiHeight, -16777216);
        graphics.fill(RenderPipelines.GUI, 0, 0, guiWidth, top, -16777216);
        graphics.fill(RenderPipelines.GUI, 0, top, left, bottom, -16777216);
        graphics.fill(RenderPipelines.GUI, right, top, guiWidth, bottom, -16777216);
    }

}