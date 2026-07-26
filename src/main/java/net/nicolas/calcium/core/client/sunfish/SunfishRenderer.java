package net.nicolas.calcium.core.client.sunfish;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.nicolas.calcium.entity.custom.Sunfish;

public class SunfishRenderer extends MobRenderer<Sunfish, SunfishRenderState, SunfishModel> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath("calcium", "sunfish"), "main");
    public static final ModelLayerLocation BABY_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath("calcium", "sunfish_baby"), "main");
    public static final ModelLayerLocation NEWBORN_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath("calcium", "sunfish_newborn"), "main");

    private final SunfishModel adultModel;
    private final SunfishModel babyModel;
    private final SunfishModel newbornModel;

    public SunfishRenderer(EntityRendererProvider.Context context) {
        super(context, new SunfishModel(context.bakeLayer(LAYER)), 0.5F);
        this.adultModel = this.model;
        this.babyModel = new SunfishModel(context.bakeLayer(BABY_LAYER));
        this.newbornModel = new SunfishModel(context.bakeLayer(NEWBORN_LAYER));
    }

    @Override public Identifier getTextureLocation(SunfishRenderState state) {
        String age = switch (state.sunfishAge) {
            case -2 -> "newborn";
            case -1 -> "baby";
            default -> "adult";
        };
        String relaxed = state.sunfishAge == 0 && state.baskingProgress > 0.9F ? "_relaxed" : "";
        return Identifier.fromNamespaceAndPath("calcium", "textures/entity/sunfish/" + age + "/sunfish_" + state.variant.variantName() + relaxed + ".png");
    }

    @Override public SunfishRenderState createRenderState() {
        return new SunfishRenderState();
    }

    @Override public void extractRenderState(Sunfish entity, SunfishRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.variant = entity.getVariant();
        state.sunfishAge = entity.getSunfishAge();
        state.baskingProgress = Mth.lerp(partialTicks, entity.prevBaskingProgress, entity.baskingProgress);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.landAnimationState.copyFrom(entity.landAnimationState);
    }

    @Override public void submit(SunfishRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        this.model = switch (state.sunfishAge) {
            case -2 -> this.newbornModel;
            case -1 -> this.babyModel;
            default -> this.adultModel;
        };
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

}
