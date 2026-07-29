package net.nicolas.calcium.mixin.client.renderers;

import net.minecraft.client.renderer.blockentity.CampfireRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

    // This mixin changes the height at which items on Campfires render. In Vanilla, items render slightly floating.

@Mixin(CampfireRenderer.class)
public class CampfireBlockEntityRendererMixin {

    @ModifyArg(method = "submit", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 0), index = 1)
    private float modifyHeight(float originalY) {
        return 0.465F;
    }

}