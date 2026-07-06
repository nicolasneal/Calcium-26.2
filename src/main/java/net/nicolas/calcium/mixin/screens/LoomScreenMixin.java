package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.LoomScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.LoomMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LoomScreen.class)
public abstract class LoomScreenMixin extends AbstractContainerScreen<LoomMenu> {

    public LoomScreenMixin(LoomMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @ModifyConstant(method = {"extractBackground", "mouseClicked"}, constant = @Constant(intValue = 60))
    private int calcium$modifyPatternListX(int constant) {
        return 58;
    }

    @ModifyConstant(method = "extractBackground", constant = @Constant(intValue = 13, ordinal = 1))
    private int calcium$modifyPatternListVisualY(int constant) {
        return 15;
    }

    @ModifyConstant(method = "mouseClicked", constant = @Constant(intValue = 13))
    private int calcium$modifyPatternListClickY(int constant) {
        return 15;
    }

    @ModifyConstant(method = {"extractBackground", "mouseClicked"}, constant = @Constant(intValue = 119))
    private int calcium$modifyScrollbarX(int constant) {
        return 118;
    }

    @ModifyConstant(method = "extractBackground", constant = @Constant(intValue = 13, ordinal = 0))
    private int calcium$modifyScrollbarVisualY(int constant) {
        return 15;
    }

    @ModifyConstant(method = "mouseDragged", constant = @Constant(intValue = 13))
    private int calcium$modifyScrollbarDragY(int constant) {
        return 15;
    }

    @ModifyConstant(method = "mouseClicked", constant = @Constant(intValue = 9))
    private int calcium$modifyScrollbarClickY(int constant) {
        return 11;
    }

    @ModifyConstant(method = "extractBackground", constant = @Constant(intValue = 141))
    private int calcium$modifyResultPreviewX(int constant) {
        return 142;
    }

    @ModifyConstant(method = "extractBackground", constant = @Constant(intValue = 8))
    private int calcium$modifyResultPreviewY(int constant) {
        return 9;
    }
}
