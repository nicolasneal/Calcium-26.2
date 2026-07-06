package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.StonecutterScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.StonecutterMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(StonecutterScreen.class)
public abstract class StonecutterScreenMixin extends AbstractContainerScreen<StonecutterMenu> {

    public StonecutterScreenMixin(StonecutterMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @ModifyConstant(method = {"extractBackground", "extractTooltip", "mouseClicked"}, constant = @Constant(intValue = 52))
    private int calcium$modifyRecipeGridX(int constant) {
        return 48;
    }

    @ModifyConstant(method = {"extractBackground", "extractTooltip", "mouseClicked"}, constant = @Constant(intValue = 14))
    private int calcium$modifyRecipeGridY(int constant) {
        return 15;
    }

    @ModifyConstant(method = {"extractBackground", "mouseClicked"}, constant = @Constant(intValue = 119))
    private int calcium$modifyScrollbarX(int constant) {
        return 116;
    }

    @Unique int ScrollBarY = 16;

    @ModifyConstant(method = "extractBackground", constant = @Constant(intValue = 15, ordinal = 0))
    private int calcium$modifyScrollbarVisualY(int constant) {
        return ScrollBarY;
    }

    @ModifyConstant(method = "mouseClicked", constant = @Constant(intValue = 9))
    private int calcium$modifyScrollbarClickY(int constant) {
        return ScrollBarY - 6;
    }

    @ModifyConstant(method = "mouseDragged", constant = @Constant(intValue = 14))
    private int calcium$modifyScrollbarDragY(int constant) {
        return ScrollBarY - 1;
    }

    @ModifyConstant(method = "extractBackground", constant = @Constant(floatValue = 41.0F))
    private float calcium$modifyScrollTravelFloat(float constant) {
        return 39.0F;
    }


    @ModifyConstant(method = "mouseClicked", constant = @Constant(intValue = 54))
    private int calcium$modifyClickAreaHeight(int constant) {
        return 52;
    }

    @ModifyConstant(method = "mouseDragged", constant = @Constant(intValue = 54))
    private int calcium$modifyDragAreaHeight(int constant) {
        return 52;
    }

}
