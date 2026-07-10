package net.nicolas.calcium.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TallIcyPlantBlock extends DoublePlantBlock {
    public static final MapCodec<TallIcyPlantBlock> CODEC = simpleCodec(TallIcyPlantBlock::new);

    public TallIcyPlantBlock(Properties settings) {
        super(settings);
    }

    @Override public MapCodec<TallIcyPlantBlock> codec() {
        return CODEC;
    }

    @Override protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(BlockTags.SNOW) || floor.is(BlockTags.ICE) || super.mayPlaceOn(floor, world, pos);
    }

}
