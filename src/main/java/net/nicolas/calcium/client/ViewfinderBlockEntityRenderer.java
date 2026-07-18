package net.nicolas.calcium.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.nicolas.calcium.block.custom.ViewfinderBlockEntity;
import org.jspecify.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ViewfinderBlockEntityRenderer implements BlockEntityRenderer<ViewfinderBlockEntity, ViewfinderRenderState> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath("calcium", "viewfinder"), "main");
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("calcium", "textures/block/viewfinder.png");
    private final ViewfinderModel model;

    public ViewfinderBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new ViewfinderModel(context.bakeLayer(LAYER));
    }

    @Override public ViewfinderRenderState createRenderState() {
        return new ViewfinderRenderState();
    }

    @Override public void extractRenderState(ViewfinderBlockEntity blockEntity, ViewfinderRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.facing = blockEntity.getBlockState().getValue(DirectionalBlock.FACING);
        state.absoluteYaw = blockEntity.getRenderYaw(partialTicks);
        state.absolutePitch = blockEntity.getRenderPitch(partialTicks);
        state.hiddenFromLocalViewer = blockEntity == ViewfinderController.getActiveViewfinder();
    }

    @Override public void submit(ViewfinderRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {

        if (state.hiddenFromLocalViewer) {
            return;
        }

        Quaternionf base = new Quaternionf(state.facing.getRotation());
        base.mul(Axis.YP.rotationDegrees(180.0F));

        Vec3 worldFront = Vec3.directionFromRotation(state.absolutePitch, state.absoluteYaw);
        Vector3f local = new Vector3f((float) worldFront.x, (float) worldFront.y, (float) worldFront.z);
        base.conjugate(new Quaternionf()).transform(local);
        local.x = -local.x;
        local.y = -local.y;
        Vec2 pitchYaw = new Vec3(-local.x, local.y, local.z).rotation();
        ViewfinderModel.Aim aim = new ViewfinderModel.Aim(pitchYaw.y * Mth.DEG_TO_RAD, pitchYaw.x * Mth.DEG_TO_RAD);

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(base);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        submitNodeCollector.submitModel(this.model, aim, poseStack, TEXTURE, state.lightCoords, OverlayTexture.NO_OVERLAY, 0, state.breakProgress);
        poseStack.popPose();

    }

}