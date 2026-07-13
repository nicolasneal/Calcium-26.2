package net.nicolas.calcium.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.DoublePlantBlock;

public class TallIcyPlantBlock extends DoublePlantBlock {
    public static final MapCodec<TallIcyPlantBlock> CODEC = simpleCodec(TallIcyPlantBlock::new);

    public TallIcyPlantBlock(Properties settings) {
        super(settings);
    }

    @Override public MapCodec<TallIcyPlantBlock> codec() {
        return CODEC;
    }

}
