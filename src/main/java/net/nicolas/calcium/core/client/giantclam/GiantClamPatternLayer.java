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
        float r = (fireworkColor >> 16 & 0xFF) / 255.0F;
        float g = (fireworkColor >> 8 & 0xFF) / 255.0F;
        float b = (fireworkColor & 0xFF) / 255.0F;
        float gray = 0.3F * r + 0.59F * g + 0.11F * b;
        float saturation = 1.0F;
        r = gray + (r - gray) * saturation;
        g = gray + (g - gray) * saturation;
        b = gray + (b - gray) * saturation;

        this.model.setupAnim(state);
        coloredCutoutModelCopyLayerRender(this.model, texture, poseStack, submitNodeCollector, lightCoords, state, ARGB.colorFromFloat(1.0F, r, g, b), 1);
    }

}
