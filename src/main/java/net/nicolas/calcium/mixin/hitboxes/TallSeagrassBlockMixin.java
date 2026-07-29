package net.nicolas.calcium.mixin.hitboxes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TallSeagrassBlock.class)
public abstract class TallSeagrassBlockMixin extends Block {

    @Shadow @Final private static VoxelShape SHAPE;

    public TallSeagrassBlockMixin(Properties settings) {
        super(settings);
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE.move(state.getOffset(pos));
    }

}