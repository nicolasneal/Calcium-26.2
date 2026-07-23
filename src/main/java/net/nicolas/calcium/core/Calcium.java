package net.nicolas.calcium.core;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.entity.ModAttributes;
import net.nicolas.calcium.entity.ModEntityTypes;
import net.nicolas.calcium.event.Cracking;
import net.nicolas.calcium.item.ModCompostables;
import net.nicolas.calcium.item.ModFuels;
import net.nicolas.calcium.item.ModItems;
import net.nicolas.calcium.item.ModOrder;
import net.nicolas.calcium.item.ModStacks;
import net.nicolas.calcium.mixin.accessors.AxeItemAccessor;
import net.nicolas.calcium.mixin.accessors.BlockStateBaseAccessor;
import net.nicolas.calcium.mixin.accessors.CauldronDispatcherAccessor;
import net.nicolas.calcium.mixin.accessors.CauldronInteractionsAccessor;
import net.nicolas.calcium.mixin.accessors.PoiTypesAccessor;
import net.nicolas.calcium.core.network.ModNetworking;
import net.nicolas.calcium.core.recipe.ModRecipes;
import net.nicolas.calcium.screen.beacon.CustomBeaconScreenHandler;
import net.nicolas.calcium.screen.enchanting.CustomEnchantingScreenHandler;
import net.nicolas.calcium.screen.oven.OvenScreenHandler;
import net.nicolas.calcium.screen.sniffer.SnifferInventoryScreenHandler;
import net.nicolas.calcium.sound.ModSoundGroups;
import net.nicolas.calcium.sound.ModSounds;
import net.nicolas.calcium.worldgen.ZPositionDensityFunction;

import java.util.HashMap;
import java.util.Map;

public class Calcium implements ModInitializer {

    // Screen Registration

