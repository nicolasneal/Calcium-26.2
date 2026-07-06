package net.nicolas.calcium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class GlowstoneOreBlock extends RedStoneOreBlock {

    private static final DustParticleOptions GLOW_DUST = new DustParticleOptions(0xFFDB72, 1.0F);

    public GlowstoneOreBlock(Properties settings) {
        super(settings);
    }

    @Override public void attack(BlockState state, Level world, BlockPos pos, Player player) {
        interact(state, world, pos);
    }

    @Override public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isSteppingCarefully()) {
            interact(state, world, pos);
        }
    }

    @Override protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide()) {
            spawnGlowParticles(world, pos);
        } else {
            interact(state, world, pos);
        }
        return stack.getItem() instanceof BlockItem && new BlockPlaceContext(player, hand, stack, hit).canPlace() ? InteractionResult.PASS : InteractionResult.SUCCESS;
    }

    @Override public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            spawnGlowParticles(world, pos);
        }
    }

    private static void interact(BlockState state, Level world, BlockPos pos) {
        spawnGlowParticles(world, pos);
        if (!state.getValue(LIT)) {
            world.setBlock(pos, state.setValue(LIT, true), 3);
        }
    }

    private static void spawnGlowParticles(Level world, BlockPos pos) {
        if (world.isClientSide()) {
            RandomSource random = world.getRandom();
            for (Direction direction : Direction.values()) {
                BlockPos blockPos = pos.relative(direction);
                if (!world.getBlockState(blockPos).isSolidRender()) {
                    Direction.Axis axis = direction.getAxis();
                    double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) random.nextFloat();
                    double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) random.nextFloat();
                    double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) random.nextFloat();
                    world.addParticle(GLOW_DUST, (double) pos.getX() + e, (double) pos.getY() + f, (double) pos.getZ() + g, 0.0, 0.0, 0.0);
                }
            }
        }
    }

}