package net.nicolas.calcium;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.minecraft.client.KeyMapping;
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

    public static final KeyMapping TOGGLE_RECIPE_BOOK_KEY = new KeyMapping(
        "key.calcium.toggle_recipe_book",
        InputConstants.KEY_R,
        KeyMapping.Category.MISC
    );

    @Override public void onInitializeClient() {

        KeyMappingHelper.registerKeyMapping(TOGGLE_RECIPE_BOOK_KEY);

        MenuScreens.register(Calcium.CUSTOM_BEACON_SCREEN_HANDLER, CustomBeaconScreen::new);
        MenuScreens.register(Calcium.CUSTOM_ENCHANTING_SCREEN_HANDLER, CustomEnchantingScreen::new);

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