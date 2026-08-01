package net.nicolas.calcium.mixin.hitboxes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SpeleothemBlock;
import net.minecraft.world.level.block.SulfurSpikeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SpeleothemThickness;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SulfurSpikeBlock.class)
public abstract class SulfurSpikeBlockMixin extends Block {

    @Unique private static final VoxelShape CALCIUM_SHAPE_FULL = Block.column(10.0, 0.0, 16.0);
    @Unique private static final VoxelShape CALCIUM_SHAPE_TIP_UP = Block.column(10.0, 0.0, 12.0);
    @Unique private static final VoxelShape CALCIUM_SHAPE_TIP_DOWN = Block.column(10.0, 4.0, 16.0);

    public SulfurSpikeBlockMixin(Properties settings) {
        super(settings);
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = switch (state.getValue(SpeleothemBlock.THICKNESS)) {
            case TIP_MERGE, FRUSTUM, MIDDLE, BASE -> CALCIUM_SHAPE_FULL;
            case TIP -> state.getValue(SpeleothemBlock.TIP_DIRECTION) == Direction.DOWN ? CALCIUM_SHAPE_TIP_DOWN : CALCIUM_SHAPE_TIP_UP;
        };
        return shape.move(state.getOffset(pos));
    }

}