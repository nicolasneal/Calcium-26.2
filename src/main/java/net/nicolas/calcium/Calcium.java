package net.nicolas.calcium;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.event.Cracking;
import net.nicolas.calcium.fluid.ModFluids;
import net.nicolas.calcium.item.ModItems;
import net.nicolas.calcium.mixin.AbstractBlockAccessor;
import net.nicolas.calcium.recipe.ModRecipes;
import net.nicolas.calcium.screen.CustomBeaconScreenHandler;
import net.nicolas.calcium.screen.CustomEnchantingScreenHandler;
import net.nicolas.calcium.sound.ModSounds;

public class Calcium implements ModInitializer {

	// Registering Screens

	public static final ScreenHandlerType<CustomBeaconScreenHandler> CUSTOM_BEACON_SCREEN_HANDLER =
		Registry.register(Registries.SCREEN_HANDLER, Identifier.of("calcium", "beacon"),
			new ScreenHandlerType<>(CustomBeaconScreenHandler::new, FeatureSet.of(FeatureFlags.VANILLA)));

	public static final ScreenHandlerType<CustomEnchantingScreenHandler> CUSTOM_ENCHANTING_SCREEN_HANDLER =
		Registry.register(Registries.SCREEN_HANDLER, Identifier.of("calcium", "enchanting_screen"),
			new ScreenHandlerType<>(CustomEnchantingScreenHandler::new, FeatureSet.of(FeatureFlags.VANILLA)));

