package net.nicolas.calcium.mixin.hitboxes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

@Mixin(WallBannerBlock.class)
public abstract class WallBannerBlockMixin extends Block {

    @Unique private static final Map<Direction, VoxelShape> NEW_SHAPES = Shapes.rotateHorizontal(Block.boxZ(16.0, 0.0, 16.0, 13.0, 16.0));

    public WallBannerBlockMixin(Properties settings) {
        super(settings);
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return NEW_SHAPES.get(state.getValue(WallBannerBlock.FACING));
    }

}