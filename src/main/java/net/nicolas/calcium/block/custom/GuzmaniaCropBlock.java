package net.nicolas.calcium.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GuzmaniaCropBlock extends TallCropBlock {

    private static final VoxelShape SHAPE_STAGE_0 = Block.column(8.0, 0.0, 10.0);
    private static final VoxelShape SHAPE_STAGE_1 = Block.column(12.0, 0.0, 16.0);
    private static final VoxelShape SHAPE_STAGE_2_LOWER = Block.column(12.0, 0.0, 16.0);
    private static final VoxelShape SHAPE_STAGE_2_UPPER = Block.column(12.0, 0.0, 12.0);

    public GuzmaniaCropBlock(Block grownPlant, BlockBehaviour.Properties properties) {
        super(grownPlant, properties);
    }

    @Override protected VoxelShape shapeStage0() {
        return SHAPE_STAGE_0;
    }
    @Override protected VoxelShape shapeStage1() {
        return SHAPE_STAGE_1;
    }
    @Override protected VoxelShape shapeStage2Lower() {
        return SHAPE_STAGE_2_LOWER;
    }
    @Override protected VoxelShape shapeStage2Upper() {
        return SHAPE_STAGE_2_UPPER;
    }

}
