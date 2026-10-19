package net.nicolas.calcium.mixin.hitboxes.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NetherWartBlock.class)
public abstract class NetherWartBlockMixin {

    @Unique private static final VoxelShape STAGE_0_SHAPE = Block.column(14.0, 0.0, 7.0);
    @Unique private static final VoxelShape STAGE_1_SHAPE = Block.column(16.0, 0.0, 9.0);
    @Unique private static final VoxelShape STAGE_2_SHAPE = Block.column(16.0, 0.0, 12.0);
    @Unique private static final VoxelShape STAGE_3_SHAPE = Block.column(16.0, 0.0, 15.0);

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void calcium$netherWartShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        VoxelShape shape = switch (state.getValue(NetherWartBlock.AGE)) {
            case 0 -> STAGE_0_SHAPE;
            case 1 -> STAGE_1_SHAPE;
            case 2 -> STAGE_2_SHAPE;
            default -> STAGE_3_SHAPE;
        };
        cir.setReturnValue(shape);
    }

}