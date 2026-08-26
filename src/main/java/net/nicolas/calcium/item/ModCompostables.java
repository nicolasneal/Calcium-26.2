package net.nicolas.calcium.item;

import net.minecraft.world.level.block.ComposterBlock;
import net.nicolas.calcium.block.ModBlocks;

    // This class stores the mod's composting efficiency values.

public class ModCompostables {

    public static void initialize() {

        // PLANT BLOCKS

        ComposterBlock.COMPOSTABLES.put(ModBlocks.BARLEY.asItem(), 0.50f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.SEA_OATS.asItem(), 0.50f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.SHORT_ICY_IRIS.asItem(), 0.30f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.PAMPAS.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.BUSY_LIZZIE.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.PONTEDERIA.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.HIBISCUS.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.POKER.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.GOLDENROD.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.MANEDROP.asItem(), 0.85f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.GUZMANIA.asItem(), 0.85f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.CLOVERS.asItem(), 0.30f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.BLACK_STINKHORN.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.WHITE_STINKHORN.asItem(), 0.65f);

        ComposterBlock.COMPOSTABLES.put(ModBlocks.EMBER_SPROUTS.asItem(), 0.30f);

        ComposterBlock.COMPOSTABLES.put(ModBlocks.END_GROWTH.asItem(), 0.30f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.WISP_SPROUTS.asItem(), 0.30f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.WISP.asItem(), 0.30f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.FLOWERING_WISP.asItem(), 0.50f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.CELESTIAL_VINES_HEAD.asItem(), 0.50f);

        // RESOURCES

        ComposterBlock.COMPOSTABLES.put(ModItems.PUMPKIN_SLICE, 0.50f);
        ComposterBlock.COMPOSTABLES.put(ModItems.FLOUR, 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModItems.DOUGH, 0.85f);
        ComposterBlock.COMPOSTABLES.put(ModItems.BATTER, 1.00f);

        // FOODS

        ComposterBlock.COMPOSTABLES.put(ModItems.AZUREBERRIES, 0.30f);
        ComposterBlock.COMPOSTABLES.put(ModItems.CHOCOLATE_CAKE, 1.00f);
        ComposterBlock.COMPOSTABLES.put(ModItems.PUMPKIN_CAKE_ROLL, 1.00f);
        ComposterBlock.COMPOSTABLES.put(ModItems.CHORUS_CAKE_ROLL, 1.00f);

        // SEEDS

        ComposterBlock.COMPOSTABLES.put(ModItems.MANEDROP_SEEDS, 0.30f);
        ComposterBlock.COMPOSTABLES.put(ModItems.GUZMANIA_SEEDS, 0.30f);
        ComposterBlock.COMPOSTABLES.put(ModItems.AZUREBERRY_SEEDS, 0.30f);


    }

}