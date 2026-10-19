package net.nicolas.calcium.mixin.hitboxes.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.PotatoBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotatoBlock.class)
public abstract class PotatoBlockMixin {

    @Unique private static final VoxelShape STAGE_1 = Block.column(12.0, -1.0, 5.0);
    @Unique private static final VoxelShape STAGE_2 = Block.column(12.0, -1.0, 8.0);
    @Unique private static final VoxelShape STAGE_3 = Block.column(14.0, -1.0, 11.0);
    @Unique private static final VoxelShape STAGE_4 = Block.column(14.0, -1.0, 16.0);

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void calcium$potatoShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        VoxelShape shape = switch (state.getValue(CropBlock.AGE)) {
            case 0, 1 -> STAGE_1;
            case 2, 3 -> STAGE_2;
            case 4, 5, 6 -> STAGE_3;
            default -> STAGE_4;
        };
        cir.setReturnValue(shape);
    }

}