package net.nicolas.calcium.event;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.nicolas.calcium.block.ModBlocks;

import java.util.List;
import java.util.Map;
import java.util.Optional;

    // This class encodes Cracking, the feature where right-clicking a crackable block with a pickaxe converts it to
    // its cracked variant. It also lists all the blocks that are crackable, along with their cracked variants.

public class Cracking {

    private static final int CRACK_PARTICLE_COUNT = 32;

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
        .put(Blocks.CINNABAR_BRICKS, ModBlocks.CRACKED_CINNABAR_BRICKS)
        .put(Blocks.CINNABAR_BRICK_STAIRS, ModBlocks.CRACKED_CINNABAR_BRICK_STAIRS)
        .put(Blocks.CINNABAR_BRICK_SLAB, ModBlocks.CRACKED_CINNABAR_BRICK_SLAB)
        .put(Blocks.CINNABAR_BRICK_WALL, ModBlocks.CRACKED_CINNABAR_BRICK_WALL)
        .put(Blocks.SULFUR_BRICKS, ModBlocks.CRACKED_SULFUR_BRICKS)
        .put(Blocks.SULFUR_BRICK_STAIRS, ModBlocks.CRACKED_SULFUR_BRICK_STAIRS)
        .put(Blocks.SULFUR_BRICK_SLAB, ModBlocks.CRACKED_SULFUR_BRICK_SLAB)
        .put(Blocks.SULFUR_BRICK_WALL, ModBlocks.CRACKED_SULFUR_BRICK_WALL)
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
        .put(Blocks.QUARTZ_BRICKS, ModBlocks.CRACKED_QUARTZ_BRICKS)
        .put(ModBlocks.QUARTZ_BRICK_STAIRS, ModBlocks.CRACKED_QUARTZ_BRICK_STAIRS)
        .put(ModBlocks.QUARTZ_BRICK_SLAB, ModBlocks.CRACKED_QUARTZ_BRICK_SLAB)
        .put(ModBlocks.QUARTZ_BRICK_WALL, ModBlocks.CRACKED_QUARTZ_BRICK_WALL)
        .put(Blocks.PURPUR_BLOCK, ModBlocks.CRACKED_PURPUR)
        .put(Blocks.PURPUR_STAIRS, ModBlocks.CRACKED_PURPUR_STAIRS)
        .put(Blocks.PURPUR_SLAB, ModBlocks.CRACKED_PURPUR_SLAB)
        .put(ModBlocks.PURPUR_WALL, ModBlocks.CRACKED_PURPUR_WALL)
        .put(Blocks.BRICKS, ModBlocks.CRACKED_BRICKS)
        .put(Blocks.BRICK_STAIRS, ModBlocks.CRACKED_BRICK_STAIRS)
        .put(Blocks.BRICK_SLAB, ModBlocks.CRACKED_BRICK_SLAB)
        .put(Blocks.BRICK_WALL, ModBlocks.CRACKED_BRICK_WALL)
        .put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS)
        .put(Blocks.NETHER_BRICK_STAIRS, ModBlocks.CRACKED_NETHER_BRICK_STAIRS)
        .put(Blocks.NETHER_BRICK_SLAB, ModBlocks.CRACKED_NETHER_BRICK_SLAB)
        .put(Blocks.NETHER_BRICK_WALL, ModBlocks.CRACKED_NETHER_BRICK_WALL)
        .put(Blocks.RED_NETHER_BRICKS, ModBlocks.CRACKED_RED_NETHER_BRICKS)
        .put(Blocks.RED_NETHER_BRICK_STAIRS, ModBlocks.CRACKED_RED_NETHER_BRICK_STAIRS)
        .put(Blocks.RED_NETHER_BRICK_SLAB, ModBlocks.CRACKED_RED_NETHER_BRICK_SLAB)
        .put(Blocks.RED_NETHER_BRICK_WALL, ModBlocks.CRACKED_RED_NETHER_BRICK_WALL)
        .put(Blocks.RESIN_BRICKS, ModBlocks.CRACKED_RESIN_BRICKS)
        .put(Blocks.RESIN_BRICK_STAIRS, ModBlocks.CRACKED_RESIN_BRICK_STAIRS)
        .put(Blocks.RESIN_BRICK_SLAB, ModBlocks.CRACKED_RESIN_BRICK_SLAB)
        .put(Blocks.RESIN_BRICK_WALL, ModBlocks.CRACKED_RESIN_BRICK_WALL)
        .put(Blocks.MUD_BRICKS, ModBlocks.CRACKED_PACKED_MUD_BRICKS)
        .put(Blocks.MUD_BRICK_STAIRS, ModBlocks.CRACKED_PACKED_MUD_BRICK_STAIRS)
        .put(Blocks.MUD_BRICK_SLAB, ModBlocks.CRACKED_PACKED_MUD_BRICK_SLAB)
        .put(Blocks.MUD_BRICK_WALL, ModBlocks.CRACKED_PACKED_MUD_BRICK_WALL)
        .put(Blocks.END_STONE_BRICKS, ModBlocks.CRACKED_END_STONE_BRICKS)
        .put(Blocks.END_STONE_BRICK_STAIRS, ModBlocks.CRACKED_END_STONE_BRICK_STAIRS)
        .put(Blocks.END_STONE_BRICK_SLAB, ModBlocks.CRACKED_END_STONE_BRICK_SLAB)
        .put(Blocks.END_STONE_BRICK_WALL, ModBlocks.CRACKED_END_STONE_BRICK_WALL)
        .put(ModBlocks.KURODITE_BRICKS, ModBlocks.CRACKED_KURODITE_BRICKS)
        .put(ModBlocks.KURODITE_BRICK_STAIRS, ModBlocks.CRACKED_KURODITE_BRICK_STAIRS)
        .put(ModBlocks.KURODITE_BRICK_SLAB, ModBlocks.CRACKED_KURODITE_BRICK_SLAB)
        .put(ModBlocks.KURODITE_BRICK_WALL, ModBlocks.CRACKED_KURODITE_BRICK_WALL)
        .put(ModBlocks.VERADITE_BRICKS, ModBlocks.CRACKED_VERADITE_BRICKS)
        .put(ModBlocks.VERADITE_BRICK_STAIRS, ModBlocks.CRACKED_VERADITE_BRICK_STAIRS)
        .put(ModBlocks.VERADITE_BRICK_SLAB, ModBlocks.CRACKED_VERADITE_BRICK_SLAB)
        .put(ModBlocks.VERADITE_BRICK_WALL, ModBlocks.CRACKED_VERADITE_BRICK_WALL)

        .build();

    public static void registerEvents() {

        UseBlockCallback.EVENT.register((playerEntity, world, hand, hitResult) -> {

            BlockPos blockPos = hitResult.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);
            ItemStack itemStack = playerEntity.getItemInHand(hand);

            if (itemStack.is(ItemTags.PICKAXES)) {
                Optional<BlockState> crackedState = getCrackedState(blockState);
                if (crackedState.isPresent()) {
                    if (!world.isClientSide()) {
                        world.setBlock(blockPos, crackedState.get(), Block.UPDATE_ALL);
                        itemStack.hurtAndBreak(1, playerEntity, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                        spawnCrackParticles((ServerLevel) world, blockPos, blockState);
                    }
                    world.playSound(null, blockPos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;

        });

    }

    private static void spawnCrackParticles(ServerLevel level, BlockPos pos, BlockState state) {

        List<AABB> boxes = state.getShape(level, pos).toAabbs();
        if (boxes.isEmpty()) {
            boxes = List.of(new AABB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0));
        }

        BlockParticleOption particle = new BlockParticleOption(ParticleTypes.BLOCK, state);
        for (int i = 0; i < CRACK_PARTICLE_COUNT; i++) {
            AABB box = pickWeightedBox(boxes, level.getRandom());
            double x = pos.getX() + Mth.lerp(level.getRandom().nextDouble(), box.minX, box.maxX);
            double y = pos.getY() + Mth.lerp(level.getRandom().nextDouble(), box.minY, box.maxY);
            double z = pos.getZ() + Mth.lerp(level.getRandom().nextDouble(), box.minZ, box.maxZ);
            level.sendParticles(particle, x, y, z, 1, 0.0, 0.0, 0.0, 0.1);
        }

    }

    private static AABB pickWeightedBox(List<AABB> boxes, RandomSource random) {

        if (boxes.size() == 1) {
            return boxes.getFirst();
        }

        double totalVolume = 0.0;
        for (AABB box : boxes) {
            totalVolume += box.getXsize() * box.getYsize() * box.getZsize();
        }

        double roll = random.nextDouble() * totalVolume;
        for (AABB box : boxes) {
            roll -= box.getXsize() * box.getYsize() * box.getZsize();
            if (roll <= 0.0) {
                return box;
            }
        }

        return boxes.getLast();

    }

    private static Optional<BlockState> getCrackedState(BlockState originalState) {

        Block targetBlock = CRACKED_BLOCKS.get(originalState.getBlock());

        if (targetBlock == null) {
            return Optional.empty();
        }

        BlockState newState = targetBlock.defaultBlockState();

        for (net.minecraft.world.level.block.state.properties.Property<?> property : originalState.getProperties()) {
            if (newState.hasProperty(property)) {
                newState = copyProperty(originalState, newState, property);
            }
        }

        return Optional.of(newState);

    }

    private static <T extends Comparable<T>> BlockState copyProperty(BlockState source, BlockState target, net.minecraft.world.level.block.state.properties.Property<T> property) {
        return target.setValue(property, source.getValue(property));
    }

}