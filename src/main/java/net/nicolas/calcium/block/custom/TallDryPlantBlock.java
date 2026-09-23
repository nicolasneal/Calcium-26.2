package net.nicolas.calcium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TallDryPlantBlock extends DoublePlantBlock {

    public TallDryPlantBlock(Properties settings) {
        super(settings);
    }

    @Override protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(BlockTags.SUPPORTS_DRY_VEGETATION) || super.mayPlaceOn(floor, world, pos);
    }

}