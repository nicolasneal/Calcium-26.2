package net.nicolas.calcium.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EndPlantBlock extends GenericPlantBlock {

    public static final MapCodec<EndPlantBlock> CODEC = simpleCodec(EndPlantBlock::new);
    private static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
    public static final TagKey<Block> VALID_BASES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("calcium", "end_plant_placement"));

    public EndPlantBlock(Properties settings) {
        super(settings);
    }

    @Override protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.move(state.getOffset(pos));
    }

    @Override protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos floorPos = pos.below();
        BlockState floorState = world.getBlockState(floorPos);
        return floorState.is(BlockTags.SUPPORTS_VEGETATION) || floorState.is(VALID_BASES);
    }

}
