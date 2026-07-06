package net.nicolas.calcium.mixin.screens;

import net.minecraft.world.inventory.CrafterMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CrafterMenu.class)
public class CrafterScreenHandlerMixin {

    @ModifyConstant(method = "addSlots", constant = @Constant(intValue = 26))
    private int calcium$modifyInputGridX(int constant) {
        return 31;
    }

    @ModifyArg(method = "addSlots", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/NonInteractiveResultSlot;<init>(Lnet/minecraft/world/Container;III)V"), index = 2)
    private int calcium$modifyOutputSlotX(int x) {
        return 125;
    }

}