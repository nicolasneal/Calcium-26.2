package net.nicolas.calcium.mixin.client;

import net.minecraft.client.renderer.blockentity.StandingSignRenderer;
import net.minecraft.world.level.block.PlainSignBlock;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StandingSignRenderer.class)
public class SignBlockEntityRendererMixin {

    @Redirect(method = "textTransformation", at = @At(value = "INVOKE", target = "Lorg/joml/Matrix4f;translate(Lorg/joml/Vector3fc;)Lorg/joml/Matrix4f;"))
    private static Matrix4f calcium$modifyTextOffset(Matrix4f instance, Vector3fc offset, PlainSignBlock.Attachment attachmentType, float angle, boolean isFrontText) {
        Vector3f modifiedOffset = attachmentType == PlainSignBlock.Attachment.GROUND
                ? new Vector3f(0.0F, 0.2975F, 0.0675F)
                : new Vector3f(0.0F, 0.3275F, 0.0675F);
        return instance.translate(modifiedOffset);
    }

}