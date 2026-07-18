package net.nicolas.calcium.client;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class ViewfinderModel extends Model<ViewfinderModel.Aim> {

    private final ModelPart legs;
    private final ModelPart pivot;

    public ViewfinderModel(ModelPart root) {
        super(root, RenderTypes::entityCutout);
        this.legs = root.getChild("legs");
        this.pivot = this.legs.getChild("head_pivot");
    }

    @Override public void setupAnim(Aim state) {
        this.resetPose();
        this.legs.yRot = state.legYaw();
        this.pivot.xRot = state.pivotXRot();
    }

    public record Aim(float legYaw, float pivotXRot) {
        public static final Aim ZERO = new Aim(0.0F, 0.0F);
    }

    public static LayerDefinition createLayer() {

        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition legs = root.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.ZERO);
        legs.addOrReplaceChild("legs_visual", CubeListBuilder.create().texOffs(0, 36).addBox(5.0F, -4.0F, -2.0F, 2.0F, 10.0F, 4.0F).texOffs(12, 36).addBox(-7.0F, -4.0F, -2.0F, 2.0F, 10.0F, 4.0F), PartPose.ZERO);
        PartDefinition pivot = legs.addOrReplaceChild("head_pivot", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));
        pivot.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, -6.0F, -4.0F, 10.0F, 12.0F, 8.0F), PartPose.ZERO);
        root.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, 6.0F, -7.0F, 14.0F, 2.0F, 14.0F), PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);

    }

}