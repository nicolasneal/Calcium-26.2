package net.nicolas.calcium.mixin.hitboxes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlock.class)
public abstract class StandingSignBlockMixin {

    @Unique private static final VoxelShape ROTATED_SHAPE = Block.column(8.0, 0.0, 17.0);

    @Unique private static final VoxelShape POST_SHAPE = Block.column(2.0, 0.0, 9.0);
    @Unique private static final VoxelShape PERPENDICULAR_SHAPE_X = Shapes.or(POST_SHAPE, Block.box(0.0, 9.0, 7.0, 16.0, 17.0, 9.0));
    @Unique private static final VoxelShape PERPENDICULAR_SHAPE_Z = Shapes.or(POST_SHAPE, Block.box(7.0, 9.0, 0.0, 9.0, 17.0, 16.0));

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void calcium$shapeStandingSign(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if ((Object) this instanceof StandingSignBlock) {
            int rotation = state.getValue(StandingSignBlock.ROTATION);
            if (rotation % 8 == 0) {
                cir.setReturnValue(PERPENDICULAR_SHAPE_X);
            } else if (rotation % 8 == 4) {
                cir.setReturnValue(PERPENDICULAR_SHAPE_Z);
            } else {
                cir.setReturnValue(ROTATED_SHAPE);
            }
        }
    }

}