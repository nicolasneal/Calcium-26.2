package net.nicolas.calcium.mixin.gameplay.lavalogging;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.nicolas.calcium.core.util.LavaloggingHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossCollisionBlock.class)
public abstract class CrossCollisionBlockMixin extends Block {

    protected CrossCollisionBlockMixin(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Inject(method = "getFluidState", at = @At("RETURN"), cancellable = true)
    private void calcium$lavaFluidState(BlockState state, CallbackInfoReturnable<FluidState> cir) {
        if ((Object) this == Blocks.IRON_BARS && state.getValue(LavaloggingHelper.LAVALOGGED)) {
            cir.setReturnValue(Fluids.LAVA.getSource(false));
        }
    }

}