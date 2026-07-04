package net.nicolas.calcium.event;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.nicolas.calcium.block.ModBlocks;

import java.util.Map;
import java.util.Optional;

public class Cracking {

    private static final Map<Block, Block> CRACKED_BLOCKS = new ImmutableMap.Builder<Block, Block>()

        .put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS)
        .put(Blocks.STONE_BRICK_STAIRS, ModBlocks.CRACKED_STONE_BRICK_STAIRS)
        .put(Blocks.STONE_BRICK_SLAB, ModBlocks.CRACKED_STONE_BRICK_SLAB)
        .put(Blocks.STONE_BRICK_WALL, ModBlocks.CRACKED_STONE_BRICK_WALL)
        .put(ModBlocks.ANDESITE_BRICKS, ModBlocks.CRACKED_ANDESITE_BRICKS)
        .put(ModBlocks.ANDESITE_BRICK_STAIRS, ModBlocks.CRACKED_ANDESITE_BRICK_STAIRS)
        .put(ModBlocks.ANDESITE_BRICK_SLAB, ModBlocks.CRACKED_ANDESITE_BRICK_SLAB)
        .put(ModBlocks.ANDESITE_BRICK_WALL, ModBlocks.CRACKED_ANDESITE_BRICK_WALL)
        .put(ModBlocks.DIORITE_BRICKS, ModBlocks.CRACKED_DIORITE_BRICKS)
        .put(ModBlocks.DIORITE_BRICK_STAIRS, ModBlocks.CRACKED_DIORITE_BRICK_STAIRS)
        .put(ModBlocks.DIORITE_BRICK_SLAB, ModBlocks.CRACKED_DIORITE_BRICK_SLAB)
        .put(ModBlocks.DIORITE_BRICK_WALL, ModBlocks.CRACKED_DIORITE_BRICK_WALL)
        .put(ModBlocks.GRANITE_BRICKS, ModBlocks.CRACKED_GRANITE_BRICKS)
        .put(ModBlocks.GRANITE_BRICK_STAIRS, ModBlocks.CRACKED_GRANITE_BRICK_STAIRS)
        .put(ModBlocks.GRANITE_BRICK_SLAB, ModBlocks.CRACKED_GRANITE_BRICK_SLAB)
        .put(ModBlocks.GRANITE_BRICK_WALL, ModBlocks.CRACKED_GRANITE_BRICK_WALL)
        .put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS)
        .put(Blocks.DEEPSLATE_BRICK_STAIRS, ModBlocks.CRACKED_DEEPSLATE_BRICK_STAIRS)
        .put(Blocks.DEEPSLATE_BRICK_SLAB, ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB)
        .put(Blocks.DEEPSLATE_BRICK_WALL, ModBlocks.CRACKED_DEEPSLATE_BRICK_WALL)
        .put(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES)
        .put(Blocks.DEEPSLATE_TILE_STAIRS, ModBlocks.CRACKED_DEEPSLATE_TILE_STAIRS)
        .put(Blocks.DEEPSLATE_TILE_SLAB, ModBlocks.CRACKED_DEEPSLATE_TILE_SLAB)
        .put(Blocks.DEEPSLATE_TILE_WALL, ModBlocks.CRACKED_DEEPSLATE_TILE_WALL)
        .put(Blocks.TUFF_BRICKS, ModBlocks.CRACKED_TUFF_BRICKS)
        .put(Blocks.TUFF_BRICK_STAIRS, ModBlocks.CRACKED_TUFF_BRICK_STAIRS)
        .put(Blocks.TUFF_BRICK_SLAB, ModBlocks.CRACKED_TUFF_BRICK_SLAB)
        .put(Blocks.TUFF_BRICK_WALL, ModBlocks.CRACKED_TUFF_BRICK_WALL)
        .put(ModBlocks.DRIPSTONE_BRICKS, ModBlocks.CRACKED_DRIPSTONE_BRICKS)
        .put(ModBlocks.DRIPSTONE_BRICK_STAIRS, ModBlocks.CRACKED_DRIPSTONE_BRICK_STAIRS)
        .put(ModBlocks.DRIPSTONE_BRICK_SLAB, ModBlocks.CRACKED_DRIPSTONE_BRICK_SLAB)
        .put(ModBlocks.DRIPSTONE_BRICK_WALL, ModBlocks.CRACKED_DRIPSTONE_BRICK_WALL)
        .put(ModBlocks.CALCITE_BRICKS, ModBlocks.CRACKED_CALCITE_BRICKS)
        .put(ModBlocks.CALCITE_BRICK_STAIRS, ModBlocks.CRACKED_CALCITE_BRICK_STAIRS)
        .put(ModBlocks.CALCITE_BRICK_SLAB, ModBlocks.CRACKED_CALCITE_BRICK_SLAB)
        .put(ModBlocks.CALCITE_BRICK_WALL, ModBlocks.CRACKED_CALCITE_BRICK_WALL)
        .put(Blocks.CUT_SANDSTONE, ModBlocks.CRACKED_SANDSTONE_BRICKS)
        .put(ModBlocks.SANDSTONE_BRICK_STAIRS, ModBlocks.CRACKED_SANDSTONE_BRICK_STAIRS)
        .put(Blocks.CUT_SANDSTONE_SLAB, ModBlocks.CRACKED_SANDSTONE_BRICK_SLAB)
        .put(ModBlocks.SANDSTONE_BRICK_WALL, ModBlocks.CRACKED_SANDSTONE_BRICK_WALL)
        .put(Blocks.CUT_RED_SANDSTONE, ModBlocks.CRACKED_RED_SANDSTONE_BRICKS)
        .put(ModBlocks.RED_SANDSTONE_BRICK_STAIRS, ModBlocks.CRACKED_RED_SANDSTONE_BRICK_STAIRS)
        .put(Blocks.CUT_RED_SANDSTONE_SLAB, ModBlocks.CRACKED_RED_SANDSTONE_BRICK_SLAB)
        .put(ModBlocks.RED_SANDSTONE_BRICK_WALL, ModBlocks.CRACKED_RED_SANDSTONE_BRICK_WALL)
        .put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)
        .put(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, ModBlocks.CRACKED_BLACKSTONE_BRICK_STAIRS)
        .put(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.CRACKED_BLACKSTONE_BRICK_SLAB)
        .put(Blocks.POLISHED_BLACKSTONE_BRICK_WALL, ModBlocks.CRACKED_BLACKSTONE_BRICK_WALL)
        .put(ModBlocks.BASALT_BRICKS, ModBlocks.CRACKED_BASALT_BRICKS)
        .put(ModBlocks.BASALT_BRICK_STAIRS, ModBlocks.CRACKED_BASALT_BRICK_STAIRS)
        .put(ModBlocks.BASALT_BRICK_SLAB, ModBlocks.CRACKED_BASALT_BRICK_SLAB)
        .put(ModBlocks.BASALT_BRICK_WALL, ModBlocks.CRACKED_BASALT_BRICK_WALL)
        .put(ModBlocks.BASALT_TILES, ModBlocks.CRACKED_BASALT_TILES)
        .put(ModBlocks.BASALT_TILE_STAIRS, ModBlocks.CRACKED_BASALT_TILE_STAIRS)
        .put(ModBlocks.BASALT_TILE_SLAB, ModBlocks.CRACKED_BASALT_TILE_SLAB)
        .put(ModBlocks.BASALT_TILE_WALL, ModBlocks.CRACKED_BASALT_TILE_WALL)
        .put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS)
        .put(Blocks.NETHER_BRICK_STAIRS, ModBlocks.CRACKED_NETHER_BRICK_STAIRS)
        .put(Blocks.NETHER_BRICK_SLAB, ModBlocks.CRACKED_NETHER_BRICK_SLAB)
        .put(Blocks.NETHER_BRICK_WALL, ModBlocks.CRACKED_NETHER_BRICK_WALL)
        .put(Blocks.RED_NETHER_BRICKS, ModBlocks.CRACKED_RED_NETHER_BRICKS)
        .put(Blocks.RED_NETHER_BRICK_STAIRS, ModBlocks.CRACKED_RED_NETHER_BRICK_STAIRS)
        .put(Blocks.RED_NETHER_BRICK_SLAB, ModBlocks.CRACKED_RED_NETHER_BRICK_SLAB)
        .put(Blocks.RED_NETHER_BRICK_WALL, ModBlocks.CRACKED_RED_NETHER_BRICK_WALL)
        .put(ModBlocks.KURODITE_BRICKS, ModBlocks.CRACKED_KURODITE_BRICKS)
        .put(ModBlocks.KURODITE_BRICK_STAIRS, ModBlocks.CRACKED_KURODITE_BRICK_STAIRS)
        .put(ModBlocks.KURODITE_BRICK_SLAB, ModBlocks.CRACKED_KURODITE_BRICK_SLAB)
        .put(ModBlocks.KURODITE_BRICK_WALL, ModBlocks.CRACKED_KURODITE_BRICK_WALL)

        .build();

    public static void registerEvents() {
        UseBlockCallback.EVENT.register((playerEntity, world, hand, hitResult) -> {
            BlockPos blockPos = hitResult.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);
            ItemStack itemStack = playerEntity.getStackInHand(hand);

            if (itemStack.isIn(ItemTags.PICKAXES)) {
                Optional<BlockState> crackedState = getCrackedState(blockState);
                if (crackedState.isPresent()) {
                    if (!world.isClient()) {
                        world.setBlockState(blockPos, crackedState.get(), Block.NOTIFY_ALL);
                        itemStack.damage(1, playerEntity, hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                        ((ServerWorld) world).spawnParticles(
                            new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState),
                            blockPos.getX() + 0.5,
                            blockPos.getY() + 0.5,
                            blockPos.getZ() + 0.5,
                            10,
                            0.25,
                            0.25,
                            0.25,
                            0.1
                        );
                    }
                    world.playSound(null, blockPos, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.PASS;

        });
    }

    private static Optional<BlockState> getCrackedState(BlockState originalState) {
        Block targetBlock = CRACKED_BLOCKS.get(originalState.getBlock());

        if (targetBlock == null) {
            return Optional.empty();
        }

        BlockState newState = targetBlock.getDefaultState();

        for (net.minecraft.state.property.Property<?> property : originalState.getProperties()) {
            if (newState.contains(property)) {
                newState = copyProperty(originalState, newState, property);
            }
        }

        return Optional.of(newState);

    }

    private static <T extends Comparable<T>> BlockState copyProperty(BlockState source, BlockState target, net.minecraft.state.property.Property<T> property) {
        return target.with(property, source.get(property));
    }

}