	@Override public void onInitialize() {

		// Class Initialization

		ModItems.initialize();
		ModBlocks.initialize();
		ModFluids.initialize();
		ModRecipes.initialize();
		ModSounds.initialize();
		Cracking.registerEvents();

		// Resourcepack Initialization

		FabricLoader.getInstance().getModContainer("calcium").ifPresent(container -> {
			boolean success = ResourceManagerHelper.registerBuiltinResourcePack(Identifier.of("calcium", "addons"), container, net.minecraft.text.Text.of("§lNicolas's Add-Ons §r§7[1.1.1]"), ResourcePackActivationType.ALWAYS_ENABLED);
		});

		FabricLoader.getInstance().getModContainer("calcium").ifPresent(container -> {
			boolean success = ResourceManagerHelper.registerBuiltinResourcePack(Identifier.of("calcium", "cubic"), container, net.minecraft.text.Text.of("§l3D Sun & Moon"), ResourcePackActivationType.NORMAL);
		});

		FabricLoader.getInstance().getModContainer("calcium").ifPresent(container -> {
			boolean success = ResourceManagerHelper.registerBuiltinResourcePack(Identifier.of("calcium", "overlays"), container, net.minecraft.text.Text.of("§lGrass Overlays"), ResourcePackActivationType.NORMAL);
		});

		FabricLoader.getInstance().getModContainer("calcium").ifPresent(container -> {
			boolean success = ResourceManagerHelper.registerBuiltinResourcePack(Identifier.of("calcium", "bushy"), container, net.minecraft.text.Text.of("§lBushy Leaves"), ResourcePackActivationType.NORMAL);
		});

		// Cauldron Behavior

		CauldronBehavior.BEHAVIOR_MAPS.put("ectoplasm", ModBlocks.ECTOPLASM_CAULDRON_BEHAVIOR);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.map().put(ModItems.ECTOPLASM_BUCKET, (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(world, pos, player, hand, stack, ModBlocks.ECTOPLASM_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3), ModSounds.ECTOPLASM_BUCKET_EMPTY));
		ModBlocks.ECTOPLASM_CAULDRON_BEHAVIOR_MAP.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(ModItems.ECTOPLASM_BUCKET), (statex) -> true, ModSounds.ECTOPLASM_BUCKET_FILL));

		// Registering Compostables

		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.PONTEDERIA.asItem(), 0.65f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.HIBISCUS.asItem(), 0.65f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.POKER.asItem(), 0.65f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.BLACK_STINKHORN.asItem(), 0.65f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.WHITE_STINKHORN.asItem(), 0.65f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.BUSY_LIZZIE.asItem(), 0.65f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.GOLDENROD.asItem(), 0.65f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.PAMPAS.asItem(), 0.65f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.CLOVERS.asItem(), 0.30f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.EMBER_SPROUTS.asItem(), 0.30f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.PUMPKIN_SLICE, 0.50f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.FLOUR, 0.65f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.DOUGH, 0.85f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.COOKIE_DOUGH, 0.85f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.CAKE_BATTER, 1.00f);

		// Registering Fuels

		FuelRegistryEvents.BUILD.register((builder, context) -> {
			// Vanilla Fuels
			builder.add(Items.LAVA_BUCKET, 12800);
			builder.add(Items.COAL_BLOCK, 6400);
			builder.add(Items.DRIED_KELP_BLOCK, 800);
			builder.add(Items.DRIED_KELP, 200);
			builder.add(Items.BLAZE_POWDER, 1200);
			// Calcium Fuels
			builder.add(ModItems.WOODEN_ROD, 100);
			builder.add(ModItems.PIXIE_DUST, 2400);
		});

		// Overriding Item Stack Sizes

		DefaultItemComponentEvents.MODIFY.register(context -> {
			// Standard Signs
			context.modify(Items.OAK_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.SPRUCE_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.BIRCH_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.JUNGLE_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.ACACIA_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.DARK_OAK_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MANGROVE_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.CHERRY_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.PALE_OAK_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.BAMBOO_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.CRIMSON_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.WARPED_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			// Hanging Signs
			context.modify(Items.OAK_HANGING_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.SPRUCE_HANGING_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.BIRCH_HANGING_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.JUNGLE_HANGING_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.ACACIA_HANGING_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.DARK_OAK_HANGING_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MANGROVE_HANGING_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.CHERRY_HANGING_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.PALE_OAK_HANGING_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.BAMBOO_HANGING_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.CRIMSON_HANGING_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.WARPED_HANGING_SIGN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			// Beds
			context.modify(Items.WHITE_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.ORANGE_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MAGENTA_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.LIGHT_BLUE_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.YELLOW_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.LIME_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.PINK_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.GRAY_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.LIGHT_GRAY_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.CYAN_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.PURPLE_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.BLUE_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.BROWN_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.GREEN_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.RED_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.BLACK_BED, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			// Banners
			context.modify(Items.WHITE_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.ORANGE_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MAGENTA_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.LIGHT_BLUE_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.YELLOW_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.LIME_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.PINK_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.GRAY_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.LIGHT_GRAY_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.CYAN_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.PURPLE_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.BLUE_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.BROWN_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.GREEN_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.RED_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.BLACK_BANNER, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			// Music Discs
			context.modify(Items.MUSIC_DISC_13, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_CAT, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_BLOCKS, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_CHIRP, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_FAR, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_MALL, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_MELLOHI, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_STAL, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_STRAD, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_WARD, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_11, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_WAIT, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_PIGSTEP, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_5, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_RELIC, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_CREATOR, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_CREATOR_MUSIC_BOX, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_PRECIPICE, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_TEARS, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSIC_DISC_LAVA_CHICKEN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			// Banner Patterns
			context.modify(Items.FIELD_MASONED_BANNER_PATTERN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.BORDURE_INDENTED_BANNER_PATTERN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.FLOWER_BANNER_PATTERN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.CREEPER_BANNER_PATTERN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.SKULL_BANNER_PATTERN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MOJANG_BANNER_PATTERN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.GLOBE_BANNER_PATTERN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.PIGLIN_BANNER_PATTERN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.FLOW_BANNER_PATTERN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.GUSTER_BANNER_PATTERN, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			// Throwables
			context.modify(Items.ENDER_PEARL, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.SNOWBALL, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.BLUE_EGG, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.EGG, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.BROWN_EGG, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			// Miscellaneous
			context.modify(Items.BUCKET, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.WATER_BUCKET, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.LAVA_BUCKET, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.POWDER_SNOW_BUCKET, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.POTION, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.SPLASH_POTION, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.LINGERING_POTION, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.HONEY_BOTTLE, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.CAKE, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.MUSHROOM_STEW, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.BEETROOT_SOUP, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.RABBIT_STEW, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.SUSPICIOUS_STEW, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.ARMOR_STAND, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.WRITTEN_BOOK, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
			context.modify(Items.ENCHANTED_BOOK, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 64));
		});

		// Overriding Block Sound Groups

		// Dirt Blocks
		((AbstractBlockAccessor) Blocks.DIRT).setSoundGroup(BlockSoundGroup.ROOTED_DIRT);
		((AbstractBlockAccessor) Blocks.COARSE_DIRT).setSoundGroup(BlockSoundGroup.ROOTED_DIRT);
		((AbstractBlockAccessor) Blocks.PODZOL).setSoundGroup(BlockSoundGroup.ROOTED_DIRT);
		((AbstractBlockAccessor) Blocks.FARMLAND).setSoundGroup(BlockSoundGroup.ROOTED_DIRT);
		((AbstractBlockAccessor) Blocks.DIRT_PATH).setSoundGroup(BlockSoundGroup.ROOTED_DIRT);
		((AbstractBlockAccessor) Blocks.CLAY).setSoundGroup(BlockSoundGroup.ROOTED_DIRT);
		// Leaves
		((AbstractBlockAccessor) Blocks.OAK_LEAVES).setSoundGroup(BlockSoundGroup.CHERRY_LEAVES);
		((AbstractBlockAccessor) Blocks.SPRUCE_LEAVES).setSoundGroup(BlockSoundGroup.CHERRY_LEAVES);
		((AbstractBlockAccessor) Blocks.BIRCH_LEAVES).setSoundGroup(BlockSoundGroup.CHERRY_LEAVES);
		((AbstractBlockAccessor) Blocks.JUNGLE_LEAVES).setSoundGroup(BlockSoundGroup.CHERRY_LEAVES);
		((AbstractBlockAccessor) Blocks.ACACIA_LEAVES).setSoundGroup(BlockSoundGroup.CHERRY_LEAVES);
		((AbstractBlockAccessor) Blocks.DARK_OAK_LEAVES).setSoundGroup(BlockSoundGroup.CHERRY_LEAVES);
		((AbstractBlockAccessor) Blocks.MANGROVE_LEAVES).setSoundGroup(BlockSoundGroup.CHERRY_LEAVES);
		((AbstractBlockAccessor) Blocks.PALE_OAK_LEAVES).setSoundGroup(BlockSoundGroup.CHERRY_LEAVES);
		// Saplings
		((AbstractBlockAccessor) Blocks.OAK_SAPLING).setSoundGroup(BlockSoundGroup.CHERRY_SAPLING);
		((AbstractBlockAccessor) Blocks.SPRUCE_SAPLING).setSoundGroup(BlockSoundGroup.CHERRY_SAPLING);
		((AbstractBlockAccessor) Blocks.BIRCH_SAPLING).setSoundGroup(BlockSoundGroup.CHERRY_SAPLING);
		((AbstractBlockAccessor) Blocks.JUNGLE_SAPLING).setSoundGroup(BlockSoundGroup.CHERRY_SAPLING);
		((AbstractBlockAccessor) Blocks.ACACIA_SAPLING).setSoundGroup(BlockSoundGroup.CHERRY_SAPLING);
		((AbstractBlockAccessor) Blocks.DARK_OAK_SAPLING).setSoundGroup(BlockSoundGroup.CHERRY_SAPLING);
		((AbstractBlockAccessor) Blocks.MANGROVE_PROPAGULE).setSoundGroup(BlockSoundGroup.CHERRY_SAPLING);
		((AbstractBlockAccessor) Blocks.PALE_OAK_SAPLING).setSoundGroup(BlockSoundGroup.CHERRY_SAPLING);
		// Mushroom Blocks
		((AbstractBlockAccessor) Blocks.RED_MUSHROOM_BLOCK).setSoundGroup(BlockSoundGroup.WART_BLOCK);
		((AbstractBlockAccessor) Blocks.BROWN_MUSHROOM_BLOCK).setSoundGroup(BlockSoundGroup.WART_BLOCK);
		((AbstractBlockAccessor) Blocks.MUSHROOM_STEM).setSoundGroup(BlockSoundGroup.WART_BLOCK);
		// Raw Ore Blocks
		((AbstractBlockAccessor) Blocks.RAW_COPPER_BLOCK).setSoundGroup(BlockSoundGroup.STONE);
		// Plant Blocks
		((AbstractBlockAccessor) Blocks.SHORT_GRASS).setSoundGroup(BlockSoundGroup.FLOWERBED);
		((AbstractBlockAccessor) Blocks.TALL_GRASS).setSoundGroup(BlockSoundGroup.FLOWERBED);
		((AbstractBlockAccessor) Blocks.SHORT_DRY_GRASS).setSoundGroup(BlockSoundGroup.FLOWERBED);
		((AbstractBlockAccessor) Blocks.TALL_DRY_GRASS).setSoundGroup(BlockSoundGroup.FLOWERBED);
		((AbstractBlockAccessor) Blocks.FERN).setSoundGroup(BlockSoundGroup.FLOWERBED);
		((AbstractBlockAccessor) Blocks.LARGE_FERN).setSoundGroup(BlockSoundGroup.FLOWERBED);
		((AbstractBlockAccessor) Blocks.BUSH).setSoundGroup(BlockSoundGroup.FLOWERBED);
		((AbstractBlockAccessor) Blocks.DEAD_BUSH).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.DANDELION).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.POPPY).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.BLUE_ORCHID).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.ALLIUM).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.AZURE_BLUET).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.RED_TULIP).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.ORANGE_TULIP).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.WHITE_TULIP).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.ORANGE_TULIP).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.OXEYE_DAISY).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.CORNFLOWER).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.LILY_OF_THE_VALLEY).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.TORCHFLOWER).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.CLOSED_EYEBLOSSOM).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.OPEN_EYEBLOSSOM).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.WITHER_ROSE).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.SUGAR_CANE).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.SUNFLOWER).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.LILAC).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.ROSE_BUSH).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.PEONY).setSoundGroup(BlockSoundGroup.AZALEA);
		((AbstractBlockAccessor) Blocks.CACTUS).setSoundGroup(BlockSoundGroup.SWEET_BERRY_BUSH);
		((AbstractBlockAccessor) Blocks.GLOW_LICHEN).setSoundGroup(BlockSoundGroup.VINE);
		((AbstractBlockAccessor) Blocks.RED_MUSHROOM).setSoundGroup(BlockSoundGroup.FUNGUS);
		((AbstractBlockAccessor) Blocks.BROWN_MUSHROOM).setSoundGroup(BlockSoundGroup.FUNGUS);
		// Wooden Furniture
		((AbstractBlockAccessor) Blocks.CHEST).setSoundGroup(BlockSoundGroup.SHELF);
		((AbstractBlockAccessor) Blocks.TRAPPED_CHEST).setSoundGroup(BlockSoundGroup.SHELF);
		((AbstractBlockAccessor) Blocks.BARREL).setSoundGroup(BlockSoundGroup.SHELF);
		((AbstractBlockAccessor) Blocks.CRAFTING_TABLE).setSoundGroup(BlockSoundGroup.SHELF);
		((AbstractBlockAccessor) Blocks.CARTOGRAPHY_TABLE).setSoundGroup(BlockSoundGroup.SHELF);
		((AbstractBlockAccessor) Blocks.FLETCHING_TABLE).setSoundGroup(BlockSoundGroup.SHELF);
		((AbstractBlockAccessor) Blocks.SMITHING_TABLE).setSoundGroup(BlockSoundGroup.SHELF);
		((AbstractBlockAccessor) Blocks.LOOM).setSoundGroup(BlockSoundGroup.SHELF);
		((AbstractBlockAccessor) Blocks.LECTERN).setSoundGroup(BlockSoundGroup.SHELF);
		((AbstractBlockAccessor) Blocks.COMPOSTER).setSoundGroup(BlockSoundGroup.SHELF);
		((AbstractBlockAccessor) Blocks.BEE_NEST).setSoundGroup(BlockSoundGroup.SHELF);
		((AbstractBlockAccessor) Blocks.BEEHIVE).setSoundGroup(BlockSoundGroup.SHELF);
		((AbstractBlockAccessor) Blocks.BOOKSHELF).setSoundGroup(BlockSoundGroup.SHELF);
		((AbstractBlockAccessor) Blocks.NOTE_BLOCK).setSoundGroup(BlockSoundGroup.SHELF);
		((AbstractBlockAccessor) Blocks.JUKEBOX).setSoundGroup(BlockSoundGroup.SHELF);
		// Bricks
		((AbstractBlockAccessor) Blocks.BRICKS).setSoundGroup(BlockSoundGroup.NETHER_BRICKS);
		((AbstractBlockAccessor) Blocks.BRICK_STAIRS).setSoundGroup(BlockSoundGroup.NETHER_BRICKS);
		((AbstractBlockAccessor) Blocks.BRICK_SLAB).setSoundGroup(BlockSoundGroup.NETHER_BRICKS);
		((AbstractBlockAccessor) Blocks.BRICK_WALL).setSoundGroup(BlockSoundGroup.NETHER_BRICKS);
		// Metal Blocks
		((AbstractBlockAccessor) Blocks.CAULDRON).setSoundGroup(BlockSoundGroup.METAL);
		((AbstractBlockAccessor) Blocks.COAL_BLOCK).setSoundGroup(BlockSoundGroup.METAL);
		((AbstractBlockAccessor) Blocks.LAPIS_BLOCK).setSoundGroup(BlockSoundGroup.METAL);
		// End Blocks
		((AbstractBlockAccessor) Blocks.END_STONE).setSoundGroup(ModSounds.END_STONE);
		((AbstractBlockAccessor) Blocks.END_ROD).setSoundGroup(ModSounds.END_ROD);

	}

}