package net.nicolas.calcium.mixin.hitboxes.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TallGrassBlock.class)
public abstract class FernMixin {

    @Unique private static final VoxelShape FERN_SHAPE = Block.column(14.0, 0.0, 16.0);

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void calcium$fernShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if ((Object) this == Blocks.FERN) {
            cir.setReturnValue(FERN_SHAPE.move(state.getOffset(pos)));
        }
    }

}