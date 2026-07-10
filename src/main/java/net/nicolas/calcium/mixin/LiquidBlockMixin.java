package net.nicolas.calcium.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.fluid.ModFluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin {

    @Shadow @Final protected FlowingFluid fluid;

    @Inject(method = "shouldSpreadLiquid", at = @At("HEAD"), cancellable = true)
    private void calcium$ectoplasmReactsToNeighbors(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {

        if (this.fluid != ModFluids.ECTOPLASM_STILL && this.fluid != ModFluids.ECTOPLASM_FLOWING) {
            return;
        }

        for (Direction direction : Direction.values()) {

            BlockPos neighborPos = pos.relative(direction);
            FluidState neighborFluid = level.getFluidState(neighborPos);

            if (neighborFluid.is(FluidTags.LAVA)) {
                level.setBlock(pos, ModBlocks.SOULSLATE.defaultBlockState(), 3);
                level.levelEvent(1501, pos, 0);
                cir.setReturnValue(false);
                cir.cancel();
                return;
            } else if (neighborFluid.is(FluidTags.WATER)) {
                level.setBlock(pos, Blocks.ICE.defaultBlockState(), 3);
                level.levelEvent(1501, pos, 0);
                cir.setReturnValue(false);
                cir.cancel();
                return;
            }

        }

    }

}
