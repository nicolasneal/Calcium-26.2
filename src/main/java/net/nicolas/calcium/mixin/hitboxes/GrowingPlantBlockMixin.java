package net.nicolas.calcium.mixin.hitboxes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBlock;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.KelpPlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GrowingPlantBlock.class)
public abstract class GrowingPlantBlockMixin {

    @Unique private static final VoxelShape KELP_HEAD_SHAPE = Block.column(14.0, 0.0, 9.0);
    @Unique private static final VoxelShape KELP_BODY_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    @Inject(method = "getShape", at = @At("RETURN"), cancellable = true)
    private void calcium$offsetShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        VoxelShape shape = cir.getReturnValue();
        if ((Object) this instanceof KelpBlock) {
            shape = KELP_HEAD_SHAPE;
        } else if ((Object) this instanceof KelpPlantBlock) {
            shape = KELP_BODY_SHAPE;
        }
        cir.setReturnValue(shape.move(state.getOffset(pos)));
    }

}
