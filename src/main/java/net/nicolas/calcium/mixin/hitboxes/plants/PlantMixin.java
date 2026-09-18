package net.nicolas.calcium.mixin.hitboxes.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.nicolas.calcium.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowerBlock.class)
public abstract class PlantMixin {

    @Unique private static final VoxelShape SHAPE_1 = Block.box(1.0, 0.0, 1.0, 15.0, 13.0, 15.0);
    @Unique private static final VoxelShape SHAPE_2 = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);
    @Unique private static final VoxelShape SHAPE_3 = Block.box(3.0, 0.0, 3.0, 13.0, 12.0, 13.0);
    @Unique private static final VoxelShape SHAPE_4 = Block.box(4.0, 0.0, 4.0, 12.0, 14.0, 12.0);
    @Unique private static final VoxelShape SHAPE_5 = Block.box(3.0, 0.0, 3.0, 13.0, 11.0, 13.0);
    @Unique private static final VoxelShape SHAPE_6 = Block.box(4.0, 0.0, 4.0, 12.0, 15.0, 12.0);
    @Unique private static final VoxelShape SHAPE_7 = Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
    @Unique private static final VoxelShape SHAPE_8 = Block.box(3.0, 0.0, 3.0, 13.0, 13.0, 13.0);
    @Unique private static final VoxelShape SHAPE_9 = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    @Unique private static final VoxelShape SHAPE_10 = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    @Unique private static final VoxelShape SHAPE_11 = Block.box(4.0, 0.0, 4.0, 12.0, 13.0, 12.0);
    @Unique private static final VoxelShape SHAPE_12 = Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0);
    @Unique private static final VoxelShape SHAPE_13 = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    @Unique private static final VoxelShape SHAPE_14 = Block.box(4.0, 0.0, 4.0, 12.0, 12.0, 12.0);

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void calcium$plantShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {

        if ((Object) this == Blocks.DANDELION) {cir.setReturnValue(SHAPE_1.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.GOLDEN_DANDELION) {cir.setReturnValue(SHAPE_1.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.POPPY) {cir.setReturnValue(SHAPE_2.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.CORNFLOWER) {cir.setReturnValue(SHAPE_3.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.OXEYE_DAISY) {cir.setReturnValue(SHAPE_4.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.AZURE_BLUET) {cir.setReturnValue(SHAPE_5.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.ALLIUM) {cir.setReturnValue(SHAPE_6.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.BLUE_ORCHID) {cir.setReturnValue(SHAPE_7.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.LILY_OF_THE_VALLEY) {cir.setReturnValue(SHAPE_8.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.RED_TULIP) {cir.setReturnValue(SHAPE_4.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.ORANGE_TULIP) {cir.setReturnValue(SHAPE_4.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.PINK_TULIP) {cir.setReturnValue(SHAPE_4.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.WHITE_TULIP) {cir.setReturnValue(SHAPE_4.move(state.getOffset(pos)));}
        else if ((Object) this == ModBlocks.PONTEDERIA) {cir.setReturnValue(SHAPE_9.move(state.getOffset(pos)));}
        else if ((Object) this == ModBlocks.HIBISCUS) {cir.setReturnValue(SHAPE_9.move(state.getOffset(pos)));}
        else if ((Object) this == ModBlocks.POKER) {cir.setReturnValue(SHAPE_10.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.CLOSED_EYEBLOSSOM) {cir.setReturnValue(SHAPE_11.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.OPEN_EYEBLOSSOM) {cir.setReturnValue(SHAPE_12.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.TORCHFLOWER) {cir.setReturnValue(SHAPE_13.move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.WITHER_ROSE) {cir.setReturnValue(SHAPE_14.move(state.getOffset(pos)));}

    }

}