    public static final MenuType<CustomBeaconScreenHandler> CUSTOM_BEACON_SCREEN_HANDLER = Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath("calcium", "beacon"), new MenuType<>(CustomBeaconScreenHandler::new, FeatureFlagSet.of(FeatureFlags.VANILLA)));
    public static final MenuType<CustomEnchantingScreenHandler> CUSTOM_ENCHANTING_SCREEN_HANDLER = Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath("calcium", "enchanting_screen"), new MenuType<>(CustomEnchantingScreenHandler::new, FeatureFlagSet.of(FeatureFlags.VANILLA)));
    public static final MenuType<OvenScreenHandler> OVEN_MENU = Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath("calcium", "oven"), new MenuType<>(OvenScreenHandler::new, FeatureFlagSet.of(FeatureFlags.VANILLA)));
    public static final MenuType<SnifferInventoryScreenHandler> SNIFFER_INVENTORY_MENU = Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath("calcium", "sniffer_inventory"), new MenuType<>((containerId, inventory) -> new SnifferInventoryScreenHandler(containerId, inventory, false), FeatureFlagSet.of(FeatureFlags.VANILLA)));
    public static final MenuType<SnifferInventoryScreenHandler> SNIFFER_INVENTORY_MENU_CHESTED = Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath("calcium", "sniffer_inventory_chested"), new MenuType<>((containerId, inventory) -> new SnifferInventoryScreenHandler(containerId, inventory, true), FeatureFlagSet.of(FeatureFlags.VANILLA)));

    @Override public void onInitialize() {

        // Class Initialization

        ModItems.initialize();
        ModFuels.initialize();
        ModStacks.initialize();
        ModBlocks.initialize();
        ModCompostables.initialize();
        ModRecipes.initialize();
        ModSoundGroups.initialize();
        ModSounds.initialize();
        ModAttributes.initialize();
        ModEntityTypes.initialize();
        ModNetworking.initialize();
        ModOrder.initialize();
        Cracking.registerEvents();

        // World Generation Density Function Types

        Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE, Identifier.fromNamespaceAndPath("calcium", "z_position"), ZPositionDensityFunction.CODEC.codec());

        // Resource Pack Initialization

        FabricLoader.getInstance().getModContainer("calcium").ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(Identifier.fromNamespaceAndPath("calcium", "cubic"), container, net.minecraft.network.chat.Component.nullToEmpty("§l3D Sun & Moon §r§7[1.0.0]"), ResourcePackActivationType.DEFAULT_ENABLED);
        });

        FabricLoader.getInstance().getModContainer("calcium").ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(Identifier.fromNamespaceAndPath("calcium", "bushy"), container, net.minecraft.network.chat.Component.nullToEmpty("§lBushy Leaves §r§7[1.0.0]"), ResourcePackActivationType.DEFAULT_ENABLED);
        });

        FabricLoader.getInstance().getModContainer("calcium").ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(Identifier.fromNamespaceAndPath("calcium", "overlays"), container, net.minecraft.network.chat.Component.nullToEmpty("§lGrass Overlays §r§7[1.0.1]"), ResourcePackActivationType.DEFAULT_ENABLED);
        });

        FabricLoader.getInstance().getModContainer("calcium").ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(Identifier.fromNamespaceAndPath("calcium", "addons"), container, net.minecraft.network.chat.Component.nullToEmpty("§lNicolas's Add-Ons §r§7[1.2.0]"), ResourcePackActivationType.ALWAYS_ENABLED);
        });

        // Cauldron Behavior

        ((CauldronDispatcherAccessor) (Object) CauldronInteractions.EMPTY).calcium$put(ModItems.ECTOPLASM_BUCKET, (state, world, pos, player, hand, stack) -> CauldronInteractionsAccessor.calcium$emptyBucket(world, pos, player, hand, stack, ModBlocks.ECTOPLASM_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), ModSoundGroups.ECTOPLASM_BUCKET_EMPTY));
        ((CauldronDispatcherAccessor) (Object) ModBlocks.ECTOPLASM_CAULDRON_BEHAVIOR).calcium$put(Items.BUCKET, (state, world, pos, player, hand, stack) -> CauldronInteractionsAccessor.calcium$fillBucket(state, world, pos, player, hand, stack, new ItemStack(ModItems.ECTOPLASM_BUCKET), (statex) -> true, ModSoundGroups.ECTOPLASM_BUCKET_FILL));

        // Villager POI Overwrites

        Holder<PoiType> butcherPoiType = BuiltInRegistries.POINT_OF_INTEREST_TYPE.getOrThrow(PoiTypes.BUTCHER);
        Map<BlockState, Holder<PoiType>> poiTypeByState = PoiTypesAccessor.calcium$getTypeByState();
        Blocks.SMOKER.getStateDefinition().getPossibleStates().forEach(poiTypeByState::remove);
        ModBlocks.OVEN.getStateDefinition().getPossibleStates().forEach(state -> poiTypeByState.put(state, butcherPoiType));

        // Model Offsets

        BlockBehaviour.OffsetFunction xzOffset = (state, pos) -> {
            long seed = Mth.getSeed(pos.getX(), 0, pos.getZ());
            double x = Mth.clamp(((float) (seed & 15L) / 15.0F - 0.5) * 0.5, -0.25, 0.25);
            double z = Mth.clamp(((float) (seed >> 8 & 15L) / 15.0F - 0.5) * 0.5, -0.25, 0.25);
            return new Vec3(x, 0.0, z);
        };
        for (Block block : new Block[]{Blocks.KELP, Blocks.KELP_PLANT, Blocks.SEAGRASS}) {
            for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                ((BlockStateBaseAccessor) state).setOffsetFunction(xzOffset);
            }
        }

        BlockBehaviour.OffsetFunction xyzOffset = (state, pos) -> {
            long seed = Mth.getSeed(pos.getX(), 0, pos.getZ());
            double y = ((float) (seed >> 4 & 15L) / 15.0F - 1.0) * 0.2;
            double x = Mth.clamp(((float) (seed & 15L) / 15.0F - 0.5) * 0.5, -0.25, 0.25);
            double z = Mth.clamp(((float) (seed >> 8 & 15L) / 15.0F - 0.5) * 0.5, -0.25, 0.25);
            return new Vec3(x, y, z);
        };
        for (BlockState state : Blocks.SHORT_GRASS.getStateDefinition().getPossibleStates()) {
            ((BlockStateBaseAccessor) state).setOffsetFunction(xyzOffset);
        }

        // Chorus Wood Registration

        Map<Block, Block> strippables = new HashMap<>(AxeItemAccessor.calcium$getStrippables());
        strippables.put(ModBlocks.CHORUS_BLOCK, ModBlocks.STRIPPED_CHORUS_BLOCK);
        AxeItemAccessor.calcium$setStrippables(strippables);

        ((FabricBlockEntityType) BlockEntityTypes.SHELF).addValidBlock(ModBlocks.CHORUS_SHELF);

        FabricBlockEntityType signType = (FabricBlockEntityType) BlockEntityTypes.SIGN;
        signType.addValidBlock(ModBlocks.CHORUS_SIGN);
        signType.addValidBlock(ModBlocks.CHORUS_WALL_SIGN);

        FabricBlockEntityType hangingSignType = (FabricBlockEntityType) BlockEntityTypes.HANGING_SIGN;
        hangingSignType.addValidBlock(ModBlocks.CHORUS_HANGING_SIGN);
        hangingSignType.addValidBlock(ModBlocks.CHORUS_WALL_HANGING_SIGN);

    }

}