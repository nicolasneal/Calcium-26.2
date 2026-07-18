package net.nicolas.calcium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.nicolas.calcium.sound.ModSounds;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class CakeRollBlock extends CakeBlock {

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    // Base geometry is defined facing north (matching the unrotated model); Shapes.rotateHorizontal derives the rest.
    private static final List<Map<Direction, VoxelShape>> SHAPES = IntStream.rangeClosed(0, MAX_BITES)
            .mapToObj(bites -> Shapes.rotateHorizontal(Block.box(4, 0, 1 + bites * 2, 12, 7, 15)))
            .toList();

    public CakeRollBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(BITES)).get(state.getValue(FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    // Unlike vanilla cake, placing a candle on top does nothing special (no CandleCakeBlock equivalent).
    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            if (calcium$eat(level, pos, state, player).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
            if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }
        return calcium$eat(level, pos, state, player);
    }

    private InteractionResult calcium$eat(Level level, BlockPos pos, BlockState state, Player player) {
        if (!player.canEat(false)) {
            return InteractionResult.PASS;
        }
        player.getFoodData().eat(2, 0.1F);
        player.spawnItemParticles(new ItemStack(this), 16);
        level.playSound(null, pos, ModSounds.CAKE_ROLL_EAT, SoundSource.PLAYERS, 1.0F, Mth.randomBetween(player.getRandom(), 0.9F, 1.1F));
        int bites = state.getValue(BITES);
        level.gameEvent(player, GameEvent.EAT, pos);
        if (bites < MAX_BITES) {
            level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
            level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            calcium$onFullyEaten(level, pos, player);
        }
        return InteractionResult.SUCCESS;
    }

    // Runs after the final slice is consumed and the block removed. Called on both client and server
    // (the eat logic runs client-side for prediction), so side-sensitive effects must guard themselves.
    protected void calcium$onFullyEaten(Level level, BlockPos pos, Player player) {
    }

}
