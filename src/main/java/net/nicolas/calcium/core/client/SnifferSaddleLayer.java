package net.nicolas.calcium.core.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.animal.sniffer.SnifferModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.SnifferRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class SnifferSaddleLayer extends RenderLayer<SnifferRenderState, SnifferModel> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath("calcium", "sniffer"), "saddle");
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("calcium", "textures/entity/equipment/sniffer/sniffer_saddle.png");
    private static final float SCALE = 1.05F;

    private final SnifferModel saddleModel;

    public SnifferSaddleLayer(RenderLayerParent<SnifferRenderState, SnifferModel> renderer, SnifferModel saddleModel) {
        super(renderer);
        this.saddleModel = saddleModel;
    }

    @Override public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, SnifferRenderState state, float yRot, float xRot) {
        ItemStack saddle = ((SnifferSaddleAccess) state).calcium$getSaddle();
        if (!saddle.isEmpty()) {
            this.saddleModel.setupAnim(state);
            poseStack.pushPose();
            poseStack.scale(SCALE, SCALE, SCALE);
            renderColoredCutoutModel(this.saddleModel, TEXTURE, poseStack, submitNodeCollector, lightCoords, state, -1, 0);
            poseStack.popPose();
        }
    }

}