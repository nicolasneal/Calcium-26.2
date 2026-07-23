package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.nicolas.calcium.core.util.FluidPlacementHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StandingAndWallBlockItem.class)
public abstract class StandingAndWallBlockItemMixin {

    @Inject(method = "canPlace", at = @At("RETURN"), cancellable = true)
    private void calcium$rejectPlacementInFluid(LevelReader level, BlockState possibleState, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && FluidPlacementHelper.isDestroyedByFluid(possibleState, level, pos)) {
            cir.setReturnValue(false);
        }
    }

}
