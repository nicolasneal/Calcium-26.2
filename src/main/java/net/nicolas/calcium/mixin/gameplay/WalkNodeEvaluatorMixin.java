package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.nicolas.calcium.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WalkNodeEvaluator.class)
public abstract class WalkNodeEvaluatorMixin {

    @Inject(method = "getPathTypeFromState", at = @At("HEAD"), cancellable = true)
    private static void calcium$avoidEctoplasmLikeLava(BlockGetter level, BlockPos pos, CallbackInfoReturnable<PathType> cir) {
        BlockState blockState = level.getBlockState(pos);
        FluidState fluidState = blockState.getFluidState();
        if (fluidState.getType() == ModBlocks.ECTOPLASM_STILL || fluidState.getType() == ModBlocks.ECTOPLASM_FLOWING) {
            cir.setReturnValue(PathType.LAVA);
        }
    }

}
