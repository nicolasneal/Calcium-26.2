package net.nicolas.calcium.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.nicolas.calcium.fluid.ModFluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow private Level level;
    @Shadow private Vec3 position;
    @Shadow private BlockPos.MutableBlockPos blockPosition;

    @ModifyExpressionValue(method = "modifyFovBasedOnDeathOrFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;getFluidInCamera()Lnet/minecraft/world/level/material/FogType;"))
    private FogType calcium$ectoplasmFov(FogType original) {

        if (original != FogType.NONE) return original;

        FluidState fluidState = this.level.getFluidState(this.blockPosition);
        if ((fluidState.getType() == ModFluids.ECTOPLASM_STILL || fluidState.getType() == ModFluids.ECTOPLASM_FLOWING)
            && this.position.y < this.blockPosition.getY() + fluidState.getHeight(this.level, this.blockPosition)) {
            return FogType.WATER;
        }

        return original;

    }

}
