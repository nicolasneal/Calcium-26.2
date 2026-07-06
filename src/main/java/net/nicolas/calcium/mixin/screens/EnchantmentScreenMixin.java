package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(EnchantmentScreen.class)
public abstract class EnchantmentScreenMixin extends AbstractContainerScreen<EnchantmentMenu> {

    public EnchantmentScreenMixin(EnchantmentMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    // Book Entity Repositioning

    @ModifyArgs(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/EnchantmentScreen;extractBook(Lnet/minecraft/client/gui/GuiGraphicsExtractor;II)V"))
    private void calcium$modifyBookPosition(Args args) {
        args.set(1, this.leftPos - 1);
        args.set(2, this.topPos + 1);
    }

    // Added Tooltips

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void calcium$renderItemHint(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {
        if (this.hoveredSlot != null && this.hoveredSlot.index == 0 && !this.hoveredSlot.hasItem()) {
            graphics.setTooltipForNextFrame(this.font, Component.literal("Add Enchantable Item"), mouseX, mouseY);
        }
    }

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void calcium$renderLapisHint(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {
        if (this.hoveredSlot != null && this.hoveredSlot.index == 1 && !this.hoveredSlot.hasItem()) {
            graphics.setTooltipForNextFrame(this.font, Component.literal("Add ").append(Component.translatable(Items.LAPIS_LAZULI.getDescriptionId())), mouseX, mouseY);
        }
    }

}
