package net.nicolas.calcium.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.material.FluidState;
import net.nicolas.calcium.fluid.ModFluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;isInWater()Z"))
    private boolean calcium$treatEctoplasmAsWater(boolean original) {

        if (original) return true;

        ItemEntity self = (ItemEntity) (Object) this;
        FluidState fluidState = self.level().getFluidState(self.blockPosition());
        return fluidState.getType() == ModFluids.ECTOPLASM_STILL || fluidState.getType() == ModFluids.ECTOPLASM_FLOWING;

    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;getFluidHeight(Lnet/minecraft/tags/TagKey;)D", ordinal = 0))
    private double calcium$ectoplasmFluidHeight(double original) {

        if (original > 0.0) return original;

        ItemEntity self = (ItemEntity) (Object) this;
        BlockPos pos = self.blockPosition();
        FluidState fluidState = self.level().getFluidState(pos);

        if (fluidState.getType() == ModFluids.ECTOPLASM_STILL || fluidState.getType() == ModFluids.ECTOPLASM_FLOWING) {
            double fluidTop = pos.getY() + fluidState.getHeight(self.level(), pos);
            double entityBottom = self.getBoundingBox().minY;
            return fluidTop - entityBottom;
        }

        return original;

    }

}
