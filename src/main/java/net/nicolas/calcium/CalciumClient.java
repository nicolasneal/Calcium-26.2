package net.nicolas.calcium;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
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
import net.nicolas.calcium.network.RotateExtraSlotsPayload;
import net.nicolas.calcium.screen.CustomBeaconScreen;
import net.nicolas.calcium.screen.CustomEnchantingScreen;

import java.util.List;

public class CalciumClient implements ClientModInitializer {

    public static final KeyMapping TOGGLE_RECIPE_BOOK_KEY = new KeyMapping(
        "key.calcium.toggle_recipe_book",
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_R,
        KeyMapping.Category.INVENTORY,
        2
    );

    public static final KeyMapping ROTATE_EXTRA_SLOTS_KEY = new KeyMapping(
        "key.calcium.rotate_extra_slots",
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_LCONTROL,
        KeyMapping.Category.INVENTORY,
        1
    );

    @Override public void onInitializeClient() {

        KeyMappingHelper.registerKeyMapping(TOGGLE_RECIPE_BOOK_KEY);
        KeyMappingHelper.registerKeyMapping(ROTATE_EXTRA_SLOTS_KEY);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (ROTATE_EXTRA_SLOTS_KEY.consumeClick()) {
                if (client.player != null) {
                    ClientPlayNetworking.send(new RotateExtraSlotsPayload());
                }
            }
        });

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