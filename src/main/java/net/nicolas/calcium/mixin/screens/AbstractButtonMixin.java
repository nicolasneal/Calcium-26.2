package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.resources.Identifier;
import net.nicolas.calcium.screen.TradeButtonAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractButton.class)
public abstract class AbstractButtonMixin {

    @Unique private static final Identifier TRADING_SLOT = Identifier.fromNamespaceAndPath("calcium", "container/villager/trading_slot");
    @Unique private static final Identifier TRADING_SLOT_HIGHLIGHTED = Identifier.fromNamespaceAndPath("calcium", "container/villager/trading_slot_highlighted");
    @Unique private static final Identifier TRADING_SLOT_SELECTED = Identifier.fromNamespaceAndPath("calcium", "container/villager/trading_slot_selected");

    @ModifyArg(method = "extractDefaultSprite", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V"), index = 1)
    private Identifier calcium$redirectTradeButtonSprite(Identifier sprite) {
        if (!(this instanceof TradeButtonAccess tradeButton)) {
            return sprite;
        }
        if (tradeButton.calcium$isSelected()) {
            return TRADING_SLOT_SELECTED;
        }
        return sprite.getPath().contains("highlighted") ? TRADING_SLOT_HIGHLIGHTED : TRADING_SLOT;
    }

}