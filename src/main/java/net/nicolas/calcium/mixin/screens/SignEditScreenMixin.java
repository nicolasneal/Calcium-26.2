package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignEditScreen.class)
public abstract class SignEditScreenMixin extends AbstractSignEditScreen {

    @Shadow @Final private Identifier texture;

    public SignEditScreenMixin(SignBlockEntity blockEntity, boolean front, boolean filtered) {
        super(blockEntity, front, filtered);
    }

    @Inject(method = "extractSignBackground", at = @At("HEAD"), cancellable = true)
    private void renderSignBackgroundReplacement(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        graphics.pose().pushMatrix();
        graphics.pose().translate(-48.0f, -26.0f);
        graphics.pose().scale(6.0f, 6.0f);
        graphics.blit(RenderPipelines.GUI_TEXTURED, this.texture, 0, 0, 0.0f, 0.0f, 16, 8, 16, 8);
        graphics.pose().popMatrix();

        ci.cancel();

    }

}
