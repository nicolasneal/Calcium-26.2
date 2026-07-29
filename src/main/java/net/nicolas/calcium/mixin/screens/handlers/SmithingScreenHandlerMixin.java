package net.nicolas.calcium.mixin.screens.handlers;

import net.minecraft.world.inventory.SmithingMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SmithingMenu.class)
public class SmithingScreenHandlerMixin {

    @ModifyArg(method = "createInputSlotDefinitions", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;withSlot(IIILjava/util/function/Predicate;)Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;", ordinal = 0), index = 1)
    private static int calcium$modifyTemplateSlotX(int x) {
        return 31;
    }

    @ModifyArg(method = "createInputSlotDefinitions", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;withSlot(IIILjava/util/function/Predicate;)Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;", ordinal = 0), index = 2)
    private static int calcium$modifyTemplateSlotY(int y) {
        return 35;
    }

    @ModifyArg(method = "createInputSlotDefinitions", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;withSlot(IIILjava/util/function/Predicate;)Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;", ordinal = 1), index = 1)
    private static int calcium$modifyEquipmentSlotX(int x) {
        return 13;
    }

    @ModifyArg(method = "createInputSlotDefinitions", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;withSlot(IIILjava/util/function/Predicate;)Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;", ordinal = 1), index = 2)
    private static int calcium$modifyEquipmentSlotY(int y) {
        return 35;
    }

    @ModifyArg(method = "createInputSlotDefinitions", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;withSlot(IIILjava/util/function/Predicate;)Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;", ordinal = 2), index = 1)
    private static int calcium$modifyMaterialSlotX(int x) {
        return 49;
    }

    @ModifyArg(method = "createInputSlotDefinitions", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;withSlot(IIILjava/util/function/Predicate;)Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;", ordinal = 2), index = 2)
    private static int calcium$modifyMaterialSlotY(int y) {
        return 35;
    }

    @ModifyArg(method = "createInputSlotDefinitions", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;withResultSlot(III)Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;"), index = 1)
    private static int calcium$modifyOutputSlotX(int x) {
        return 103;
    }

    @ModifyArg(method = "createInputSlotDefinitions", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;withResultSlot(III)Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$Builder;"), index = 2)
    private static int calcium$modifyOutputSlotY(int y) {
        return 35;
    }

}