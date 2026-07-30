package net.nicolas.calcium.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GuzmaniaCropBlock extends TallCropBlock {

    public static final MapCodec<GuzmaniaCropBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("grown_plant").forGetter(GuzmaniaCropBlock::grownPlant), propertiesCodec()).apply(instance, GuzmaniaCropBlock::new));

    private static final VoxelShape SHAPE_STAGE_0 = Block.column(8.0, 0.0, 10.0);
    private static final VoxelShape SHAPE_STAGE_1 = Block.column(12.0, 0.0, 16.0);
    private static final VoxelShape SHAPE_STAGE_2_LOWER = Block.column(12.0, 0.0, 16.0);
    private static final VoxelShape SHAPE_STAGE_2_UPPER = Block.column(12.0, 0.0, 12.0);

    public GuzmaniaCropBlock(Block grownPlant, BlockBehaviour.Properties properties) {
        super(grownPlant, properties);
    }

    @Override public MapCodec<? extends DoublePlantBlock> codec() {
        return CODEC;
    }

    @Override protected VoxelShape shapeStage0() {
        return SHAPE_STAGE_0;
    }
    @Override protected VoxelShape shapeStage1() {
        return SHAPE_STAGE_1;
    }
    @Override protected VoxelShape shapeStage2Lower() {
        return SHAPE_STAGE_2_LOWER;
    }
    @Override protected VoxelShape shapeStage2Upper() {
        return SHAPE_STAGE_2_UPPER;
    }

}
