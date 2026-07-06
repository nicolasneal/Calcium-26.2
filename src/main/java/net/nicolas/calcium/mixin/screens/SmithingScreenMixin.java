package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.SmithingMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(SmithingScreen.class)
public abstract class SmithingScreenMixin extends ItemCombinerScreen<SmithingMenu> {

    public SmithingScreenMixin(SmithingMenu handler, Inventory inventory, Component title, Identifier texture) {
        super(handler, inventory, title, texture);
    }

    @ModifyArg(method = "extractErrorIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"), index = 2)
    private int calcium$modifyErrorTextureX(int x) {
        return this.leftPos + 56;
    }

    @ModifyArg(method = "extractErrorIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"), index = 3)
    private int calcium$modifyErrorTextureY(int y) {
        return this.topPos + 33;
    }

    @ModifyArg(method = "extractOnboardingTooltips", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/SmithingScreen;isHovering(IIIIDD)Z"), index = 0)
    private int calcium$modifyErrorTooltipX(int x) {
        return 15;
    }

    @ModifyArg(method = "extractOnboardingTooltips", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/SmithingScreen;isHovering(IIIIDD)Z"), index = 1)
    private int calcium$modifyErrorTooltipY(int y) {
        return 25;
    }

    @ModifyArgs(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;entity(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;FLorg/joml/Vector3fc;Lorg/joml/Quaternionfc;Lorg/joml/Quaternionfc;IIII)V"))
    private void calcium$modifyArmorStandPosition(Args args) {
        args.set(5, this.leftPos + 125);
        args.set(6, this.topPos + 9);
        args.set(7, this.leftPos + 165);
        args.set(8, this.topPos + 69);
    }

}
