package net.nicolas.calcium.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TallDryPlantBlock extends DoublePlantBlock {
    public static final MapCodec<TallDryPlantBlock> CODEC = simpleCodec(TallDryPlantBlock::new);

    public TallDryPlantBlock(Properties settings) {
        super(settings);
    }

    @Override public MapCodec<TallDryPlantBlock> codec() {
        return CODEC;
    }

    @Override protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(BlockTags.SUPPORTS_DRY_VEGETATION) || super.mayPlaceOn(floor, world, pos);
    }

}