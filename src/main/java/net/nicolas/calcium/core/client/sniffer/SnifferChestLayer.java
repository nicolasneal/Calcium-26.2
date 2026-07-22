package net.nicolas.calcium.core.client.sniffer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.animal.sniffer.SnifferModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.SnifferRenderState;
import net.minecraft.resources.Identifier;

public class SnifferChestLayer extends RenderLayer<SnifferRenderState, SnifferModel> {

    private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/entity/sniffer/sniffer.png");

    private final SnifferChestModel chestModel;

    public SnifferChestLayer(RenderLayerParent<SnifferRenderState, SnifferModel> renderer, SnifferChestModel chestModel) {
        super(renderer);
        this.chestModel = chestModel;
    }

    @Override public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, SnifferRenderState state, float yRot, float xRot) {
        if (((SnifferChestRenderAccess) state).calcium$hasChest()) {
            this.chestModel.setupAnim(state);
            renderColoredCutoutModel(this.chestModel, TEXTURE, poseStack, submitNodeCollector, lightCoords, state, -1, 0);
        }
    }

}