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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowerBlock.class)
public abstract class FlowerMixin {

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    private void calcium$flowerShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {

        if ((Object) this == Blocks.DANDELION) {cir.setReturnValue(Block.column(14.0, 0.0, 13.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.GOLDEN_DANDELION) {cir.setReturnValue(Block.column(14.0, 0.0, 13.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.POPPY) {cir.setReturnValue(Block.column(12.0, 0.0, 13.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.CORNFLOWER) {cir.setReturnValue(Block.column(10.0, 0.0, 12.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.OXEYE_DAISY) {cir.setReturnValue(Block.column(8.0, 0.0, 14.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.AZURE_BLUET) {cir.setReturnValue(Block.column(10.0, 0.0, 11.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.ALLIUM) {cir.setReturnValue(Block.column(8.0, 0.0, 15.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.BLUE_ORCHID) {cir.setReturnValue(Block.column(12.0, 0.0, 13.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.LILY_OF_THE_VALLEY) {cir.setReturnValue(Block.column(10.0, 0.0, 13.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.RED_TULIP) {cir.setReturnValue(Block.column(8.0, 0.0, 14.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.ORANGE_TULIP) {cir.setReturnValue(Block.column(8.0, 0.0, 14.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.PINK_TULIP) {cir.setReturnValue(Block.column(8.0, 0.0, 14.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.WHITE_TULIP) {cir.setReturnValue(Block.column(8.0, 0.0, 14.0).move(state.getOffset(pos)));}
        else if ((Object) this == ModBlocks.PONTEDERIA) {cir.setReturnValue(Block.column(8.0, 0.0, 16.0).move(state.getOffset(pos)));}
        else if ((Object) this == ModBlocks.HIBISCUS) {cir.setReturnValue(Block.column(8.0, 0.0, 16.0).move(state.getOffset(pos)));}
        else if ((Object) this == ModBlocks.POKER) {cir.setReturnValue(Block.column(10.0, 0.0, 16.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.CLOSED_EYEBLOSSOM) {cir.setReturnValue(Block.column(8.0, 0.0, 13.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.OPEN_EYEBLOSSOM) {cir.setReturnValue(Block.column(6.0, 0.0, 10.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.TORCHFLOWER) {cir.setReturnValue(Block.column(14.0, 0.0, 16.0).move(state.getOffset(pos)));}
        else if ((Object) this == Blocks.WITHER_ROSE) {cir.setReturnValue(Block.column(8.0, 0.0, 12.0).move(state.getOffset(pos)));}

    }

}