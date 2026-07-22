package net.nicolas.calcium.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.block.entity.ViewfinderBlockEntity;
import net.nicolas.calcium.core.client.viewfinder.ViewfinderController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow private Level level;
    @Shadow private Vec3 position;
    @Shadow private BlockPos.MutableBlockPos blockPosition;

    @Shadow protected abstract void setPosition(Vec3 position);

    @Shadow protected abstract void setRotation(float yRot, float xRot);

    @ModifyExpressionValue(method = "modifyFovBasedOnDeathOrFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;getFluidInCamera()Lnet/minecraft/world/level/material/FogType;"))
    private FogType calcium$ectoplasmFov(FogType original) {

        if (original != FogType.NONE) return original;

        FluidState fluidState = this.level.getFluidState(this.blockPosition);
        if ((fluidState.getType() == ModBlocks.ECTOPLASM_STILL || fluidState.getType() == ModBlocks.ECTOPLASM_FLOWING)
            && this.position.y < this.blockPosition.getY() + fluidState.getHeight(this.level, this.blockPosition)) {
            return FogType.WATER;
        }

        return original;

    }

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;alignWithEntity(F)V", shift = At.Shift.AFTER))
    private void calcium$overrideForViewfinder(CallbackInfo ci) {

        if (!ViewfinderController.isActive()) return;
        ViewfinderBlockEntity viewfinder = ViewfinderController.getActiveViewfinder();
        if (viewfinder == null) {
            ViewfinderController.stopLooking();
            return;
        }

        float yaw = ViewfinderController.getPanYaw();
        float pitch = ViewfinderController.getPanPitch();
        Vec3 forward = Vec3.directionFromRotation(pitch, yaw);
        BlockPos pos = viewfinder.getBlockPos();
        boolean ceilingMounted = viewfinder.getBlockState().getValue(DirectionalBlock.FACING) == Direction.DOWN;
        double eyeHeight = ceilingMounted ? 6.0 / 16.0 : 10.0 / 16.0;
        Vec3 basePos = new Vec3(pos.getX() + 0.5, pos.getY() + eyeHeight, pos.getZ() + 0.5);
        Vec3 eyePos = basePos.add(forward.scale(0.4));

        this.setPosition(eyePos);
        this.setRotation(yaw, pitch);

    }

    @Inject(method = "calculateFov", at = @At("RETURN"), cancellable = true)
    private void calcium$applyViewfinderZoom(float partialTicks, CallbackInfoReturnable<Float> cir) {
        if (ViewfinderController.isActive()) {
            cir.setReturnValue(cir.getReturnValue() / ViewfinderController.getRenderZoom(partialTicks));
        }
    }

}