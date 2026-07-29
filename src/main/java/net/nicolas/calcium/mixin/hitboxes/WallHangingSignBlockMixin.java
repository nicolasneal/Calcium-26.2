package net.nicolas.calcium.mixin.hitboxes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WallHangingSignBlock.class)
public abstract class WallHangingSignBlockMixin extends Block {

    @Unique private static final VoxelShape BOARD_X = Block.box(7.0, 0.0, 1.0, 9.0, 10.0, 15.0);
    @Unique private static final VoxelShape BAR_X = Block.box(7.0, 14.0, 0.0, 9.0, 16.0, 16.0);
    @Unique private static final VoxelShape SHAPE_X = Shapes.or(BOARD_X, BAR_X);

    @Unique private static final VoxelShape BOARD_Z = Block.box(1.0, 0.0, 7.0, 15.0, 10.0, 9.0);
    @Unique private static final VoxelShape BAR_Z = Block.box(0.0, 14.0, 7.0, 16.0, 16.0, 9.0);
    @Unique private static final VoxelShape SHAPE_Z = Shapes.or(BOARD_Z, BAR_Z);

    public WallHangingSignBlockMixin(Properties settings) {
        super(settings);
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(WallHangingSignBlock.FACING).getAxis() == Direction.Axis.X ? SHAPE_X : SHAPE_Z;
    }

}