package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.nicolas.calcium.screen.TradeButtonAccess;
import net.nicolas.calcium.screen.TradeSelectionAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net.minecraft.client.gui.screens.inventory.MerchantScreen$TradeOfferButton")
public abstract class TradeOfferButtonMixin implements TradeButtonAccess {

    @Shadow @Final MerchantScreen this$0;
    @Shadow private int index;

    @Override public boolean calcium$isSelected() {
        TradeSelectionAccess selection = (TradeSelectionAccess) this.this$0;
        return selection.calcium$hasSelectedTrade() && this.index + selection.calcium$getScrollOff() == selection.calcium$getShopItem();
    }

}