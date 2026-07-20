package net.nicolas.calcium.item;

import net.fabricmc.fabric.api.registry.FuelValueEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.nicolas.calcium.block.ModBlocks;

public class ModFuels {

    public static void initialize() {

        FuelValueEvents.BUILD.register((builder, context) -> {

            // Vanilla Overrides

            builder.add(ItemTags.LOGS, 800);
            builder.add(ItemTags.PLANKS, 200);
            builder.add(ItemTags.WOODEN_STAIRS, 150);
            builder.add(ItemTags.WOODEN_SLABS, 100);
            builder.add(ItemTags.WOODEN_FENCES, 200);
            builder.add(ItemTags.FENCE_GATES, 200);
            builder.add(ItemTags.WOODEN_DOORS, 400);
            builder.add(ItemTags.WOODEN_TRAPDOORS, 200);
            builder.add(ItemTags.BAMBOO_BLOCKS, 800);
            builder.add(Items.BAMBOO_MOSAIC, 200);
            builder.add(Items.BAMBOO_MOSAIC_STAIRS, 150);
            builder.add(Items.BAMBOO_MOSAIC_SLAB, 100);
            builder.add(ItemTags.WOOL, 100);
            builder.add(ItemTags.WOOL_CARPETS, 50);
            builder.add(ItemTags.BANNERS, 200);
            builder.add(Items.MANGROVE_ROOTS, 200);
            builder.add(ItemTags.SAPLINGS, 50);
            builder.add(Items.DRY_SHORT_GRASS, 50);
            builder.add(Items.DRY_TALL_GRASS, 50);
            builder.add(Items.DEAD_BUSH, 50);
            builder.add(Items.LEAF_LITTER, 50);
            builder.add(Items.BAMBOO, 100);
            builder.add(Items.CRAFTING_TABLE, 200);
            builder.add(Items.SMITHING_TABLE, 200);
            builder.add(Items.LOOM, 200);
            builder.add(Items.CARTOGRAPHY_TABLE, 200);
            builder.add(Items.FLETCHING_TABLE, 200);
            builder.add(Items.COMPOSTER, 200);
            builder.add(Items.LECTERN, 200);
            builder.add(Items.BARREL, 200);
            builder.add(Items.CHEST, 200);
            builder.add(Items.BOOKSHELF, 200);
            builder.add(Items.CHISELED_BOOKSHELF, 200);
            builder.add(Items.NOTE_BLOCK, 200);
            builder.add(Items.JUKEBOX, 200);
            builder.add(Items.LADDER, 100);
            builder.add(Items.SCAFFOLDING, 100);
            builder.add(ItemTags.WOODEN_SHELVES, 200);
            builder.add(ItemTags.SIGNS, 200);
            builder.add(ItemTags.HANGING_SIGNS, 200);
            builder.add(ItemTags.WOODEN_BUTTONS, 100);
            builder.add(ItemTags.WOODEN_PRESSURE_PLATES, 100);
            builder.add(Items.DAYLIGHT_DETECTOR, 100);
            builder.add(Items.TRAPPED_CHEST, 200);
            builder.add(Items.WOODEN_PICKAXE, 200);
            builder.add(Items.WOODEN_AXE, 200);
            builder.add(Items.WOODEN_SHOVEL, 200);
            builder.add(Items.WOODEN_HOE, 200);
            builder.add(Items.FISHING_ROD, 200);
            builder.add(ItemTags.BOATS, 800);
            builder.add(Items.WOODEN_SWORD, 200);
            builder.add(Items.WOODEN_SPEAR, 200);
            builder.add(Items.BOW, 200);
            builder.add(Items.CROSSBOW, 200);
            builder.add(Items.COAL, 1600);
            builder.add(Items.CHARCOAL, 1600);
            builder.add(Items.COAL_BLOCK, 6400);
            builder.add(Items.DRIED_KELP, 200);
            builder.add(Items.DRIED_KELP_BLOCK, 800);
            builder.add(Items.STICK, 100);
            builder.add(Items.BLAZE_ROD, 2400);
            builder.add(Items.BLAZE_POWDER, 600);
            builder.add(Items.BOWL, 100);
            builder.add(Items.LAVA_BUCKET, 12800);
            builder.remove(ItemTags.NON_FLAMMABLE_WOOD);

            // Mod Fuels

            builder.add(ModItems.WOODEN_ROD, 100);
            builder.add(ModItems.PIXIE_DUST, 2400);
            builder.add(ModBlocks.CHORUS_BLOCK, 800);
            builder.add(ModBlocks.STRIPPED_CHORUS_BLOCK, 800);

        });

    }

}