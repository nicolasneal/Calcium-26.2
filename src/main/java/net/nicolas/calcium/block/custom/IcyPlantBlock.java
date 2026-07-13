package net.nicolas.calcium.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;

public class IcyPlantBlock extends DirtPlantBlock {

    public IcyPlantBlock(Properties settings) {
        super(settings);
    }

    public static final MapCodec<IcyPlantBlock> CODEC = simpleCodec(IcyPlantBlock::new);
    @Override protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

}
