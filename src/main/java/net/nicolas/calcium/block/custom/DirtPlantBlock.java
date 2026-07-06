package net.nicolas.calcium.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DirtPlantBlock extends GenericPlantBlock {

    public DirtPlantBlock(Properties settings) {
        super(settings);
    }

    public static final MapCodec<DirtPlantBlock> CODEC = simpleCodec(DirtPlantBlock::new);
    @Override protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    private static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
    @Override protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.move(state.getOffset(pos));
    }

    @Override protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        // BlockTags.DIRT only covers plain dirt/coarse_dirt/rooted_dirt in this version - grass_block,
        // podzol, and mycelium live in the separate grass_blocks tag. SUPPORTS_VEGETATION is vanilla's
        // own comprehensive tag (dirt + grass_blocks + mud + moss_blocks + farmland) and is what these
        // plants actually need to place on ordinary grass.
        BlockPos floorPos = pos.below();
        BlockState floor = world.getBlockState(floorPos);
        return floor.is(BlockTags.SUPPORTS_VEGETATION);
    }

}