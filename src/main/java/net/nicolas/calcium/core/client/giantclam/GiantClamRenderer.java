package net.nicolas.calcium.core.client.giantclam;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.nicolas.calcium.entity.custom.GiantClam;

public class GiantClamRenderer extends MobRenderer<GiantClam, GiantClamRenderState, GiantClamModel> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath("calcium", "giant_clam"), "main");

    public GiantClamRenderer(EntityRendererProvider.Context context) {
        super(context, new GiantClamModel(context.bakeLayer(LAYER)), 1.0F);
        this.addLayer(new GiantClamPatternLayer(this, context.getModelSet()));
    }

    @Override public Identifier getTextureLocation(GiantClamRenderState state) {
        return Identifier.fromNamespaceAndPath("calcium", "textures/entity/clam/giant_clam_" + state.baseColor.colorName() + ".png");
    }

    @Override public GiantClamRenderState createRenderState() {
        return new GiantClamRenderState();
    }

    @Override public void extractRenderState(GiantClam entity, GiantClamRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.baseColor = entity.getBaseColor();
        state.pattern = entity.getPattern();
        state.dyeColor = entity.getDyeColor();
        state.anchored = entity.isAnchored();
        state.anchoringAnimationState.copyFrom(entity.anchoringAnimationState);
        state.openAnimationState.copyFrom(entity.openAnimationState);
    }

}