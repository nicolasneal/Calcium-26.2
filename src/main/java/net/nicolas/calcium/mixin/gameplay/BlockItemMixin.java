package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.nicolas.calcium.core.util.FluidPlacementHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {

    @Inject(method = "canPlace", at = @At("RETURN"), cancellable = true)
    private void calcium$rejectPlacementInFluid(BlockPlaceContext context, BlockState stateForPlacement, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && FluidPlacementHelper.isDestroyedByFluid(stateForPlacement, context.getLevel(), context.getClickedPos())) {
            cir.setReturnValue(false);
        }
    }

}
