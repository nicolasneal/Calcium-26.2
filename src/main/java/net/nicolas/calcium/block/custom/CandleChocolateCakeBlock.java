package net.nicolas.calcium.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.stats.Stats;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.item.ModItems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CandleChocolateCakeBlock extends AbstractCandleBlock {

    public static final MapCodec<CandleChocolateCakeBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("candle").forGetter(b -> b.candleBlock), propertiesCodec()).apply(i, CandleChocolateCakeBlock::new));
    public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
    private static final VoxelShape SHAPE = Shapes.or(Block.column(2.0, 8.0, 14.0), Block.column(14.0, 0.0, 8.0));
    private static final Map<CandleBlock, CandleChocolateCakeBlock> BY_CANDLE = new HashMap<>();
    private static final Iterable<Vec3> PARTICLE_OFFSETS = List.of(new Vec3(8.0, 16.0, 8.0).scale(0.0625));
    private final CandleBlock candleBlock;

    public CandleChocolateCakeBlock(Block block, Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
        if (block instanceof CandleBlock matchingCandleBlock) {
            BY_CANDLE.put(matchingCandleBlock, this);
            this.candleBlock = matchingCandleBlock;
        } else {
            throw new IllegalArgumentException("Expected block to be of " + CandleBlock.class + " was " + block.getClass());
        }
    }

    @Override protected MapCodec<? extends AbstractCandleBlock> codec() {
        return CODEC;
    }

    @Override protected Iterable<Vec3> getParticleOffsets(BlockState state) {
        return PARTICLE_OFFSETS;
    }

    @Override protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (itemStack.is(Items.FLINT_AND_STEEL) || itemStack.is(Items.FIRE_CHARGE)) {
            return InteractionResult.PASS;
        } else if (candleHit(hitResult) && itemStack.isEmpty() && state.getValue(LIT)) {
            extinguish(player, state, level, pos);
            return InteractionResult.SUCCESS;
        } else {
            return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
        }
    }

    @Override protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!player.canEat(false)) {
            return InteractionResult.PASS;
        }
        player.awardStat(Stats.EAT_CAKE_SLICE);
        player.getFoodData().eat(2, 0.1F);
        level.setBlock(pos, ModBlocks.CHOCOLATE_CAKE.defaultBlockState().setValue(CakeBlock.BITES, 1), 3);
        level.gameEvent(player, GameEvent.EAT, pos);
        dropResources(state, level, pos);
        return InteractionResult.SUCCESS;
    }

    private static boolean candleHit(BlockHitResult hitResult) {
        return hitResult.getLocation().y - hitResult.getBlockPos().getY() > 0.5;
    }

    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return new ItemStack(ModItems.CHOCOLATE_CAKE);
    }

    @Override protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        return directionToNeighbour == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
        return CakeBlock.FULL_CAKE_SIGNAL;
    }

    @Override protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    public static BlockState byCandle(CandleBlock block) {
        return BY_CANDLE.get(block).defaultBlockState();
    }

}