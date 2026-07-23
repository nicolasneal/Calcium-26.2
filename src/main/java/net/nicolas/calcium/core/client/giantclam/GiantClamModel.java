package net.nicolas.calcium.core.client.giantclam;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class GiantClamModel extends EntityModel<GiantClamRenderState> {

    private final KeyframeAnimation anchorAnimation;
    private final KeyframeAnimation anchoredAnimation;
    private final KeyframeAnimation openAnimation;

    public GiantClamModel(ModelPart root) {
        super(root, RenderTypes::entityTranslucent);
        this.anchorAnimation = GiantClamAnimations.ANCHOR.bake(root);
        this.anchoredAnimation = GiantClamAnimations.ANCHORED.bake(root);
        this.openAnimation = GiantClamAnimations.OPEN.bake(root);
    }

    @Override public void setupAnim(GiantClamRenderState state) {
        super.setupAnim(state);
        this.anchorAnimation.apply(state.anchoringAnimationState, state.ageInTicks);
        this.openAnimation.apply(state.openAnimationState, state.ageInTicks);
        if (state.anchored && !state.anchoringAnimationState.isStarted()) {
            this.anchoredAnimation.applyStatic();
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition all = root.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition clam = all.addOrReplaceChild("clam", CubeListBuilder.create(), PartPose.offset(0.0F, -6.5F, 0.0F));
        clam.addOrReplaceChild(
            "bottom",
            CubeListBuilder.create()
                .texOffs(0, 22)
                .addBox(-10.0F, 0.0F, -15.0F, 20.0F, 7.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(0, 65)
                .addBox(-10.0F, -2.0F, -15.0F, 20.0F, 7.0F, 15.0F, new CubeDeformation(0.25F)),
            PartPose.offset(0.0F, -0.5F, 7.5F)
        );
        clam.addOrReplaceChild(
            "top",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-10.0F, -7.0F, -15.0F, 20.0F, 7.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44)
                .addBox(-10.0F, -4.0F, -15.0F, 20.0F, 7.0F, 15.0F, new CubeDeformation(0.5F)),
            PartPose.offset(0.0F, -0.5F, 7.5F)
        );
        return LayerDefinition.create(mesh, 80, 97);
    }

}
