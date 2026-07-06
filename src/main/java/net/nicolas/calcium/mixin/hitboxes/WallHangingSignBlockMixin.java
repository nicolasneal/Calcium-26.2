package net.nicolas.calcium.mixin.hitboxes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WallHangingSignBlock.class)
public class WallHangingSignBlockMixin {

    @Unique private static final VoxelShape BOARD_X = Block.box(7.0, 0.0, 1.0, 9.0, 10.0, 15.0);
    @Unique private static final VoxelShape BAR_X = Block.box(7.0, 14.0, 0.0, 9.0, 16.0, 16.0);
    @Unique private static final VoxelShape SHAPE_X = Shapes.or(BOARD_X, BAR_X);

    @Unique private static final VoxelShape BOARD_Z = Block.box(1.0, 0.0, 7.0, 15.0, 10.0, 9.0);
    @Unique private static final VoxelShape BAR_Z = Block.box(0.0, 14.0, 7.0, 16.0, 16.0, 9.0);
    @Unique private static final VoxelShape SHAPE_Z = Shapes.or(BOARD_Z, BAR_Z);

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void injectCustomShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {

        Direction.Axis axis = state.getValue(WallHangingSignBlock.FACING).getAxis();
        if (axis == Direction.Axis.X) {
            cir.setReturnValue(SHAPE_X);
        } else {
            cir.setReturnValue(SHAPE_Z);
        }

    }

}