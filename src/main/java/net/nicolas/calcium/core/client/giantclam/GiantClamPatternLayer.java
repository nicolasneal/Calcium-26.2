package net.nicolas.calcium.core.client.giantclam;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.nicolas.calcium.entity.custom.GiantClamVariant;

public class GiantClamPatternLayer extends RenderLayer<GiantClamRenderState, GiantClamModel> {

    private final GiantClamModel model;

    public GiantClamPatternLayer(RenderLayerParent<GiantClamRenderState, GiantClamModel> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new GiantClamModel(modelSet.bakeLayer(GiantClamRenderer.LAYER));
    }

    @Override public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, GiantClamRenderState state, float yRot, float xRot) {
        if (state.pattern == GiantClamVariant.Pattern.NO_PATTERN) {
            return;
        }

        Identifier texture = Identifier.fromNamespaceAndPath("calcium", "textures/entity/clam/giant_clam_" + state.pattern.patternName() + ".png");
        int fireworkColor = state.dyeColor.getFireworkColor();
        this.model.setupAnim(state);
        coloredCutoutModelCopyLayerRender(this.model, texture, poseStack, submitNodeCollector, lightCoords, state, ARGB.opaque(fireworkColor), 1);
    }

}