package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.client.gui.screens.inventory.HangingSignEditScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSignEditScreen.class)
public abstract class AbstractSignEditScreenMixin extends Screen {

    protected AbstractSignEditScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractSignEditScreen;extractSign(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V"))
    private void calcium$drawBackgroundTexture(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {

        int bgWidth;
        int bgHeight;
        Identifier backgroundTexture;

        if ((Object) this instanceof HangingSignEditScreen) {
            bgWidth = 110;
            bgHeight = 86;
            backgroundTexture = Identifier.withDefaultNamespace("textures/gui/container/hanging_sign.png");
        }
        else {
            bgWidth = 122;
            bgHeight = 74;
            backgroundTexture = Identifier.withDefaultNamespace("textures/gui/container/sign.png");
        }

        int x = (this.width - bgWidth) / 2;
        int y = (this.height - bgHeight) / 2;

        graphics.blit(RenderPipelines.GUI_TEXTURED, backgroundTexture, x, y, 0.0F, 0.0F, bgWidth, bgHeight, 128, 128);

    }

    @Redirect(method = "extractSign", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractSignEditScreen;getSignYOffset()F"))
    private float calcium$modifySignYPosition(AbstractSignEditScreen instance) {
        if (instance instanceof HangingSignEditScreen) {
            return 235.0F;
        }
        else {
            return 242.0F;
        }
    }

    @Inject(method = "extractSign", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractSignEditScreen;extractSignText(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lorg/joml/Vector2f;)V"))
    private void calcium$offsetSignText(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if ((Object) this instanceof HangingSignEditScreen) {
            graphics.pose().translate(0.0F, 5.0F);
        }
    }

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractSignEditScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"))
    private GuiEventListener calcium$preventDoneButton(AbstractSignEditScreen instance, GuiEventListener element) {
        return null;
    }

}
