package net.nicolas.calcium.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Lightmap;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.material.FluidState;
import net.nicolas.calcium.fluid.ModFluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenEffectRenderer.class)
public abstract class InGameOverlayRendererMixin {

    @Shadow @Final private Minecraft minecraft;
    @Unique private static final Identifier ECTOPLASM_TEXTURE = Identifier.fromNamespaceAndPath("calcium", "textures/block/ectoplasm_overlay.png");

    @Inject(method = "submit", at = @At("HEAD"))
    private void calcium$renderEctoplasmOverlay(boolean isFirstPerson, boolean sleeping, float tickProgress, SubmitNodeCollector submitNodeCollector, boolean hideGui, CallbackInfo ci) {

        if (this.minecraft.player == null || sleeping) {
            return;
        }

        BlockPos pos = this.minecraft.gameRenderer.mainCamera().blockPosition();
        FluidState fluidState = this.minecraft.player.level().getFluidState(pos);

        if (fluidState.getType() == ModFluids.ECTOPLASM_STILL || fluidState.getType() == ModFluids.ECTOPLASM_FLOWING) {
            PoseStack matrices = new PoseStack();
            calcium$submitOverlay(this.minecraft, matrices, submitNodeCollector, pos);
        }

    }

    @Unique private void calcium$submitOverlay(Minecraft client, PoseStack matrices, SubmitNodeCollector submitNodeCollector, BlockPos pos) {

        assert client.player != null;
        float brightness = Lightmap.getBrightness(client.player.level().dimensionType(), client.player.level().getMaxLocalRawBrightness(pos));
        int color = ARGB.colorFromFloat(1.0F, brightness, brightness, brightness);

        submitNodeCollector.submitCustomGeometry(matrices, RenderTypes.blockScreenEffect(ECTOPLASM_TEXTURE), (pose, builder) -> {
            var matrix4f = pose.pose();
            builder.addVertex(matrix4f, -1.0F, -1.0F, -0.5F).setUv(4.0F, 4.0F).setColor(color);
            builder.addVertex(matrix4f, 1.0F, -1.0F, -0.5F).setUv(0.0F, 4.0F).setColor(color);
            builder.addVertex(matrix4f, 1.0F, 1.0F, -0.5F).setUv(0.0F, 0.0F).setColor(color);
            builder.addVertex(matrix4f, -1.0F, 1.0F, -0.5F).setUv(4.0F, 0.0F).setColor(color);
        });

    }

}
