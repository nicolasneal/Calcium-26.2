package net.nicolas.calcium.core.client.sunfish;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class SunfishModel extends EntityModel<SunfishRenderState> {

    private final ModelPart all;
    private final ModelPart topFin;
    private final ModelPart bottomFin;
    private final ModelPart tailFin;
    private final ModelPart leftFin;
    private final ModelPart rightFin;
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation swimAnimation;
    private final KeyframeAnimation landAnimation;

    public SunfishModel(ModelPart root) {
        super(root);
        this.all = root.getChild("all");
        this.topFin = this.all.getChild("topFin");
        this.bottomFin = this.all.getChild("bottomFin");
        this.tailFin = this.all.getChild("tailFin");
        this.leftFin = this.all.getChild("leftFin");
        this.rightFin = this.all.getChild("rightFin");
        this.idleAnimation = SunfishAnimations.IDLE.bake(root);
        this.swimAnimation = SunfishAnimations.SWIM.bake(root);
        this.landAnimation = SunfishAnimations.LAND.bake(root);
    }

    @Override public void setupAnim(SunfishRenderState state) {
        super.setupAnim(state);
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float pi = (float) Math.PI;

        if (state.sunfishAge == 0) {
            this.all.y = 11.0F;
            this.all.yRot = 0.0F;
            this.all.zRot = 0.0F;
            this.tailFin.yRot = 0.0F;
            this.leftFin.yRot = 0.8F;
            this.rightFin.yRot = -0.8F;
            this.topFin.zRot = 0.0F;
            this.bottomFin.zRot = 0.0F;
            if (state.isInWater) {
                if (state.baskingProgress <= 0.0F) {
                    this.swimAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 2.5F, 8.0F);
                }
                this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
                this.all.zRot += state.baskingProgress * (pi / 180.0F) * 90.0F;
                this.all.y += state.baskingProgress * 8.0F;
            } else {
                this.landAnimation.apply(state.landAnimationState, state.ageInTicks);
            }
        } else {
            if (!state.isInWater) {
                this.all.y = state.sunfishAge == -2 ? 22.5F : 22.0F;
                this.all.zRot = pi / 2.0F;
                this.all.xRot = 0.0F;
                this.all.yRot = 0.0F;
            } else {
                this.all.zRot = 0.0F;
                this.all.xRot = state.xRot * (pi / 180.0F);
                this.all.yRot = state.yRot * (pi / 180.0F);
            }
            float phase = state.ageInTicks * 0.4F;
            float cosPhase = Mth.cos(phase);
            float cosPhaseOpposite = Mth.cos(phase + pi);
            this.all.y = this.all.y + Mth.sin(state.ageInTicks * 0.2F) * 1.5F * 0.5F;
            this.all.yRot = this.all.yRot + Mth.cos(state.ageInTicks * 0.2F) * 0.4F * 0.25F;
            this.tailFin.yRot = cosPhase * 0.8F * 0.25F;
            this.leftFin.yRot = cosPhase * 0.8F * 0.25F + 0.8F;
            this.rightFin.yRot = cosPhaseOpposite * 0.8F * 0.25F - 0.8F;
            this.topFin.zRot = cosPhase * 1.6F * 0.25F;
            this.bottomFin.zRot = cosPhaseOpposite * 1.6F * 0.25F;
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition all = root.addOrReplaceChild(
            "all",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-3.0F, -12.0F, -23.0F, 6.0F, 23.0F, 26.0F)
                .texOffs(28, 55)
                .addBox(-3.0F, 3.0F, -23.0F, 6.0F, 8.0F, 8.0F)
                .texOffs(0, 49)
                .addBox(-3.5F, -5.0F, -24.0F, 7.0F, 4.0F, 2.0F),
            PartPose.offset(0.0F, 13.0F, 11.0F)
        );
        all.addOrReplaceChild(
            "chunk",
            CubeListBuilder.create().texOffs(0, 55).addBox(-3.0F, -4.0F, -4.0F, 6.0F, 8.0F, 8.0F),
            PartPose.offset(0.0F, 7.0F, -19.0F)
        );
        all.addOrReplaceChild(
            "topFin",
            CubeListBuilder.create().texOffs(0, 1).addBox(-1.0F, -15.0F, -5.0F, 2.0F, 15.0F, 10.0F),
            PartPose.offset(0.0F, -12.0F, -3.25F)
        );
        all.addOrReplaceChild(
            "bottomFin",
            CubeListBuilder.create().texOffs(38, 1).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 15.0F, 10.0F),
            PartPose.offset(0.0F, 11.0F, -3.25F)
        );
        all.addOrReplaceChild(
            "tailFin",
            CubeListBuilder.create().texOffs(64, -6).addBox(0.0F, -9.0F, 0.0F, 0.0F, 19.0F, 6.0F),
            PartPose.offset(0.0F, -1.0F, 3.0F)
        );
        all.addOrReplaceChild(
            "leftFin",
            CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -3.0F, 0.0F, 0.0F, 4.0F, 4.0F),
            PartPose.offsetAndRotation(3.0F, -3.0F, -11.5F, 0.0F, 0.7854F, 0.0F)
        );
        all.addOrReplaceChild(
            "rightFin",
            CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -3.0F, 0.0F, 0.0F, 4.0F, 4.0F).mirror(false),
            PartPose.offsetAndRotation(-3.0F, -3.0F, -11.5F, 0.0F, -0.7854F, 0.0F)
        );
        return LayerDefinition.create(mesh, 80, 80);
    }

    public static LayerDefinition createBabyBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition all = root.addOrReplaceChild(
            "all",
            CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -5.0F, -9.5F, 4.0F, 10.0F, 12.0F),
            PartPose.offset(0.0F, 19.0F, 4.0F)
        );
        all.addOrReplaceChild(
            "topFin",
            CubeListBuilder.create().texOffs(21, 0).addBox(-0.5F, -7.0F, -1.5F, 1.0F, 7.0F, 3.0F),
            PartPose.offset(0.0F, -5.0F, 0.0F)
        );
        all.addOrReplaceChild(
            "bottomFin",
            CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 7.0F, 3.0F),
            PartPose.offset(0.0F, 5.0F, 0.0F)
        );
        all.addOrReplaceChild(
            "tailFin",
            CubeListBuilder.create().texOffs(8, 0).addBox(0.0F, -5.0F, 0.0F, 0.0F, 10.0F, 2.0F),
            PartPose.offset(0.0F, 0.0F, 2.5F)
        );
        all.addOrReplaceChild(
            "leftFin",
            CubeListBuilder.create().texOffs(8, 20).addBox(0.0F, -3.0F, 0.0F, 0.0F, 4.0F, 3.0F),
            PartPose.offsetAndRotation(2.0F, 1.0F, -4.5F, 0.0F, 0.3927F, 0.0F)
        );
        all.addOrReplaceChild(
            "rightFin",
            CubeListBuilder.create().texOffs(8, 20).mirror().addBox(0.0F, -3.0F, 0.0F, 0.0F, 4.0F, 3.0F).mirror(false),
            PartPose.offsetAndRotation(-2.0F, 1.0F, -4.5F, 0.0F, -0.3927F, 0.0F)
        );
        all.addOrReplaceChild("chunk", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 48, 32);
    }

    public static LayerDefinition createNewbornBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition all = root.addOrReplaceChild(
            "all",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 3.0F)
                .texOffs(0, 2)
                .addBox(0.0F, -2.5F, -2.0F, 0.0F, 5.0F, 4.0F),
            PartPose.offset(0.0F, 22.5F, 0.5F)
        );
        all.addOrReplaceChild(
            "leftFin",
            CubeListBuilder.create().texOffs(0, -1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 1.0F),
            PartPose.offsetAndRotation(1.5F, 0.5F, -0.5F, 0.0F, 0.3927F, 0.0F)
        );
        all.addOrReplaceChild(
            "rightFin",
            CubeListBuilder.create().texOffs(0, -1).mirror().addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 1.0F).mirror(false),
            PartPose.offsetAndRotation(-1.5F, 0.5F, -0.5F, 0.0F, -0.3927F, 0.0F)
        );
        all.addOrReplaceChild("bottomFin", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, -0.5F));
        all.addOrReplaceChild("topFin", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, -0.5F));
        all.addOrReplaceChild("tailFin", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, -0.5F));
        all.addOrReplaceChild("chunk", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 16, 16);
    }

}
