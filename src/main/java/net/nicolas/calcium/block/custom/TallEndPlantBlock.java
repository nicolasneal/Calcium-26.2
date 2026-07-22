package net.nicolas.calcium.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.nicolas.calcium.block.tag.ModBlockTags;

public class TallEndPlantBlock extends DoublePlantBlock {

    public static final MapCodec<TallEndPlantBlock> CODEC = simpleCodec(TallEndPlantBlock::new);

    public TallEndPlantBlock(Properties settings) {
        super(settings);
    }

    @Override public MapCodec<TallEndPlantBlock> codec() {
        return CODEC;
    }

    @Override protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
            BlockPos floorPos = pos.below();
            BlockState floorState = level.getBlockState(floorPos);
            return floorState.is(BlockTags.SUPPORTS_VEGETATION) || floorState.is(ModBlockTags.END_PLANT_PLACEMENT);
        } else {
            BlockState belowState = level.getBlockState(pos.below());
            return belowState.is(this) && belowState.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }

}