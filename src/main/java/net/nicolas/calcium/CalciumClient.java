package net.nicolas.calcium;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.fluid.ModFluids;
import net.nicolas.calcium.screen.CustomBeaconScreen;
import net.nicolas.calcium.screen.CustomEnchantingScreen;

import java.util.List;

public class CalciumClient implements ClientModInitializer {

    @Override public void onInitializeClient() {

        MenuScreens.register(Calcium.CUSTOM_BEACON_SCREEN_HANDLER, CustomBeaconScreen::new);
        MenuScreens.register(Calcium.CUSTOM_ENCHANTING_SCREEN_HANDLER, CustomEnchantingScreen::new);

        // Cutout render layers (grates, doors, bars, chains, lanterns, all the plant blocks,
        // potted variants) are now assigned automatically from each texture's real alpha
        // channel (ChunkSectionLayer.byTransparency) - no registration needed any more.
        // Soul Glass's forced translucency now lives in its block model JSON instead
        // (models/block/soul_glass.json - "force_translucent": true).

        FluidRenderingRegistry.register(
            ModFluids.ECTOPLASM_STILL,
            ModFluids.ECTOPLASM_FLOWING,
            new FluidModel.Unbaked(
                new Material(Identifier.fromNamespaceAndPath("calcium", "block/ectoplasm_still")),
                new Material(Identifier.fromNamespaceAndPath("calcium", "block/ectoplasm_flow")),
                null,
                null
            )
        );

        BlockColorRegistry.register(
            List.of(BlockTintSources.grass()),
            ModBlocks.WILD_WHEAT,
            ModBlocks.WILD_CARROT,
            ModBlocks.WILD_POTATO,
            ModBlocks.WILD_BEETROOT,
            ModBlocks.BARLEY,
            ModBlocks.CLOVERS,
            ModBlocks.POTTED_BUSH
        );

    }

}
