package net.nicolas.calcium.core.client.seacow;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class SeaCowModel extends EntityModel<SeaCowRenderState> {

    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart tail;
    private final ModelPart flukes;
    private final ModelPart head;

    public SeaCowModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.leftArm = this.body.getChild("leftArm");
        this.rightArm = this.body.getChild("rightArm");
        this.tail = this.body.getChild("tail");
        this.flukes = this.tail.getChild("flukes");
        this.head = this.body.getChild("head");
    }

    @Override public void setupAnim(SeaCowRenderState state) {
        super.setupAnim(state);
        this.root().getAllParts().forEach(ModelPart::resetPose);

        float pi = (float) Math.PI;
        float babyY = state.isBaby ? 21.0F : 17.0F;
        float speed = state.isBaby ? 2.0F : 1.0F;
        float limbAngle = state.walkAnimationPos;
        float limbDistance = state.walkAnimationSpeed;
        float animationProgress = state.ageInTicks;
        float headYaw = state.yRot;
        float headPitch = state.xRot;

        if (state.isInWater) {
            float degree = Math.min(limbDistance / 0.3F, 0.7F) + 0.3F;
            float tailDegree = Math.min(limbDistance / 0.3F, 0.8F) + 0.2F;
            float armDegree = Math.min(limbDistance / 0.3F, 0.6F);
            this.body.xRot = headPitch * (pi / 180.0F);
            this.body.yRot = headYaw * (pi / 180.0F);
            this.head.xRot = -(headPitch * (pi / 180.0F)) / 2.0F;
            this.head.yRot = -(headYaw * (pi / 180.0F)) / 2.0F;
            this.head.xRot = this.head.xRot + Mth.cos(animationProgress * 0.2F * speed + 3.0F) * 0.6F * degree * 0.25F;
            this.body.xRot = this.body.xRot + Mth.cos(animationProgress * 0.2F * speed + 2.0F) * 0.6F * degree * 0.25F;
            this.body.y = Mth.cos(animationProgress * 0.2F) * 1.5F * degree + babyY;
            this.rightArm.zRot = Mth.cos(animationProgress * 0.2F * speed + 3.5F) * 2.0F * 0.25F * degree - 0.8F;
            this.rightArm.yRot = Mth.sin(animationProgress * 0.2F * speed + 3.5F) * 3.0F * 0.25F * armDegree;
            this.leftArm.zRot = Mth.cos(animationProgress * 0.2F * speed + 3.5F + pi) * 2.0F * 0.25F * degree + 0.8F;
            this.leftArm.yRot = Mth.sin(animationProgress * 0.2F * speed + 3.5F + pi) * 3.0F * 0.25F * armDegree;
            this.tail.xRot = Mth.cos(animationProgress * 0.2F * speed + 1.0F) * tailDegree * 0.35F;
            this.flukes.xRot = Mth.cos(animationProgress * 0.2F * speed - 1.0F) * tailDegree * 0.35F;
        } else {
            limbDistance = Mth.clamp(limbDistance, -0.25F, 0.25F);
            float degree = 3.0F;
            this.body.xRot = 0.0F;
            this.body.yRot = 0.0F;
            this.head.xRot = headPitch * (pi / 180.0F) / 2.0F;
            this.head.yRot = headYaw * (pi / 180.0F) / 2.0F;
            this.body.y = Mth.cos(limbAngle + 1.0F) * 1.4F * 2.0F * limbDistance + babyY;
            this.rightArm.zRot = 0.0F;
            this.rightArm.yRot = Mth.cos(limbAngle) * 1.4F * degree * limbDistance + 0.4F;
            this.leftArm.zRot = 0.0F;
            this.leftArm.yRot = Mth.cos(limbAngle + pi) * 1.4F * degree * limbDistance - 0.4F;
            this.tail.xRot = Mth.cos(limbAngle) * 0.4F * degree * limbDistance - 0.4F;
            this.flukes.xRot = Mth.cos(limbAngle) * 0.4F * degree * limbDistance + 0.3F;
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition partdefinition = mesh.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild(
            "body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -9.0F, -13.0F, 21.0F, 16.0F, 25.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 17.0F, 1.0F)
        );
        body.addOrReplaceChild(
            "leftArm",
            CubeListBuilder.create().texOffs(0, 81).addBox(0.0F, -1.0F, -4.0F, 14.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
            PartPose.offset(10.5F, 6.0F, -7.0F)
        );
        body.addOrReplaceChild(
            "rightArm",
            CubeListBuilder.create().texOffs(0, 81).mirror().addBox(-14.0F, -1.0F, -4.0F, 14.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false),
            PartPose.offset(-10.5F, 6.0F, -7.0F)
        );
        PartDefinition tail = body.addOrReplaceChild(
            "tail",
            CubeListBuilder.create().texOffs(0, 60).addBox(-6.5F, -4.5F, 0.0F, 13.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -1.5F, 12.0F)
        );
        tail.addOrReplaceChild(
            "flukes",
            CubeListBuilder.create().texOffs(36, 67).addBox(-9.5F, -1.0F, -1.0F, 19.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 0.5F, 8.0F)
        );
        body.addOrReplaceChild(
            "head",
            CubeListBuilder.create()
                .texOffs(0, 41)
                .addBox(-7.5F, -6.0F, -6.0F, 15.0F, 11.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(46, 41)
                .addBox(-5.5F, -6.0F, -13.0F, 11.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -1.0F, -13.0F)
        );
        return LayerDefinition.create(mesh, 112, 96);
    }

    public static LayerDefinition createBabyBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition partdefinition = mesh.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild(
            "body",
            CubeListBuilder.create().texOffs(0, 0).addBox(-6.5F, -6.0F, -5.0F, 13.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 21.0F, 1.0F)
        );
        body.addOrReplaceChild(
            "leftArm",
            CubeListBuilder.create().texOffs(0, 40).addBox(0.0F, -0.5F, -2.5F, 7.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
            PartPose.offset(6.5F, 1.5F, -1.5F)
        );
        body.addOrReplaceChild(
            "rightArm",
            CubeListBuilder.create().texOffs(0, 40).mirror().addBox(-7.0F, -0.5F, -2.5F, 7.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false),
            PartPose.offset(-6.5F, 1.5F, -1.5F)
        );
        PartDefinition tail = body.addOrReplaceChild(
            "tail",
            CubeListBuilder.create().texOffs(0, 30).addBox(-2.5F, -2.5F, 0.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -1.5F, 5.0F)
        );
        tail.addOrReplaceChild(
            "flukes",
            CubeListBuilder.create().texOffs(18, 41).addBox(-4.5F, -0.5F, 0.0F, 9.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 0.0F, 3.0F)
        );
        body.addOrReplaceChild(
            "head",
            CubeListBuilder.create()
                .texOffs(0, 19)
                .addBox(-4.5F, -3.0F, -4.0F, 9.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(23, 20)
                .addBox(-3.5F, -7.0F, -1.5F, 7.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(24, 26)
                .addBox(-3.5F, -3.0F, -8.0F, 7.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -2.0F, -5.0F)
        );
        return LayerDefinition.create(mesh, 48, 48);
    }

}