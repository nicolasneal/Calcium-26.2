package net.nicolas.calcium.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.IdentityHashMap;
import java.util.Map;

public final class ModStrengths {

    public record Strength(float hardness, float resistance) {}

    private static final Map<Block, Strength> BLOCK_STRENGTH = new IdentityHashMap<>();

    static {

        // Corrections

        BLOCK_STRENGTH.put(Blocks.GRASS_BLOCK, new Strength(0.5F, 0.5F));
        BLOCK_STRENGTH.put(Blocks.PODZOL, new Strength(0.5F, 0.5F));
        BLOCK_STRENGTH.put(Blocks.MYCELIUM, new Strength(0.5F, 0.5F));
        BLOCK_STRENGTH.put(Blocks.DIRT_PATH, new Strength(0.5F, 0.5F));
        BLOCK_STRENGTH.put(Blocks.FARMLAND, new Strength(0.5F, 0.5F));

        BLOCK_STRENGTH.put(Blocks.COBBLESTONE, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.COBBLESTONE_STAIRS, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.COBBLESTONE_SLAB, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.COBBLESTONE_WALL, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.MOSSY_COBBLESTONE, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.MOSSY_COBBLESTONE_STAIRS, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.MOSSY_COBBLESTONE_SLAB, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.MOSSY_COBBLESTONE_WALL, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.COBBLED_DEEPSLATE, new Strength(3.0F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.COBBLED_DEEPSLATE_STAIRS, new Strength(3.0F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.COBBLED_DEEPSLATE_SLAB, new Strength(3.0F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.COBBLED_DEEPSLATE_WALL, new Strength(3.0F, 6.0F));

        BLOCK_STRENGTH.put(Blocks.STONE_SLAB, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.SANDSTONE_SLAB, new Strength(0.8F, 0.8F));
        BLOCK_STRENGTH.put(Blocks.CUT_SANDSTONE_SLAB, new Strength(0.8F, 0.8F));
        BLOCK_STRENGTH.put(Blocks.RED_SANDSTONE_SLAB, new Strength(0.8F, 0.8F));
        BLOCK_STRENGTH.put(Blocks.CUT_RED_SANDSTONE_SLAB, new Strength(0.8F, 0.8F));
        BLOCK_STRENGTH.put(Blocks.QUARTZ_SLAB, new Strength(0.8F, 0.8F));
        BLOCK_STRENGTH.put(Blocks.PURPUR_SLAB, new Strength(1.5F, 6.0F));

        BLOCK_STRENGTH.put(Blocks.BLACKSTONE_SLAB, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.POLISHED_BLACKSTONE, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.POLISHED_BLACKSTONE_STAIRS, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.POLISHED_BLACKSTONE_SLAB, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.POLISHED_BLACKSTONE_WALL, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, new Strength(1.5F, 6.0F));

        BLOCK_STRENGTH.put(Blocks.SMOOTH_STONE, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.SMOOTH_STONE_SLAB, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.SMOOTH_SANDSTONE, new Strength(0.8F, 0.8F));
        BLOCK_STRENGTH.put(Blocks.SMOOTH_SANDSTONE_STAIRS, new Strength(0.8F, 0.8F));
        BLOCK_STRENGTH.put(Blocks.SMOOTH_SANDSTONE_SLAB, new Strength(0.8F, 0.8F));
        BLOCK_STRENGTH.put(Blocks.SMOOTH_RED_SANDSTONE, new Strength(0.8F, 0.8F));
        BLOCK_STRENGTH.put(Blocks.SMOOTH_RED_SANDSTONE_STAIRS, new Strength(0.8F, 0.8F));
        BLOCK_STRENGTH.put(Blocks.SMOOTH_RED_SANDSTONE_SLAB, new Strength(0.8F, 0.8F));
        BLOCK_STRENGTH.put(Blocks.SMOOTH_QUARTZ, new Strength(0.8F, 0.8F));
        BLOCK_STRENGTH.put(Blocks.SMOOTH_QUARTZ_STAIRS, new Strength(0.8F, 0.8F));
        BLOCK_STRENGTH.put(Blocks.SMOOTH_QUARTZ_SLAB, new Strength(0.8F, 0.8F));

        // Ores

        BLOCK_STRENGTH.put(Blocks.COAL_ORE, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.IRON_ORE, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.COPPER_ORE, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.GOLD_ORE, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.REDSTONE_ORE, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.LAPIS_ORE, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.DIAMOND_ORE, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.EMERALD_ORE, new Strength(1.5F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.DEEPSLATE_COAL_ORE, new Strength(3.0F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.DEEPSLATE_IRON_ORE, new Strength(3.0F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.DEEPSLATE_COPPER_ORE, new Strength(3.0F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.DEEPSLATE_GOLD_ORE, new Strength(3.0F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.DEEPSLATE_REDSTONE_ORE, new Strength(3.0F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.DEEPSLATE_LAPIS_ORE, new Strength(3.0F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.DEEPSLATE_DIAMOND_ORE, new Strength(3.0F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.DEEPSLATE_EMERALD_ORE, new Strength(3.0F, 6.0F));
        BLOCK_STRENGTH.put(Blocks.NETHER_GOLD_ORE, new Strength(0.4F, 0.4F));
        BLOCK_STRENGTH.put(Blocks.NETHER_QUARTZ_ORE, new Strength(0.4F, 0.4F));

    }

    private ModStrengths() {}

    public static Strength of(Block block) {
        return BLOCK_STRENGTH.get(block);
    }

}