package net.nicolas.calcium.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ManedropCropBlock extends TallCropBlock {

    private static final VoxelShape SHAPE_STAGE_2_LOWER = Block.column(12.0, -1.0, 16.0);
    private static final VoxelShape SHAPE_STAGE_2_UPPER = Block.column(12.0, 0.0, 10.0);
    private static final VoxelShape SHAPE_STAGE_3_LOWER = Block.column(14.0, -1.0, 16.0);
    private static final VoxelShape SHAPE_STAGE_3_UPPER = Block.column(14.0, 0.0, 15.0);

    public ManedropCropBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override protected VoxelShape shapeStage2Lower() {
        return SHAPE_STAGE_2_LOWER;
    }
    @Override protected VoxelShape shapeStage2Upper() {
        return SHAPE_STAGE_2_UPPER;
    }
    @Override protected VoxelShape shapeStage3Upper() {
        return SHAPE_STAGE_3_UPPER;
    }
    @Override protected VoxelShape shapeStage3Lower() {
        return SHAPE_STAGE_3_LOWER;
    }

}