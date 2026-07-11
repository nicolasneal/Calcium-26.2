package net.nicolas.calcium.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.nicolas.calcium.block.ModBlocks;

public class BlinkvineHeadBlock extends GrowingPlantHeadBlock {

    public static final MapCodec<BlinkvineHeadBlock> CODEC = simpleCodec(BlinkvineHeadBlock::new);
    private static final VoxelShape SHAPE = Block.column(14.0, 0.0, 16.0);

    public BlinkvineHeadBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false, 0.1);
    }

    @Override public MapCodec<BlinkvineHeadBlock> codec() {
        return CODEC;
    }

    @Override protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return NetherVines.getBlocksToGrowWhenBonemealed(random);
    }

    @Override protected boolean canGrowInto(BlockState state) {
        return NetherVines.isValidGrowthState(state);
    }

    @Override protected Block getBodyBlock() {
        return ModBlocks.BLINKVINE_PLANT;
    }

}
