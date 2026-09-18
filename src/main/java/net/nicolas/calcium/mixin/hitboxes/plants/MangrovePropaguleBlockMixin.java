package net.nicolas.calcium.mixin.hitboxes.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MangrovePropaguleBlock.class)
public abstract class MangrovePropaguleBlockMixin {

    @Unique private static final VoxelShape STANDING_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 15.0, 12.0);
    @Unique private static final VoxelShape AGE_0_SHAPE = Block.box(6.0, 12.0, 6.0, 10.0, 16.0, 10.0);
    @Unique private static final VoxelShape AGE_1_SHAPE = Block.box(6.0, 9.0, 6.0, 10.0, 16.0, 10.0);
    @Unique private static final VoxelShape AGE_2_SHAPE = Block.box(6.0, 6.0, 6.0, 10.0, 16.0, 10.0);
    @Unique private static final VoxelShape AGE_3_SHAPE = Block.box(6.0, 2.0, 6.0, 10.0, 16.0, 10.0);
    @Unique private static final VoxelShape AGE_4_SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    @Unique private static final VoxelShape[] AGE_SHAPES = {AGE_0_SHAPE, AGE_1_SHAPE, AGE_2_SHAPE, AGE_3_SHAPE, AGE_4_SHAPE};

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void calcium$propaguleShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        VoxelShape shape = state.getValue(MangrovePropaguleBlock.HANGING) ? AGE_SHAPES[state.getValue(MangrovePropaguleBlock.AGE)] : STANDING_SHAPE;
        cir.setReturnValue(shape.move(state.getOffset(pos)));
    }

}