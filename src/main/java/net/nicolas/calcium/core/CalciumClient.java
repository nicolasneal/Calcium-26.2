package net.nicolas.calcium.core;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.client.model.animal.sniffer.SnifferModel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.block.entity.MonitorBlockEntity;
import net.nicolas.calcium.block.entity.ViewfinderBlockEntity;
import net.nicolas.calcium.core.client.sniffer.SnifferChestModel;
import net.nicolas.calcium.core.client.sniffer.SnifferSaddleLayer;
import net.nicolas.calcium.core.client.viewfinder.ViewfinderBlockEntityRenderer;
import net.nicolas.calcium.core.client.viewfinder.ViewfinderController;
import net.nicolas.calcium.core.client.viewfinder.ViewfinderModel;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.nicolas.calcium.core.network.OpenSnifferInventoryPayload;
import net.nicolas.calcium.core.network.RotateExtraSlotsPayload;
import net.nicolas.calcium.screen.beacon.CustomBeaconScreen;
import net.nicolas.calcium.screen.enchanting.CustomEnchantingScreen;
import net.nicolas.calcium.screen.oven.OvenScreen;
import net.nicolas.calcium.screen.sniffer.SnifferInventoryScreenHandler;
import net.nicolas.calcium.screen.sniffer.SnifferInventoryScreen;

import java.util.List;

public class CalciumClient implements ClientModInitializer {

    public static final KeyMapping ROTATE_EXTRA_SLOTS_KEY = new KeyMapping("key.calcium.rotate_extra_slots", InputConstants.Type.KEYSYM, InputConstants.KEY_LCONTROL, KeyMapping.Category.INVENTORY, 1);
    public static final KeyMapping TOGGLE_RECIPE_BOOK_KEY = new KeyMapping("key.calcium.toggle_recipe_book", InputConstants.Type.KEYSYM, InputConstants.KEY_R, KeyMapping.Category.INVENTORY, 2);

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
        MenuScreens.register(Calcium.OVEN_MENU, OvenScreen::new);
        MenuScreens.register(Calcium.SNIFFER_INVENTORY_MENU, SnifferInventoryScreen::new);
        MenuScreens.register(Calcium.SNIFFER_INVENTORY_MENU_CHESTED, SnifferInventoryScreen::new);

        ClientPlayNetworking.registerGlobalReceiver(OpenSnifferInventoryPayload.TYPE, (payload, context) -> {
            if (context.player().containerMenu instanceof SnifferInventoryScreenHandler menu && menu.containerId == payload.containerId() && context.player().level().getEntity(payload.entityId()) instanceof Sniffer sniffer) {
                menu.setClientSniffer(sniffer);
            }
        });

        FluidRenderingRegistry.register(ModBlocks.ECTOPLASM_STILL, ModBlocks.ECTOPLASM_FLOWING, new FluidModel.Unbaked(new Material(Identifier.fromNamespaceAndPath("calcium", "block/ectoplasm_still")), new Material(Identifier.fromNamespaceAndPath("calcium", "block/ectoplasm_flow")), null, null));

        ModelLayerRegistry.registerModelLayer(ViewfinderBlockEntityRenderer.LAYER, ViewfinderModel::createLayer);
        BlockEntityRendererRegistry.register(ModBlocks.VIEWFINDER_BLOCK_ENTITY, ViewfinderBlockEntityRenderer::new);

        ModelLayerRegistry.registerModelLayer(SnifferSaddleLayer.LAYER, SnifferModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(SnifferChestModel.LAYER, SnifferChestModel::createChestLayer);

        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            if (!level.isClientSide() || hand != InteractionHand.MAIN_HAND || !player.getMainHandItem().isEmpty()) {
                return InteractionResult.PASS;
            }
            BlockEntity blockEntity = level.getBlockEntity(hitResult.getBlockPos());
            if (blockEntity instanceof ViewfinderBlockEntity viewfinder) {
                ViewfinderController.startLooking(viewfinder);
                return InteractionResult.SUCCESS;
            }
            if (blockEntity instanceof MonitorBlockEntity monitor && !player.isShiftKeyDown() && monitor.getBlockState().getValue(BlockStateProperties.POWERED)) {
                ViewfinderBlockEntity linkedViewfinder = monitor.getLinkedViewfinder();
                if (linkedViewfinder != null) {
                    ViewfinderController.startLooking(linkedViewfinder, monitor.getBlockPos());
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        });

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (!ViewfinderController.isActive()) return;
            if (client.options.keyInventory.consumeClick()) {
                ViewfinderController.stopLooking();
                return;
            }
            ViewfinderController.tick();
        });

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