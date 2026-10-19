package net.nicolas.calcium.mixin.hitboxes.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.PitcherCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PitcherCropBlock.class)
public abstract class PitcherCropBlockMixin {

    @Unique private static final VoxelShape STAGE_0_LOWER = Block.column(6.0, -1.0, 3.0);
    @Unique private static final VoxelShape STAGE_0_UPPER = Block.column(6.0, 0.0, 0.0);
    @Unique private static final VoxelShape STAGE_1_LOWER = Block.column(10.0, -1.0, 16.0);
    @Unique private static final VoxelShape STAGE_1_UPPER = Block.column(10.0, 0.0, 0.0);
    @Unique private static final VoxelShape STAGE_2_LOWER = Block.column(10.0, -1.0, 16.0);
    @Unique private static final VoxelShape STAGE_2_UPPER = Block.column(10.0, 0.0, 4.0);
    @Unique private static final VoxelShape STAGE_3_LOWER = Block.column(10.0, -1.0, 16.0);
    @Unique private static final VoxelShape STAGE_3_UPPER = Block.column(10.0, 0.0, 12.0);
    @Unique private static final VoxelShape STAGE_4_LOWER = Block.column(10.0, -1.0, 16.0);
    @Unique private static final VoxelShape STAGE_4_UPPER = Block.column(10.0, 0.0, 16.0);

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void calcium$pitcherCropShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        boolean upper = state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER;
        VoxelShape shape = switch (state.getValue(PitcherCropBlock.AGE)) {
            case 0 -> upper ? STAGE_0_UPPER : STAGE_0_LOWER;
            case 1 -> upper ? STAGE_1_UPPER : STAGE_1_LOWER;
            case 2 -> upper ? STAGE_2_UPPER : STAGE_2_LOWER;
            case 3 -> upper ? STAGE_3_UPPER : STAGE_3_LOWER;
            default -> upper ? STAGE_4_UPPER : STAGE_4_LOWER;
        };
        cir.setReturnValue(shape);
    }

}