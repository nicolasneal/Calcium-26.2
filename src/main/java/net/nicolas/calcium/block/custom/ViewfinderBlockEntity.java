package net.nicolas.calcium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.nicolas.calcium.block.ModBlocks;

public class ViewfinderBlockEntity extends BlockEntity {

    private static final float RENDER_SMOOTHING = 0.4F;

    private float aimYaw;
    private float aimPitch;
    private float zoom = 1.0F;

    private float prevRenderYaw;
    private float renderYaw;
    private float prevRenderPitch;
    private float renderPitch;
    private boolean hasTicked;

    public ViewfinderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.VIEWFINDER_BLOCK_ENTITY, pos, state);
        Direction facing = state.getValue(DirectionalBlock.FACING);
        this.aimYaw = facing.getAxis() == Direction.Axis.Y ? 0.0F : facing.toYRot();
        this.aimPitch = 0.0F;
        this.snapRenderState();
    }

    public float getViewYaw() {
        return this.aimYaw;
    }

    public float getViewPitch() {
        return this.aimPitch;
    }

    public float getZoom() {
        return this.zoom;
    }

    public float getRenderYaw(float partialTicks) {
        return Mth.rotLerp(partialTicks, this.prevRenderYaw, this.renderYaw);
    }

    public float getRenderPitch(float partialTicks) {
        return Mth.lerp(partialTicks, this.prevRenderPitch, this.renderPitch);
    }

    public void setAim(float yaw, float pitch, float zoom) {
        this.aimYaw = yaw;
        this.aimPitch = pitch;
        this.zoom = zoom;
        this.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    private void snapRenderState() {
        this.renderYaw = this.aimYaw;
        this.prevRenderYaw = this.aimYaw;
        this.renderPitch = this.aimPitch;
        this.prevRenderPitch = this.aimPitch;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ViewfinderBlockEntity viewfinder) {
        viewfinder.hasTicked = true;
        if (level.isClientSide()) {
            viewfinder.prevRenderYaw = viewfinder.renderYaw;
            viewfinder.prevRenderPitch = viewfinder.renderPitch;
            viewfinder.renderYaw = Mth.rotLerp(RENDER_SMOOTHING, viewfinder.renderYaw, viewfinder.aimYaw);
            viewfinder.renderPitch = Mth.lerp(RENDER_SMOOTHING, viewfinder.renderPitch, viewfinder.aimPitch);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putFloat("aim_yaw", this.aimYaw);
        output.putFloat("aim_pitch", this.aimPitch);
        output.putFloat("zoom", this.zoom);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.aimYaw = input.getFloatOr("aim_yaw", 0.0F);
        this.aimPitch = input.getFloatOr("aim_pitch", 0.0F);
        this.zoom = input.getFloatOr("zoom", 1.0F);
        if (!this.hasTicked) {
            this.snapRenderState();
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

}