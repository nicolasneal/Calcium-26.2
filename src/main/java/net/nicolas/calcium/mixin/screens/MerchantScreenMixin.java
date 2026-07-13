package net.nicolas.calcium.mixin.screens;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MerchantMenu;
import net.nicolas.calcium.screen.TradeSelectionAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantScreen.class)
public abstract class MerchantScreenMixin extends AbstractContainerScreen<MerchantMenu> implements TradeSelectionAccess {

    @Shadow private int shopItem;
    @Shadow private int scrollOff;
    @Unique private boolean calcium$hasSelectedTrade = false;

    public MerchantScreenMixin(MerchantMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public int calcium$getShopItem() {
        return this.shopItem;
    }

    @Override
    public int calcium$getScrollOff() {
        return this.scrollOff;
    }

    @Override
    public boolean calcium$hasSelectedTrade() {
        return this.calcium$hasSelectedTrade;
    }

    @Inject(method = "postButtonClick", at = @At("HEAD"))
    private void calcium$markTradeSelected(CallbackInfo ci) {
        this.calcium$hasSelectedTrade = true;
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;<init>(Lnet/minecraft/world/inventory/AbstractContainerMenu;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/network/chat/Component;II)V"), index = 3)
    private static int calcium$modifyScreenSize(int imageWidth) {
        return 280;
    }

    @Redirect(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"))
    private void calcium$redirectErrorTexture(GuiGraphicsExtractor instance, RenderPipeline pipeline, Identifier texture, int x, int y, int width, int height) {
        if (texture.getPath().contains("out_of_stock")) {
            instance.blitSprite(pipeline, texture, this.leftPos + 185, this.topPos + 41, width, height);
        } else {
            instance.blitSprite(pipeline, texture, x, y, width, height);
        }
    }

    @Redirect(method = "extractProgressBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"))
    private void calcium$redirectExpBarBackground(GuiGraphicsExtractor instance, RenderPipeline pipeline, Identifier texture, int x, int y, int width, int height) {
        if (texture.getPath().contains("experience_bar_background")) {
            instance.blitSprite(pipeline, texture, this.leftPos + 141, this.topPos + 15, width, 6);
        }
        else {
            instance.blitSprite(pipeline, texture, x, y, width, height);
        }
    }

    @Redirect(method = "extractProgressBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIII)V"))
    private void calcium$redirectExpBarProgress(GuiGraphicsExtractor instance, RenderPipeline pipeline, Identifier texture, int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height) {
        String path = texture.getPath();
        if (path.contains("experience_bar_current")) {
            instance.blitSprite(pipeline, texture, textureWidth, textureHeight, u, v, this.leftPos + 141, this.topPos + 15, width, height);
        }
        else if (path.contains("experience_bar_result")) {
            instance.blitSprite(pipeline, texture, textureWidth, textureHeight, u, v, x + 5, this.topPos + 15, width, height);
        }
        else {
            instance.blitSprite(pipeline, texture, textureWidth, textureHeight, u, v, x, y, width, height);
        }
    }

    @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/MerchantScreen$TradeOfferButton;<init>(Lnet/minecraft/client/gui/screens/inventory/MerchantScreen;IIILnet/minecraft/client/gui/components/Button$OnPress;)V"), index = 1)
    private int calcium$modifyButtonX(int x) {
        return x + 3;
    }

    @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/MerchantScreen$TradeOfferButton;<init>(Lnet/minecraft/client/gui/screens/inventory/MerchantScreen;IIILnet/minecraft/client/gui/components/Button$OnPress;)V"), index = 2)
    private int calcium$modifyButtonY(int y) {
        return y - 10;
    }

    @ModifyArg(method = "extractContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/MerchantScreen;extractAndDecorateCostA(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;II)V"), index = 3)
    private int calcium$modifyFirstBuyItemX(int x) {
        return x + 3;
    }

    @ModifyArg(method = "extractContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/MerchantScreen;extractAndDecorateCostA(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;II)V"), index = 4)
    private int calcium$modifyFirstBuyItemY(int y) {
        return y - 10;
    }

    // extractAndDecorateCostA reuses this same y for its own internal fakeItem/itemDecorations calls,
    // so its icon needs its own +1 nudge here rather than shifting the shared baseline above.
    @ModifyArg(method = "extractAndDecorateCostA", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fakeItem(Lnet/minecraft/world/item/ItemStack;II)V"), index = 2)
    private int calcium$modifyFirstBuyIconY(int y) {
        return y + 1;
    }

    @ModifyArg(method = "extractAndDecorateCostA", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"), index = 3)
    private int calcium$modifyDiscountStrikethroughY(int y) {
        return y + 1;
    }

    @ModifyArg(method = "extractContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/MerchantScreen;extractButtonArrows(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/item/trading/MerchantOffer;II)V"), index = 2)
    private int calcium$modifyArrowX(int x) {
        return x + 3;
    }

    @ModifyArg(method = "extractContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/MerchantScreen;extractButtonArrows(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/item/trading/MerchantOffer;II)V"), index = 3)
    private int calcium$modifyArrowY(int y) {
        return y - 9;
    }

    @ModifyArg(method = "extractContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fakeItem(Lnet/minecraft/world/item/ItemStack;II)V"), index = 1)
    private int calcium$modifyDrawItemX(int x) {
        return x + 3;
    }

    @ModifyArg(method = "extractContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fakeItem(Lnet/minecraft/world/item/ItemStack;II)V"), index = 2)
    private int calcium$modifyDrawItemY(int y) {
        return y - 9;
    }

    @ModifyArg(method = "extractContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;itemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;II)V"), index = 2)
    private int calcium$modifyDrawOverlayX(int x) {
        return x + 3;
    }

    @ModifyArg(method = "extractContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;itemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;II)V"), index = 3)
    private int calcium$modifyDrawOverlayY(int y) {
        return y - 10;
    }

    @ModifyConstant(method = {"extractScroller", "mouseClicked"}, constant = @Constant(intValue = 94))
    private int calcium$modifyScrollbarX(int originalValue) {
        return 100;
    }

    @ModifyConstant(method = {"extractScroller", "mouseClicked", "mouseDragged"}, constant = @Constant(intValue = 18))
    private int calcium$modifyScrollbarY(int originalValue) {
        return 8;
    }

}
