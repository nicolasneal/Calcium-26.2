package net.nicolas.calcium.mixin.hitboxes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BannerBlock.class)
public abstract class BannerBlockMixin extends Block {

    @Unique private static final VoxelShape NEW_SHAPE = Block.column(10.0, 0.0, 30.0);

    public BannerBlockMixin(Properties settings) {
        super(settings);
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return NEW_SHAPE;
    }

}