package net.nicolas.calcium.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WispSproutsBlock extends EndPlantBlock {

    public static final MapCodec<WispSproutsBlock> CODEC = simpleCodec(WispSproutsBlock::new);
    private static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 3.0, 15.0);

    public WispSproutsBlock(Properties settings) {
        super(settings);
    }

    @Override protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.move(state.getOffset(pos));
    }

}