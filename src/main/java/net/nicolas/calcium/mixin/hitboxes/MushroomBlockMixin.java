package net.nicolas.calcium.mixin.hitboxes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MushroomBlock.class)
public abstract class MushroomBlockMixin extends Block {

    @Unique private static final VoxelShape SHAPE = Block.column(9.0, 0.0, 8.0);

    public MushroomBlockMixin(Properties settings) {
        super(settings);
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

}