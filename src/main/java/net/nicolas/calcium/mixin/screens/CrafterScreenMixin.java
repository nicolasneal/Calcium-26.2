package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.screens.inventory.CrafterScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CrafterScreen.class)
public class CrafterScreenMixin {

    @ModifyConstant(method = "extractRedstone", constant = @Constant(intValue = 9))
    private int calcium$modifyArrowX(int constant) {
        return 6;
    }

    @ModifyConstant(method = "extractRedstone", constant = @Constant(intValue = 48))
    private int calcium$modifyArrowY(int constant) {
        return 49;
    }

}
