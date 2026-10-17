package net.nicolas.calcium.block.custom;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.nicolas.calcium.block.ModBlocks;

public class BlinkvineBodyBlock extends GrowingPlantBodyBlock {

    private static final VoxelShape SHAPE = Block.column(14.0, 0.0, 16.0);

    public BlinkvineBodyBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false);
    }

    @Override protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) ModBlocks.BLINKVINE_HEAD;
    }

}