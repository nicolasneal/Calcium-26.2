package net.nicolas.calcium.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class IcyPlantBlock extends DirtPlantBlock {

    public IcyPlantBlock(Properties settings) {
        super(settings);
    }

    public static final MapCodec<IcyPlantBlock> CODEC = simpleCodec(IcyPlantBlock::new);
    @Override protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (super.canSurvive(state, world, pos)) return true;
        BlockState floor = world.getBlockState(pos.below());
        return floor.is(BlockTags.SNOW) || floor.is(BlockTags.ICE);
    }

}
