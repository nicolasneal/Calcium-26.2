package net.nicolas.calcium.mixin.hitboxes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin {

    @Unique private static final VoxelShape FACE_X = Block.box(0.0, 9.0, 2.0, 16.0, 16.0, 14.0);
    @Unique private static final VoxelShape WAIST_X = Block.box(4.0, 5.0, 5.0, 12.0, 9.0, 11.0);
    @Unique private static final VoxelShape LIP_X = Block.box(3.0, 4.0, 4.0, 13.0, 5.0, 12.0);
    @Unique private static final VoxelShape BASE_X = Block.box(1.0, 0.0, 2.0, 15.0, 4.0, 14.0);
    @Unique private static final VoxelShape SHAPE_X = Shapes.or(FACE_X, WAIST_X, LIP_X, BASE_X);

    @Unique private static final VoxelShape FACE_Z = Block.box(2.0, 9.0, 0.0, 14.0, 16.0, 16.0);
    @Unique private static final VoxelShape WAIST_Z = Block.box(5.0, 5.0, 4.0, 11.0, 9.0, 12.0);
    @Unique private static final VoxelShape LIP_Z = Block.box(4.0, 4.0, 3.0, 12.0, 5.0, 13.0);
    @Unique private static final VoxelShape BASE_Z = Block.box(2.0, 0.0, 1.0, 14.0, 4.0, 15.0);
    @Unique private static final VoxelShape SHAPE_Z = Shapes.or(FACE_Z, WAIST_Z, LIP_Z, BASE_Z);

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void injectCustomAnvilShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {

        Direction.Axis axis = state.getValue(AnvilBlock.FACING).getAxis();

        if (axis == Direction.Axis.X) {
            cir.setReturnValue(SHAPE_X);
        } else {
            cir.setReturnValue(SHAPE_Z);
        }

    }

}