package net.nicolas.calcium.mixin.hitboxes.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TorchflowerCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TorchflowerCropBlock.class)
public abstract class TorchflowerCropBlockMixin {

    @Unique private static final VoxelShape STAGE_0_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 6.0, 12.0);
    @Unique private static final VoxelShape STAGE_1_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 10.0, 14.0);

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void calcium$torchflowerCropShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        VoxelShape shape = state.getValue(TorchflowerCropBlock.AGE) == 0 ? STAGE_0_SHAPE : STAGE_1_SHAPE;
        cir.setReturnValue(shape);
    }

}