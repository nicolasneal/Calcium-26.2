package net.nicolas.calcium.mixin.hitboxes.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({StemBlock.class, AttachedStemBlock.class})
public abstract class StemBlockMixin {

    @Unique private static final VoxelShape STAGE_0_SHAPE = Block.column(6.0, -1.0, 5.0);
    @Unique private static final VoxelShape STAGE_1_SHAPE = Block.column(6.0, -1.0, 5.0);
    @Unique private static final VoxelShape STAGE_2_SHAPE = Block.column(8.0, -1.0, 6.0);
    @Unique private static final VoxelShape STAGE_3_SHAPE = Block.column(8.0, -1.0, 9.0);
    @Unique private static final VoxelShape STAGE_4_SHAPE = Block.column(8.0, -1.0, 12.0);
    @Unique private static final VoxelShape STAGE_5_SHAPE = Block.column(8.0, -1.0, 14.0);
    @Unique private static final VoxelShape STAGE_6_SHAPE = Block.column(10.0, -1.0, 16.0);
    @Unique private static final VoxelShape STAGE_7_SHAPE = Block.column(10.0, -1.0, 16.0);

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void calcium$stemShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (state.hasProperty(AttachedStemBlock.FACING)) {
            cir.setReturnValue(STAGE_7_SHAPE);
            return;
        }
        VoxelShape shape = switch (state.getValue(StemBlock.AGE)) {
            case 0 -> STAGE_0_SHAPE;
            case 1 -> STAGE_1_SHAPE;
            case 2 -> STAGE_2_SHAPE;
            case 3 -> STAGE_3_SHAPE;
            case 4 -> STAGE_4_SHAPE;
            case 5 -> STAGE_5_SHAPE;
            case 6 -> STAGE_6_SHAPE;
            default -> STAGE_7_SHAPE;
        };
        cir.setReturnValue(shape);
    }

}