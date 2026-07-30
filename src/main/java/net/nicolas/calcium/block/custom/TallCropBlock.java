package net.nicolas.calcium.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.nicolas.calcium.mixin.accessors.CropBlockInvoker;
import org.jspecify.annotations.Nullable;

public class TallCropBlock extends DoublePlantBlock implements BonemealableBlock {

    public static final MapCodec<TallCropBlock> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("grown_plant").forGetter(TallCropBlock::grownPlant),
            propertiesCodec()
        ).apply(instance, TallCropBlock::new)
    );
    public static final int MAX_AGE = 2;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;

    private static final VoxelShape SHAPE_STAGE_0 = Block.column(6.0, 0.0, 6.0);
    private static final VoxelShape SHAPE_STAGE_1 = Block.column(10.0, 0.0, 12.0);
    private static final VoxelShape SHAPE_STAGE_2_LOWER = Block.column(12.0, 0.0, 16.0);
    private static final VoxelShape SHAPE_STAGE_2_UPPER = Block.column(12.0, 0.0, 12.0);

    private final Block grownPlant;

    public TallCropBlock(Block grownPlant, BlockBehaviour.Properties properties) {
        super(properties);
        this.grownPlant = grownPlant;
    }

    public Block grownPlant() {
        return this.grownPlant;
    }

    @Override public MapCodec<? extends DoublePlantBlock> codec() {
        return CODEC;
    }

    @Override public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState();
    }

    @Override public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity by, ItemStack itemStack) {}

    @Override public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return false;
    }

    @Override protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(AGE) == 0) {
            return this.shapeStage0();
        }
        if (state.getValue(AGE) == 1) {
            return this.shapeStage1();
        }
        return state.getValue(HALF) == DoubleBlockHalf.UPPER ? this.shapeStage2Upper() : this.shapeStage2Lower();
    }

    protected VoxelShape shapeStage0() {
        return SHAPE_STAGE_0;
    }

    protected VoxelShape shapeStage1() {
        return SHAPE_STAGE_1;
    }

    protected VoxelShape shapeStage2Lower() {
        return SHAPE_STAGE_2_LOWER;
    }

    protected VoxelShape shapeStage2Upper() {
        return SHAPE_STAGE_2_UPPER;
    }

    @Override protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.SUPPORTS_CROPS);
    }

    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        super.createBlockStateDefinition(builder);
    }

    @Override public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER;
    }

    @Override public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        float growthSpeed = CropBlockInvoker.calcium$getGrowthSpeed(this, level, pos);
        if (random.nextInt((int) (25.0F / growthSpeed) + 1) == 0) {
            if (this.isMaxAge(state)) {
                this.convertToPlant(level, pos);
            } else {
                this.grow(level, state, pos);
            }
        }
    }

    private void grow(ServerLevel level, BlockState lowerState, BlockPos lowerPos) {
        int updatedAge = Math.min(lowerState.getValue(AGE) + 1, MAX_AGE);
        if (this.canGrow(level, lowerPos, lowerState, updatedAge)) {
            BlockState newLowerState = lowerState.setValue(AGE, updatedAge);
            level.setBlock(lowerPos, newLowerState, 2);
            if (isDouble(updatedAge)) {
                level.setBlock(lowerPos.above(), newLowerState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
            }
        }
    }

    private void convertToPlant(ServerLevel level, BlockPos lowerPos) {
        if (CropBlockInvoker.calcium$hasSufficientLight(level, lowerPos)) {
            DoublePlantBlock.placeAt(level, this.grownPlant.defaultBlockState(), lowerPos, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
        }
    }

    private boolean canGrowInto(LevelReader level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.isAir() || state.is(this);
    }

    private boolean isLower(BlockState state) {
        return state.is(this) && state.getValue(HALF) == DoubleBlockHalf.LOWER;
    }

    private static boolean isDouble(int age) {
        return age >= MAX_AGE;
    }

    private boolean canGrow(LevelReader level, BlockPos lowerPos, BlockState lowerState, int newAge) {
        return !this.isMaxAge(lowerState)
            && CropBlockInvoker.calcium$hasSufficientLight(level, lowerPos)
            && level.isInsideBuildHeight(lowerPos.above())
            && (!isDouble(newAge) || canGrowInto(level, lowerPos.above()));
    }

    private boolean isMaxAge(BlockState state) {
        return state.getValue(AGE) >= MAX_AGE;
    }

    private TallCropBlock.@Nullable PosAndState getLowerHalf(LevelReader level, BlockPos pos, BlockState state) {
        if (isLower(state)) {
            return new TallCropBlock.PosAndState(pos, state);
        }
        BlockPos lowerPos = pos.below();
        BlockState lowerState = level.getBlockState(lowerPos);
        return isLower(lowerState) ? new TallCropBlock.PosAndState(lowerPos, lowerState) : null;
    }

    @Override public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        TallCropBlock.PosAndState lowerHalf = this.getLowerHalf(level, pos, state);
        if (lowerHalf == null) {
            return false;
        }
        return this.isMaxAge(lowerHalf.state) || this.canGrow(level, lowerHalf.pos, lowerHalf.state, lowerHalf.state.getValue(AGE) + 1);
    }

    @Override public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        TallCropBlock.PosAndState lowerHalf = this.getLowerHalf(level, pos, state);
        if (lowerHalf != null) {
            if (this.isMaxAge(lowerHalf.state)) {
                this.convertToPlant(level, lowerHalf.pos);
            } else {
                this.grow(level, lowerHalf.state, lowerHalf.pos);
            }
        }
    }

    private record PosAndState(BlockPos pos, BlockState state) {
    }

}
