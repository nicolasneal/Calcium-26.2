package net.nicolas.calcium.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.LavaFluid;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.fluid.ModFluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FlowingFluid.class)
public abstract class FlowableFluidMixin {

    @Inject(method = "spreadTo", at = @At("HEAD"), cancellable = true)
    private void calcium$interactWithEctoplasm(LevelAccessor world, BlockPos pos, BlockState state, Direction direction, FluidState newFluidState, CallbackInfo ci) {

        if (!((Object) this instanceof LavaFluid)) {
            return;
        }

        FluidState currentFluidState = state.getFluidState();

        if (currentFluidState.getType() == ModFluids.ECTOPLASM_STILL || currentFluidState.getType() == ModFluids.ECTOPLASM_FLOWING) {

            if (currentFluidState.isSource()) {
                world.setBlock(pos, Blocks.OBSIDIAN.defaultBlockState(), 3);
            } else {
                world.setBlock(pos, ModBlocks.SOULSLATE.defaultBlockState(), 3);
            }
            this.playExtinguishEvent(world, pos);

            ci.cancel();

        }

    }

    @Unique private void playExtinguishEvent(LevelAccessor world, BlockPos pos) {
        world.levelEvent(1501, pos, 0);
    }

}