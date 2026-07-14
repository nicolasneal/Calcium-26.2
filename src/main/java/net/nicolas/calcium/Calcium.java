package net.nicolas.calcium;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.fabricmc.fabric.api.registry.FuelValueEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.event.Cracking;
import net.nicolas.calcium.fluid.ModFluids;
import net.nicolas.calcium.item.ModItems;
import net.nicolas.calcium.mixin.accessors.AbstractBlockAccessor;
import net.nicolas.calcium.mixin.accessors.AxeItemAccessor;
import net.nicolas.calcium.mixin.accessors.BlockStateBaseAccessor;
import net.nicolas.calcium.mixin.accessors.CauldronDispatcherAccessor;
import net.nicolas.calcium.mixin.accessors.CauldronInteractionsAccessor;
import net.nicolas.calcium.network.ModNetworking;
import net.nicolas.calcium.recipe.ModRecipes;
import net.nicolas.calcium.screen.CustomBeaconScreenHandler;
import net.nicolas.calcium.screen.CustomEnchantingScreenHandler;
import net.nicolas.calcium.sound.ModSounds;

import java.util.HashMap;
import java.util.Map;

public class Calcium implements ModInitializer {

    // Screen Registration

    public static final MenuType<CustomBeaconScreenHandler> CUSTOM_BEACON_SCREEN_HANDLER =
        Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath("calcium", "beacon"),
            new MenuType<>(CustomBeaconScreenHandler::new, FeatureFlagSet.of(FeatureFlags.VANILLA)));

    public static final MenuType<CustomEnchantingScreenHandler> CUSTOM_ENCHANTING_SCREEN_HANDLER =
        Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath("calcium", "enchanting_screen"),
            new MenuType<>(CustomEnchantingScreenHandler::new, FeatureFlagSet.of(FeatureFlags.VANILLA)));

    @Override public void onInitialize() {

        // Class Initialization

        ModItems.initialize();
        ModBlocks.initialize();
        ModFluids.initialize();
        ModRecipes.initialize();
        ModSounds.initialize();
        ModNetworking.initialize();
        Cracking.registerEvents();

        // Resource Pack Initialization

        FabricLoader.getInstance().getModContainer("calcium").ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(Identifier.fromNamespaceAndPath("calcium", "cubic"), container, net.minecraft.network.chat.Component.nullToEmpty("§l3D Sun & Moon §r§7[1.0.0]"), ResourcePackActivationType.DEFAULT_ENABLED);
        });

        FabricLoader.getInstance().getModContainer("calcium").ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(Identifier.fromNamespaceAndPath("calcium", "bushy"), container, net.minecraft.network.chat.Component.nullToEmpty("§lBushy Leaves §r§7[1.0.0]"), ResourcePackActivationType.DEFAULT_ENABLED);
        });

        FabricLoader.getInstance().getModContainer("calcium").ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(Identifier.fromNamespaceAndPath("calcium", "overlays"), container, net.minecraft.network.chat.Component.nullToEmpty("§lGrass Overlays §r§7[1.0.0]"), ResourcePackActivationType.DEFAULT_ENABLED);
        });

        FabricLoader.getInstance().getModContainer("calcium").ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(Identifier.fromNamespaceAndPath("calcium", "addons"), container, net.minecraft.network.chat.Component.nullToEmpty("§lNicolas's Add-Ons §r§7[1.1.4]"), ResourcePackActivationType.ALWAYS_ENABLED);
        });

        // Cauldron Behavior

        ((CauldronDispatcherAccessor) (Object) CauldronInteractions.EMPTY).calcium$put(ModItems.ECTOPLASM_BUCKET, (state, world, pos, player, hand, stack) -> CauldronInteractionsAccessor.calcium$emptyBucket(world, pos, player, hand, stack, ModBlocks.ECTOPLASM_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), ModSounds.ECTOPLASM_BUCKET_EMPTY));
        ((CauldronDispatcherAccessor) (Object) ModBlocks.ECTOPLASM_CAULDRON_BEHAVIOR).calcium$put(Items.BUCKET, (state, world, pos, player, hand, stack) -> CauldronInteractionsAccessor.calcium$fillBucket(state, world, pos, player, hand, stack, new ItemStack(ModItems.ECTOPLASM_BUCKET), (statex) -> true, ModSounds.ECTOPLASM_BUCKET_FILL));

        // Registering Compostables

        ComposterBlock.COMPOSTABLES.put(ModBlocks.PONTEDERIA.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.HIBISCUS.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.POKER.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.BLACK_STINKHORN.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.WHITE_STINKHORN.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.BUSY_LIZZIE.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.GOLDENROD.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.PAMPAS.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.CLOVERS.asItem(), 0.30f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.EMBER_SPROUTS.asItem(), 0.30f);
        ComposterBlock.COMPOSTABLES.put(ModItems.PUMPKIN_SLICE, 0.50f);
        ComposterBlock.COMPOSTABLES.put(ModItems.FLOUR, 0.65f);
        ComposterBlock.COMPOSTABLES.put(ModItems.DOUGH, 0.85f);
        ComposterBlock.COMPOSTABLES.put(ModItems.BATTER, 1.00f);

        // Fuel Registration

        FuelValueEvents.BUILD.register((builder, context) -> {
            builder.add(Items.LAVA_BUCKET, 12800);
            builder.add(Items.COAL_BLOCK, 6400);
            builder.add(Items.DRIED_KELP_BLOCK, 800);
            builder.add(Items.DRIED_KELP, 200);
            builder.add(Items.BLAZE_POWDER, 1200);
            builder.add(ModItems.WOODEN_ROD, 100);
            builder.add(ModItems.PIXIE_DUST, 2400);
        });

        // Stack Size Overrides

        DefaultItemComponentEvents.MODIFY.register(context -> {

            // Standard Signs

            context.modify(Items.OAK_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.SPRUCE_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BIRCH_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.JUNGLE_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.ACACIA_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.DARK_OAK_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MANGROVE_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.CHERRY_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.PALE_OAK_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BAMBOO_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.CRIMSON_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.WARPED_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));

            // Hanging Signs

            context.modify(Items.OAK_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.SPRUCE_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BIRCH_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.JUNGLE_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.ACACIA_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.DARK_OAK_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MANGROVE_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.CHERRY_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.PALE_OAK_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BAMBOO_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.CRIMSON_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.WARPED_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));

            // Beds & Banners

            Items.BED.forEach(bed -> context.modify(bed, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64)));
            Items.BANNER.forEach(banner -> context.modify(banner, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64)));

            // Music Discs

            context.modify(Items.MUSIC_DISC_13, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_CAT, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_BLOCKS, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_CHIRP, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_FAR, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_MALL, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_MELLOHI, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_STAL, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_STRAD, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_WARD, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_11, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_WAIT, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_PIGSTEP, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_5, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_RELIC, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_CREATOR, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_CREATOR_MUSIC_BOX, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_PRECIPICE, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_TEARS, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_LAVA_CHICKEN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_BOUNCE, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));

            // Banner Patterns

            context.modify(Items.FIELD_MASONED_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BORDURE_INDENTED_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.FLOWER_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.CREEPER_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.SKULL_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MOJANG_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.GLOBE_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.PIGLIN_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.FLOW_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.GUSTER_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));

            // Throwables

            context.modify(Items.ENDER_PEARL, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.SNOWBALL, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BLUE_EGG, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.EGG, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BROWN_EGG, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));

            // Miscellaneous

            context.modify(Items.BUCKET, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.WATER_BUCKET, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.LAVA_BUCKET, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.POWDER_SNOW_BUCKET, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.POTION, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.SPLASH_POTION, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.LINGERING_POTION, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.HONEY_BOTTLE, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.CAKE, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSHROOM_STEW, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BEETROOT_SOUP, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.RABBIT_STEW, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.SUSPICIOUS_STEW, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.ARMOR_STAND, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.WRITTEN_BOOK, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.ENCHANTED_BOOK, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));

        });

        // Sound Group Overrides

        // Stone Blocks
        ((AbstractBlockAccessor) Blocks.BRICKS).setSoundGroup(SoundType.NETHER_BRICKS);
        ((AbstractBlockAccessor) Blocks.BRICK_STAIRS).setSoundGroup(SoundType.NETHER_BRICKS);
        ((AbstractBlockAccessor) Blocks.BRICK_SLAB).setSoundGroup(SoundType.NETHER_BRICKS);
        ((AbstractBlockAccessor) Blocks.BRICK_WALL).setSoundGroup(SoundType.NETHER_BRICKS);
        ((AbstractBlockAccessor) Blocks.END_STONE).setSoundGroup(ModSounds.END_STONE);
        ((AbstractBlockAccessor) Blocks.END_STONE_BRICKS).setSoundGroup(ModSounds.END_STONE);
        ((AbstractBlockAccessor) Blocks.END_STONE_BRICK_STAIRS).setSoundGroup(ModSounds.END_STONE);
        ((AbstractBlockAccessor) Blocks.END_STONE_BRICK_SLAB).setSoundGroup(ModSounds.END_STONE);
        ((AbstractBlockAccessor) Blocks.END_STONE_BRICK_WALL).setSoundGroup(ModSounds.END_STONE);
        // Dirt Blocks
        ((AbstractBlockAccessor) Blocks.DIRT).setSoundGroup(SoundType.ROOTED_DIRT);
        ((AbstractBlockAccessor) Blocks.COARSE_DIRT).setSoundGroup(SoundType.ROOTED_DIRT);
        ((AbstractBlockAccessor) Blocks.PODZOL).setSoundGroup(SoundType.ROOTED_DIRT);
        ((AbstractBlockAccessor) Blocks.FARMLAND).setSoundGroup(SoundType.ROOTED_DIRT);
        ((AbstractBlockAccessor) Blocks.DIRT_PATH).setSoundGroup(SoundType.ROOTED_DIRT);
        ((AbstractBlockAccessor) Blocks.CLAY).setSoundGroup(SoundType.ROOTED_DIRT);
        // Leaves
        ((AbstractBlockAccessor) Blocks.OAK_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);
        ((AbstractBlockAccessor) Blocks.SPRUCE_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);
        ((AbstractBlockAccessor) Blocks.BIRCH_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);
        ((AbstractBlockAccessor) Blocks.JUNGLE_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);
        ((AbstractBlockAccessor) Blocks.ACACIA_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);
        ((AbstractBlockAccessor) Blocks.DARK_OAK_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);
        ((AbstractBlockAccessor) Blocks.MANGROVE_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);
        ((AbstractBlockAccessor) Blocks.PALE_OAK_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);
        // Saplings
        ((AbstractBlockAccessor) Blocks.OAK_SAPLING).setSoundGroup(SoundType.CHERRY_SAPLING);
        ((AbstractBlockAccessor) Blocks.SPRUCE_SAPLING).setSoundGroup(SoundType.CHERRY_SAPLING);
        ((AbstractBlockAccessor) Blocks.BIRCH_SAPLING).setSoundGroup(SoundType.CHERRY_SAPLING);
        ((AbstractBlockAccessor) Blocks.JUNGLE_SAPLING).setSoundGroup(SoundType.CHERRY_SAPLING);
        ((AbstractBlockAccessor) Blocks.ACACIA_SAPLING).setSoundGroup(SoundType.CHERRY_SAPLING);
        ((AbstractBlockAccessor) Blocks.DARK_OAK_SAPLING).setSoundGroup(SoundType.CHERRY_SAPLING);
        ((AbstractBlockAccessor) Blocks.MANGROVE_PROPAGULE).setSoundGroup(SoundType.CHERRY_SAPLING);
        ((AbstractBlockAccessor) Blocks.PALE_OAK_SAPLING).setSoundGroup(SoundType.CHERRY_SAPLING);
        // Mushroom Blocks
        ((AbstractBlockAccessor) Blocks.RED_MUSHROOM_BLOCK).setSoundGroup(SoundType.WART_BLOCK);
        ((AbstractBlockAccessor) Blocks.BROWN_MUSHROOM_BLOCK).setSoundGroup(SoundType.WART_BLOCK);
        ((AbstractBlockAccessor) Blocks.MUSHROOM_STEM).setSoundGroup(SoundType.WART_BLOCK);
        // Raw Ore Blocks
        ((AbstractBlockAccessor) Blocks.RAW_COPPER_BLOCK).setSoundGroup(SoundType.STONE);
        // Plant Blocks
        ((AbstractBlockAccessor) Blocks.SHORT_GRASS).setSoundGroup(SoundType.PINK_PETALS);
        ((AbstractBlockAccessor) Blocks.TALL_GRASS).setSoundGroup(SoundType.PINK_PETALS);
        ((AbstractBlockAccessor) Blocks.SHORT_DRY_GRASS).setSoundGroup(SoundType.PINK_PETALS);
        ((AbstractBlockAccessor) Blocks.TALL_DRY_GRASS).setSoundGroup(SoundType.PINK_PETALS);
        ((AbstractBlockAccessor) Blocks.FERN).setSoundGroup(SoundType.PINK_PETALS);
        ((AbstractBlockAccessor) Blocks.LARGE_FERN).setSoundGroup(SoundType.PINK_PETALS);
        ((AbstractBlockAccessor) Blocks.BUSH).setSoundGroup(SoundType.PINK_PETALS);
        ((AbstractBlockAccessor) Blocks.DEAD_BUSH).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.DANDELION).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.POPPY).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.BLUE_ORCHID).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.ALLIUM).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.AZURE_BLUET).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.RED_TULIP).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.ORANGE_TULIP).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.WHITE_TULIP).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.ORANGE_TULIP).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.OXEYE_DAISY).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.CORNFLOWER).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.LILY_OF_THE_VALLEY).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.TORCHFLOWER).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.CLOSED_EYEBLOSSOM).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.OPEN_EYEBLOSSOM).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.WITHER_ROSE).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.SUGAR_CANE).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.SUNFLOWER).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.LILAC).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.ROSE_BUSH).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.PEONY).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.GOLDEN_DANDELION).setSoundGroup(SoundType.AZALEA);
        ((AbstractBlockAccessor) Blocks.CACTUS).setSoundGroup(SoundType.SWEET_BERRY_BUSH);
        ((AbstractBlockAccessor) Blocks.GLOW_LICHEN).setSoundGroup(SoundType.VINE);
        ((AbstractBlockAccessor) Blocks.RED_MUSHROOM).setSoundGroup(SoundType.FUNGUS);
        ((AbstractBlockAccessor) Blocks.BROWN_MUSHROOM).setSoundGroup(SoundType.FUNGUS);
        // Wooden Furniture
        ((AbstractBlockAccessor) Blocks.CHEST).setSoundGroup(SoundType.SHELF);
        ((AbstractBlockAccessor) Blocks.TRAPPED_CHEST).setSoundGroup(SoundType.SHELF);
        ((AbstractBlockAccessor) Blocks.BARREL).setSoundGroup(SoundType.SHELF);
        ((AbstractBlockAccessor) Blocks.CRAFTING_TABLE).setSoundGroup(SoundType.SHELF);
        ((AbstractBlockAccessor) Blocks.CARTOGRAPHY_TABLE).setSoundGroup(SoundType.SHELF);
        ((AbstractBlockAccessor) Blocks.FLETCHING_TABLE).setSoundGroup(SoundType.SHELF);
        ((AbstractBlockAccessor) Blocks.SMITHING_TABLE).setSoundGroup(SoundType.SHELF);
        ((AbstractBlockAccessor) Blocks.LOOM).setSoundGroup(SoundType.SHELF);
        ((AbstractBlockAccessor) Blocks.LECTERN).setSoundGroup(SoundType.SHELF);
        ((AbstractBlockAccessor) Blocks.COMPOSTER).setSoundGroup(SoundType.SHELF);
        ((AbstractBlockAccessor) Blocks.BEE_NEST).setSoundGroup(SoundType.SHELF);
        ((AbstractBlockAccessor) Blocks.BEEHIVE).setSoundGroup(SoundType.SHELF);
        ((AbstractBlockAccessor) Blocks.BOOKSHELF).setSoundGroup(SoundType.SHELF);
        ((AbstractBlockAccessor) Blocks.NOTE_BLOCK).setSoundGroup(SoundType.SHELF);
        ((AbstractBlockAccessor) Blocks.JUKEBOX).setSoundGroup(SoundType.SHELF);
        // Metal Blocks
        ((AbstractBlockAccessor) Blocks.CAULDRON).setSoundGroup(SoundType.METAL);
        ((AbstractBlockAccessor) Blocks.COAL_BLOCK).setSoundGroup(SoundType.METAL);
        ((AbstractBlockAccessor) Blocks.LAPIS_BLOCK).setSoundGroup(SoundType.METAL);
        // Miscellaneous
        ((AbstractBlockAccessor) Blocks.END_ROD).setSoundGroup(ModSounds.END_ROD);

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

        FabricBlockEntityType signType = (FabricBlockEntityType) BlockEntityTypes.SIGN;
        signType.addValidBlock(ModBlocks.CHORUS_SIGN);
        signType.addValidBlock(ModBlocks.CHORUS_WALL_SIGN);

        FabricBlockEntityType hangingSignType = (FabricBlockEntityType) BlockEntityTypes.HANGING_SIGN;
        hangingSignType.addValidBlock(ModBlocks.CHORUS_HANGING_SIGN);
        hangingSignType.addValidBlock(ModBlocks.CHORUS_WALL_HANGING_SIGN);

    }

}
