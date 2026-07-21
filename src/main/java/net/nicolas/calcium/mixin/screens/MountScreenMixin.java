package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.screens.inventory.AbstractMountInventoryScreen;
import net.minecraft.client.gui.screens.inventory.NautilusInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractMountInventoryScreen.class)
public class MountScreenMixin {

    @ModifyArg(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIII)V"), index = 7)
    private int calcium$modifyChestTextureY(int y) {
        return y - 1;
    }

    @ModifyArg(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractMountInventoryScreen;extractSlot(Lnet/minecraft/client/gui/GuiGraphicsExtractor;II)V", ordinal = 0), index = 2)
    private int calcium$modifySaddleTextureY(int y) {
        return y - 1;
    }

    @ModifyArg(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractMountInventoryScreen;extractSlot(Lnet/minecraft/client/gui/GuiGraphicsExtractor;II)V", ordinal = 1), index = 2)
    private int calcium$modifyArmorTextureY(int y) {
        return y - 1;
    }

    // Moves Entity Viewport (Nautilus needs an extra offset on top of the universal one)

    @ModifyArg(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;extractEntityInInventoryFollowsMouse(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIIIIFFFLnet/minecraft/world/entity/LivingEntity;)V"), index = 2)
    private int calcium$modifyEntityViewportTopY(int y) {
        return ((Object) this instanceof NautilusInventoryScreen) ? y - 5 : y - 1;
    }

    @ModifyArg(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;extractEntityInInventoryFollowsMouse(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIIIIFFFLnet/minecraft/world/entity/LivingEntity;)V"), index = 4)
    private int calcium$modifyEntityViewportBottomY(int y) {
        return ((Object) this instanceof NautilusInventoryScreen) ? y - 5 : y - 1;
    }

}
