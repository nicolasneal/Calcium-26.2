package net.nicolas.calcium.core.client.seacow;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.nicolas.calcium.entity.custom.SeaCow;

public class SeaCowRenderer extends MobRenderer<SeaCow, SeaCowRenderState, SeaCowModel> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath("calcium", "sea_cow"), "main");
    public static final ModelLayerLocation BABY_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath("calcium", "sea_cow_baby"), "main");

    private final SeaCowModel adultModel;
    private final SeaCowModel babyModel;

    public SeaCowRenderer(EntityRendererProvider.Context context) {
        super(context, new SeaCowModel(context.bakeLayer(LAYER)), 1.0F);
        this.adultModel = this.model;
        this.babyModel = new SeaCowModel(context.bakeLayer(BABY_LAYER));
    }

    @Override public Identifier getTextureLocation(SeaCowRenderState state) {
        String baby = state.isBaby ? "_baby" : "";
        return Identifier.fromNamespaceAndPath("calcium", "textures/entity/sea_cow/sea_cow_" + state.variant.variantName() + baby + ".png");
    }

    @Override public SeaCowRenderState createRenderState() {
        return new SeaCowRenderState();
    }

    @Override public void extractRenderState(SeaCow entity, SeaCowRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.variant = entity.getVariant();
    }

    @Override public void submit(SeaCowRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        this.model = state.isBaby ? this.babyModel : this.adultModel;
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

}
