package net.nicolas.calcium.mixin.gameplay.logging;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.nicolas.calcium.core.util.LoggingHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CrossCollisionBlock.class)
public abstract class CrossCollisionBlockMixin extends Block {

    protected CrossCollisionBlockMixin(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @ModifyReturnValue(method = "getFluidState", at = @At("RETURN"))
    private FluidState calcium$loggedFluidState(FluidState original, BlockState state) {
        return (Object) this == Blocks.IRON_BARS ? LoggingHelper.fluidState(state, original, false) : original;
    }

}