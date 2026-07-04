package net.nicolas.calcium.block;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ColorCode;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.biome.Biome;
import net.nicolas.calcium.block.custom.*;
import net.nicolas.calcium.fluid.ModFluids;
import net.nicolas.calcium.sound.ModSounds;

import java.util.function.Function;

public class ModBlocks {

    public static final String MOD_ID = "calcium";

    // NATURAL BLOCKS (3)

    public static final Block SILT = register("silt", settings -> new SandBlock(new ColorCode(0x766551), settings), Block.Settings.copy(Blocks.SAND), true);
    public static final Block SOULSLATE = register("soulslate", Block::new, Block.Settings.create().sounds(ModSounds.SOULSLATE).mapColor(MapColor.BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.4F, 0.4F), true);
    public static final Block NETHERRACK_GLOWSTONE_ORE = register("netherrack_glowstone_ore", GlowstoneOreBlock::new, Block.Settings.create().luminance(state -> state.get(RedstoneOreBlock.LIT) ? 9 : 0).sounds(BlockSoundGroup.NETHER_ORE).mapColor(MapColor.DARK_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 3.0F), true);
    public static final Block PALLID_MAGNIA = register("pallid_magnia", Block::new, Block.Settings.create().sounds(ModSounds.PALLID_MAGNIA).mapColor(MapColor.WHITE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F), true);
    public static final Block UMBRAL_MAGNIA = register("umbral_magnia", Block::new, Block.Settings.create().sounds(ModSounds.UMBRAL_MAGNIA).mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F), true);

    // NATURAL FLUIDS (1, 1)

    public static final Block ECTOPLASM = register("ectoplasm", settings -> new FluidBlock(ModFluids.ECTOPLASM_STILL, settings), Block.Settings.create().mapColor(MapColor.DIAMOND_BLUE).replaceable().noCollision().strength(100.0F).pistonBehavior(PistonBehavior.DESTROY).dropsNothing().liquid().luminance(state -> 6), false);
    public static final java.util.Map<Item, CauldronBehavior> ECTOPLASM_CAULDRON_BEHAVIOR_MAP = Util.make(new Object2ObjectOpenHashMap<>(), map -> map.defaultReturnValue((state, world, pos, player, hand, stack) -> ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION));
    public static final CauldronBehavior.CauldronBehaviorMap ECTOPLASM_CAULDRON_BEHAVIOR = new CauldronBehavior.CauldronBehaviorMap("ectoplasm", ECTOPLASM_CAULDRON_BEHAVIOR_MAP);
    public static final Block ECTOPLASM_CAULDRON = register("ectoplasm_cauldron", settings -> new LeveledCauldronBlock(Biome.Precipitation.NONE, ECTOPLASM_CAULDRON_BEHAVIOR, settings), Block.Settings.copy(Blocks.CAULDRON).mapColor(MapColor.IRON_GRAY).luminance(state -> 6), false);

    // PLANT BLOCKS (18, 5)

    public static final Block WILD_WHEAT = register("wild_wheat", DirtPlantBlock::new, Block.Settings.create().mapColor(MapColor.DARK_GREEN).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).offset(AbstractBlock.OffsetType.XZ).burnable().pistonBehavior(PistonBehavior.DESTROY), true);
    public static final Block WILD_CARROT = register("wild_carrot", DirtPlantBlock::new, Block.Settings.create().mapColor(MapColor.DARK_GREEN).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).offset(AbstractBlock.OffsetType.XZ).burnable().pistonBehavior(PistonBehavior.DESTROY), true);
    public static final Block WILD_POTATO = register("wild_potato", DirtPlantBlock::new, Block.Settings.create().mapColor(MapColor.DARK_GREEN).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).offset(AbstractBlock.OffsetType.XZ).burnable().pistonBehavior(PistonBehavior.DESTROY), true);
    public static final Block WILD_BEETROOT = register("wild_beetroot", DirtPlantBlock::new, Block.Settings.create().mapColor(MapColor.DARK_GREEN).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).offset(AbstractBlock.OffsetType.XZ).burnable().pistonBehavior(PistonBehavior.DESTROY), true);
    public static final Block PONTEDERIA = register("pontederia", (settings) -> new FlowerBlock(StatusEffects.WATER_BREATHING, 5.0f, settings), Block.Settings.create().mapColor(MapColor.DARK_GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).offset(AbstractBlock.OffsetType.XZ).burnable().pistonBehavior(PistonBehavior.DESTROY),true);
    public static final Block HIBISCUS = register("hibiscus", (settings) -> new FlowerBlock(StatusEffects.BLINDNESS, 5.0f, settings), Block.Settings.create().mapColor(MapColor.DARK_GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).offset(AbstractBlock.OffsetType.XZ).burnable().pistonBehavior(PistonBehavior.DESTROY),true);
    public static final Block POKER = register("poker", (settings) -> new FlowerBlock(StatusEffects.NIGHT_VISION, 5.0f, settings), Block.Settings.create().mapColor(MapColor.DARK_GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).offset(AbstractBlock.OffsetType.XZ).burnable().pistonBehavior(PistonBehavior.DESTROY),true);
    public static final Block BLACK_STINKHORN = register("black_stinkhorn", GenericPlantBlock::new, Block.Settings.create().mapColor(MapColor.DEEPSLATE_GRAY).noCollision().breakInstantly().sounds(BlockSoundGroup.FUNGUS).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).nonOpaque(), true);
    public static final Block WHITE_STINKHORN = register("white_stinkhorn", GenericPlantBlock::new, Block.Settings.create().mapColor(MapColor.LIGHT_GRAY).noCollision().breakInstantly().sounds(BlockSoundGroup.FUNGUS).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).nonOpaque(), true);
    public static final Block BUSY_LIZZIE = register("busy_lizzie", BushBlock::new, Block.Settings.create().mapColor(MapColor.DARK_GREEN).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY).burnable(), true);
    public static final Block GOLDENROD = register("goldenrod", TallFlowerBlock::new, Block.Settings.create().mapColor(MapColor.YELLOW).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).burnable(), true);
    public static final Block BARLEY = register("barley", TallPlantBlock::new, Block.Settings.create().mapColor(MapColor.DARK_GREEN).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).burnable(), true);
    public static final Block SEA_OATS = register("sea_oats", TallDryPlantBlock::new, Block.Settings.create().mapColor(MapColor.PALE_YELLOW).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).burnable(), true);
    public static final Block PAMPAS = register("pampas", TallPlantBlock::new, Block.Settings.create().mapColor(MapColor.PALE_YELLOW).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).burnable(), true);
    public static final Block ICY_IRIS = register("icy_iris", DirtPlantBlock::new, Block.Settings.create().mapColor(MapColor.PALE_PURPLE).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).burnable(), true);
    public static final Block TALL_ICY_IRIS = register("tall_icy_iris", TallFlowerBlock::new, Block.Settings.create().mapColor(MapColor.PALE_PURPLE).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).burnable(), true);
    public static final Block CLOVERS = register("clovers", FlowerbedBlock::new, Block.Settings.create().mapColor(MapColor.DARK_GREEN).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.FLOWERBED).pistonBehavior(PistonBehavior.DESTROY).burnable(), true);
    public static final Block EMBER_SPROUTS = register("ember_sprouts", FlatPlantBlock::new, Block.Settings.create().mapColor(MapColor.DARK_CRIMSON).replaceable().noCollision().breakInstantly().sounds(BlockSoundGroup.NETHER_SPROUTS).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY), true);
    public static final Block POTTED_PONTEDERIA = register("potted_pontederia", settings -> new FlowerPotBlock(PONTEDERIA, settings), Block.Settings.copy(Blocks.FLOWER_POT), false);
    public static final Block POTTED_HIBISCUS = register("potted_hibiscus", settings -> new FlowerPotBlock(HIBISCUS, settings), Block.Settings.copy(Blocks.FLOWER_POT), false);
    public static final Block POTTED_POKER = register("potted_poker", settings -> new FlowerPotBlock(POKER, settings), Block.Settings.copy(Blocks.FLOWER_POT), false);
    public static final Block POTTED_BLACK_STINKHORN = register("potted_black_stinkhorn", settings -> new FlowerPotBlock(BLACK_STINKHORN, settings), Block.Settings.copy(Blocks.FLOWER_POT), false);
    public static final Block POTTED_WHITE_STINKHORN = register("potted_white_stinkhorn", settings -> new FlowerPotBlock(WHITE_STINKHORN, settings), Block.Settings.copy(Blocks.FLOWER_POT), false);

    // STONE VARIANT BLOCKS (124)

    public static final Block POLISHED_STONE = register("polished_stone", Block::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block POLISHED_STONE_STAIRS = register("polished_stone_stairs", settings -> new StairsBlock(ModBlocks.POLISHED_STONE.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block POLISHED_STONE_SLAB = register("polished_stone_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block POLISHED_STONE_WALL = register("polished_stone_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_STONE_BRICK_STAIRS = register("cracked_stone_brick_stairs", settings -> new StairsBlock(Blocks.CRACKED_STONE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_STONE_BRICK_SLAB = register("cracked_stone_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_STONE_BRICK_WALL = register("cracked_stone_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block SMOOTH_STONE_STAIRS = register("smooth_stone_stairs", settings -> new StairsBlock(Blocks.SMOOTH_STONE.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block SMOOTH_STONE_WALL = register("smooth_stone_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block POLISHED_ANDESITE_WALL = register("polished_andesite_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block ANDESITE_BRICKS = register("andesite_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block ANDESITE_BRICK_STAIRS = register("andesite_brick_stairs", settings -> new StairsBlock(ModBlocks.ANDESITE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block ANDESITE_BRICK_SLAB = register("andesite_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block ANDESITE_BRICK_WALL = register("andesite_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_ANDESITE_BRICKS = register("cracked_andesite_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_ANDESITE_BRICK_STAIRS = register("cracked_andesite_brick_stairs", settings -> new StairsBlock(ModBlocks.ANDESITE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_ANDESITE_BRICK_SLAB = register("cracked_andesite_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_ANDESITE_BRICK_WALL = register("cracked_andesite_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CHISELED_ANDESITE = register("chiseled_andesite", Block::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block POLISHED_DIORITE_WALL = register("polished_diorite_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block DIORITE_BRICKS = register("diorite_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block DIORITE_BRICK_STAIRS = register("diorite_brick_stairs", settings -> new StairsBlock(ModBlocks.DIORITE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block DIORITE_BRICK_SLAB = register("diorite_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block DIORITE_BRICK_WALL = register("diorite_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_DIORITE_BRICKS = register("cracked_diorite_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_DIORITE_BRICK_STAIRS = register("cracked_diorite_brick_stairs", settings -> new StairsBlock(ModBlocks.CRACKED_DIORITE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_DIORITE_BRICK_SLAB = register("cracked_diorite_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_DIORITE_BRICK_WALL = register("cracked_diorite_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CHISELED_DIORITE = register("chiseled_diorite", Block::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block POLISHED_GRANITE_WALL = register("polished_granite_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block GRANITE_BRICKS = register("granite_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block GRANITE_BRICK_STAIRS = register("granite_brick_stairs", settings -> new StairsBlock(ModBlocks.GRANITE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block GRANITE_BRICK_SLAB = register("granite_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block GRANITE_BRICK_WALL = register("granite_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_GRANITE_BRICKS = register("cracked_granite_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_GRANITE_BRICK_STAIRS = register("cracked_granite_brick_stairs", settings -> new StairsBlock(ModBlocks.CRACKED_GRANITE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_GRANITE_BRICK_SLAB = register("cracked_granite_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_GRANITE_BRICK_WALL = register("cracked_granite_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CHISELED_GRANITE = register("chiseled_granite", Block::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_DEEPSLATE_BRICK_STAIRS = register("cracked_deepslate_brick_stairs", settings -> new StairsBlock(Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.DEEPSLATE).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 6.0F), true);
    public static final Block CRACKED_DEEPSLATE_BRICK_SLAB = register("cracked_deepslate_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.DEEPSLATE).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 6.0F), true);
    public static final Block CRACKED_DEEPSLATE_BRICK_WALL = register("cracked_deepslate_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.DEEPSLATE).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 6.0F), true);
    public static final Block CRACKED_DEEPSLATE_TILE_STAIRS = register("cracked_deepslate_tile_stairs", settings -> new StairsBlock(Blocks.CRACKED_DEEPSLATE_TILES.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.DEEPSLATE_TILES).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 6.0F), true);
    public static final Block CRACKED_DEEPSLATE_TILE_SLAB = register("cracked_deepslate_tile_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.DEEPSLATE_TILES).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 6.0F), true);
    public static final Block CRACKED_DEEPSLATE_TILE_WALL = register("cracked_deepslate_tile_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.DEEPSLATE).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 6.0F), true);
    public static final Block CRACKED_TUFF_BRICKS = register("cracked_tuff_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.TUFF_BRICKS).mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_TUFF_BRICK_STAIRS = register("cracked_tuff_brick_stairs", settings -> new StairsBlock(ModBlocks.CRACKED_TUFF_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.TUFF_BRICKS).mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_TUFF_BRICK_SLAB = register("cracked_tuff_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.TUFF_BRICKS).mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_TUFF_BRICK_WALL = register("cracked_tuff_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.TUFF_BRICKS).mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block POLISHED_DRIPSTONE = register("polished_dripstone", Block::new, Block.Settings.create().sounds(BlockSoundGroup.DRIPSTONE_BLOCK).mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 1.0F), true);
    public static final Block POLISHED_DRIPSTONE_STAIRS = register("polished_dripstone_stairs", settings -> new StairsBlock(ModBlocks.POLISHED_DRIPSTONE.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.DRIPSTONE_BLOCK).mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 1.0F), true);
    public static final Block POLISHED_DRIPSTONE_SLAB = register("polished_dripstone_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.DRIPSTONE_BLOCK).mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 1.0F), true);
    public static final Block POLISHED_DRIPSTONE_WALL = register("polished_dripstone_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.DRIPSTONE_BLOCK).mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 1.0F), true);
    public static final Block DRIPSTONE_BRICKS = register("dripstone_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.DRIPSTONE_BLOCK).mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 1.0F), true);
    public static final Block DRIPSTONE_BRICK_STAIRS = register("dripstone_brick_stairs", settings -> new StairsBlock(ModBlocks.DRIPSTONE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.DRIPSTONE_BLOCK).mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 1.0F), true);
    public static final Block DRIPSTONE_BRICK_SLAB = register("dripstone_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.DRIPSTONE_BLOCK).mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 1.0F), true);
    public static final Block DRIPSTONE_BRICK_WALL = register("dripstone_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.DRIPSTONE_BLOCK).mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 1.0F), true);
    public static final Block CRACKED_DRIPSTONE_BRICKS = register("cracked_dripstone_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.DRIPSTONE_BLOCK).mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 1.0F), true);
    public static final Block CRACKED_DRIPSTONE_BRICK_STAIRS = register("cracked_dripstone_brick_stairs", settings -> new StairsBlock(ModBlocks.CRACKED_DRIPSTONE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.DRIPSTONE_BLOCK).mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 1.0F), true);
    public static final Block CRACKED_DRIPSTONE_BRICK_SLAB = register("cracked_dripstone_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.DRIPSTONE_BLOCK).mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 1.0F), true);
    public static final Block CRACKED_DRIPSTONE_BRICK_WALL = register("cracked_dripstone_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.DRIPSTONE_BLOCK).mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 1.0F), true);
    public static final Block CHISELED_DRIPSTONE = register("chiseled_dripstone", Block::new, Block.Settings.create().sounds(BlockSoundGroup.DRIPSTONE_BLOCK).mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 1.0F), true);
    // Polished Calcite
    // Polished Calcite Stairs
    // Polished Calcite Slab
    // Polished Calcite Wall
    public static final Block CALCITE_BRICKS = register("calcite_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.CALCITE).mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.75F, 0.75F), true);
    public static final Block CALCITE_BRICK_STAIRS = register("calcite_brick_stairs", settings -> new StairsBlock(ModBlocks.CALCITE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.CALCITE).mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.75F, 0.75F), true);
    public static final Block CALCITE_BRICK_SLAB = register("calcite_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.CALCITE).mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.75F, 0.75F), true);
    public static final Block CALCITE_BRICK_WALL = register("calcite_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.CALCITE).mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.75F, 0.75F), true);
    public static final Block CRACKED_CALCITE_BRICKS = register("cracked_calcite_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.CALCITE).mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.75F, 0.75F), true);
    public static final Block CRACKED_CALCITE_BRICK_STAIRS = register("cracked_calcite_brick_stairs", settings -> new StairsBlock(ModBlocks.CRACKED_CALCITE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.CALCITE).mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.75F, 0.75F), true);
    public static final Block CRACKED_CALCITE_BRICK_SLAB = register("cracked_calcite_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.CALCITE).mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.75F, 0.75F), true);
    public static final Block CRACKED_CALCITE_BRICK_WALL = register("cracked_calcite_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.CALCITE).mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.75F, 0.75F), true);
    // Chiseled Calcite
    public static final Block POLISHED_SANDSTONE_WALL = register("polished_sandstone_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block SANDSTONE_BRICK_STAIRS = register("sandstone_brick_stairs", settings -> new StairsBlock(Blocks.CUT_SANDSTONE.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block SANDSTONE_BRICK_WALL = register("sandstone_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block CRACKED_SANDSTONE_BRICKS = register("cracked_sandstone_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block CRACKED_SANDSTONE_BRICK_STAIRS = register("cracked_sandstone_brick_stairs", settings -> new StairsBlock(ModBlocks.CRACKED_SANDSTONE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block CRACKED_SANDSTONE_BRICK_SLAB = register("cracked_sandstone_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block CRACKED_SANDSTONE_BRICK_WALL = register("cracked_sandstone_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block POLISHED_RED_SANDSTONE_WALL = register("polished_red_sandstone_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.ORANGE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block RED_SANDSTONE_BRICK_STAIRS = register("red_sandstone_brick_stairs", settings -> new StairsBlock(Blocks.CUT_RED_SANDSTONE.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.ORANGE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block RED_SANDSTONE_BRICK_WALL = register("red_sandstone_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.ORANGE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block CRACKED_RED_SANDSTONE_BRICKS = register("cracked_red_sandstone_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.ORANGE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block CRACKED_RED_SANDSTONE_BRICK_STAIRS = register("cracked_red_sandstone_brick_stairs", settings -> new StairsBlock(ModBlocks.CRACKED_RED_SANDSTONE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.ORANGE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block CRACKED_RED_SANDSTONE_BRICK_SLAB = register("cracked_red_sandstone_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.ORANGE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block CRACKED_RED_SANDSTONE_BRICK_WALL = register("cracked_red_sandstone_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.ORANGE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block PRISMARINE_BRICK_WALL = register("prismarine_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.DIAMOND_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block DARK_PRISMARINE_WALL = register("dark_prismarine_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.DIAMOND_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F), true);
    public static final Block CRACKED_BLACKSTONE_BRICK_STAIRS = register("cracked_blackstone_brick_stairs", settings -> new StairsBlock(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block CRACKED_BLACKSTONE_BRICK_SLAB = register("cracked_blackstone_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block CRACKED_BLACKSTONE_BRICK_WALL = register("cracked_blackstone_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block POLISHED_BASALT = register("polished_basalt", Block::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block POLISHED_BASALT_STAIRS = register("polished_basalt_stairs", settings -> new StairsBlock(ModBlocks.POLISHED_BASALT.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block POLISHED_BASALT_SLAB = register("polished_basalt_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block POLISHED_BASALT_WALL = register("polished_basalt_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block BASALT_BRICKS = register("basalt_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block BASALT_BRICK_STAIRS = register("basalt_brick_stairs", settings -> new StairsBlock(ModBlocks.BASALT_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block BASALT_BRICK_SLAB = register("basalt_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block BASALT_BRICK_WALL = register("basalt_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block CRACKED_BASALT_BRICKS = register("cracked_basalt_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block CRACKED_BASALT_BRICK_STAIRS = register("cracked_basalt_brick_stairs", settings -> new StairsBlock(ModBlocks.CRACKED_BASALT_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block CRACKED_BASALT_BRICK_SLAB = register("cracked_basalt_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block CRACKED_BASALT_BRICK_WALL = register("cracked_basalt_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block BASALT_TILES = register("basalt_tiles", Block::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block BASALT_TILE_STAIRS = register("basalt_tile_stairs", settings -> new StairsBlock(ModBlocks.BASALT_TILES.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block BASALT_TILE_SLAB = register("basalt_tile_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block BASALT_TILE_WALL = register("basalt_tile_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block CRACKED_BASALT_TILES = register("cracked_basalt_tiles", Block::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block CRACKED_BASALT_TILE_STAIRS = register("cracked_basalt_tile_stairs", settings -> new StairsBlock(ModBlocks.CRACKED_BASALT_TILES.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block CRACKED_BASALT_TILE_SLAB = register("cracked_basalt_tile_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block CRACKED_BASALT_TILE_WALL = register("cracked_basalt_tile_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block CHISELED_BASALT = register("chiseled_basalt", Block::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block ENGRAVED_BASALT = register("engraved_basalt", Block::new, Block.Settings.create().sounds(BlockSoundGroup.BASALT).mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block POLISHED_QUARTZ_WALL = register("polished_quartz_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block QUARTZ_BRICK_STAIRS = register("quartz_brick_stairs", settings -> new StairsBlock(Blocks.QUARTZ_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block QUARTZ_BRICK_SLAB = register("quartz_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    public static final Block QUARTZ_BRICK_WALL = register("quartz_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F, 0.8F), true);
    // Cracked Quartz Bricks
    // Cracked Quartz Brick Stairs
    // Cracked Quartz Brick Slab
    // Cracked Quartz Brick Wall
    // Polished End Stone
    // Polished End Stone Stairs
    // Polished End Stone Slab
    // Polished End Stone Wall
    // Cracked End Stone Bricks
    // Cracked End Stone Brick Stairs
    // Cracked End Stone Brick Slab
    // Cracked End Stone Brick Wall
    // Chiseled End Stone
    public static final Block PURPUR_WALL = register("purpur_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.MAGENTA).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F), true);
    public static final Block CHISELED_PURPUR = register("chiseled_purpur", Block::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.MAGENTA).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F), true);
    // Cracked Bricks
    // Cracked Brick Stairs
    // Cracked Brick Slab
    // Cracked Brick Wall
    public static final Block CRACKED_RED_NETHER_BRICKS = register("cracked_red_nether_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.NETHER_BRICKS).mapColor(MapColor.DARK_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.25F, 4.2F), true);
    public static final Block CRACKED_NETHER_BRICK_STAIRS = register("cracked_nether_brick_stairs", settings -> new StairsBlock(Blocks.CRACKED_NETHER_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.NETHER_BRICKS).mapColor(MapColor.DARK_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F), true);
    public static final Block CRACKED_NETHER_BRICK_SLAB = register("cracked_nether_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.NETHER_BRICKS).mapColor(MapColor.DARK_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F), true);
    public static final Block CRACKED_NETHER_BRICK_WALL = register("cracked_nether_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.NETHER_BRICKS).mapColor(MapColor.DARK_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F), true);
    public static final Block CRACKED_RED_NETHER_BRICK_STAIRS = register("cracked_red_nether_brick_stairs", settings -> new StairsBlock(ModBlocks.CRACKED_RED_NETHER_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.NETHER_BRICKS).mapColor(MapColor.DARK_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F), true);
    public static final Block CRACKED_RED_NETHER_BRICK_SLAB = register("cracked_red_nether_brick_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.NETHER_BRICKS).mapColor(MapColor.DARK_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F), true);
    public static final Block CRACKED_RED_NETHER_BRICK_WALL = register("cracked_red_nether_brick_wall", WallBlock::new, Block.Settings.create().sounds(BlockSoundGroup.NETHER_BRICKS).mapColor(MapColor.DARK_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F), true);
    public static final Block CHISELED_RED_NETHER_BRICKS = register("chiseled_red_nether_bricks", Block::new, Block.Settings.create().sounds(BlockSoundGroup.NETHER_BRICKS).mapColor(MapColor.DARK_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F), true);
    // Cracked Resin Bricks
    // Cracked Resin Brick Stairs
    // Cracked Resin Brick Slab
    // Cracked Resin Brick Wall
    // Polished Packed Mud
    // Polished Packed Mud Stairs
    // Polished Packed Mud Slab
    // Polished Packed Mud Wall
    // Cracked Packed Mud Bricks
    // Cracked Packed Mud Brick Stairs
    // Cracked Packed Mud Brick Slab
    // Cracked Packed Mud Brick Wall
    // Chiseled Packed Mud
    public static final Block POLISHED_END_STONE = register("polished_end_stone", Block::new, Block.Settings.create().sounds(ModSounds.END_STONE).mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block POLISHED_END_STONE_STAIRS = register("polished_end_stone_stairs", settings -> new StairsBlock(ModBlocks.POLISHED_END_STONE.getDefaultState(), settings), Block.Settings.create().sounds(ModSounds.END_STONE).mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block POLISHED_END_STONE_SLAB = register("polished_end_stone_slab", SlabBlock::new, Block.Settings.create().sounds(ModSounds.END_STONE).mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block POLISHED_END_STONE_WALL = register("polished_end_stone_wall", WallBlock::new, Block.Settings.create().sounds(ModSounds.END_STONE).mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block KURODITE = register("kurodite", Block::new, Block.Settings.create().sounds(ModSounds.KURODITE).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block POLISHED_KURODITE = register("polished_kurodite", Block::new, Block.Settings.create().sounds(ModSounds.KURODITE).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block POLISHED_KURODITE_STAIRS = register("polished_kurodite_stairs", settings -> new StairsBlock(ModBlocks.POLISHED_KURODITE.getDefaultState(), settings), Block.Settings.create().sounds(ModSounds.KURODITE).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block POLISHED_KURODITE_SLAB = register("polished_kurodite_slab", SlabBlock::new, Block.Settings.create().sounds(ModSounds.KURODITE).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block POLISHED_KURODITE_WALL = register("polished_kurodite_wall", WallBlock::new, Block.Settings.create().sounds(ModSounds.KURODITE).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block KURODITE_BRICKS = register("kurodite_bricks", Block::new, Block.Settings.create().sounds(ModSounds.KURODITE_BRICKS).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block KURODITE_BRICK_STAIRS = register("kurodite_brick_stairs", settings -> new StairsBlock(ModBlocks.KURODITE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(ModSounds.KURODITE_BRICKS).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block KURODITE_BRICK_SLAB = register("kurodite_brick_slab", SlabBlock::new, Block.Settings.create().sounds(ModSounds.KURODITE_BRICKS).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block KURODITE_BRICK_WALL = register("kurodite_brick_wall", WallBlock::new, Block.Settings.create().sounds(ModSounds.KURODITE_BRICKS).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block CRACKED_KURODITE_BRICKS = register("cracked_kurodite_bricks", Block::new, Block.Settings.create().sounds(ModSounds.KURODITE_BRICKS).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block CRACKED_KURODITE_BRICK_STAIRS = register("cracked_kurodite_brick_stairs", settings -> new StairsBlock(ModBlocks.CRACKED_KURODITE_BRICKS.getDefaultState(), settings), Block.Settings.create().sounds(ModSounds.KURODITE_BRICKS).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block CRACKED_KURODITE_BRICK_SLAB = register("cracked_kurodite_brick_slab", SlabBlock::new, Block.Settings.create().sounds(ModSounds.KURODITE_BRICKS).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block CRACKED_KURODITE_BRICK_WALL = register("cracked_kurodite_brick_wall", WallBlock::new, Block.Settings.create().sounds(ModSounds.KURODITE_BRICKS).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);
    public static final Block CHISELED_KURODITE = register("chiseled_kurodite", Block::new, Block.Settings.create().sounds(ModSounds.KURODITE).mapColor(MapColor.DEEPSLATE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 9.0F), true);

    // METAL VARIANT BLOCKS (17)

    public static final Block CUT_IRON = register("cut_iron", Block::new, Block.Settings.create().sounds(BlockSoundGroup.IRON).mapColor(MapColor.IRON_GRAY).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresTool().strength(5.0F, 6.0F), true);
    public static final Block CUT_IRON_STAIRS = register("cut_iron_stairs", settings -> new StairsBlock(ModBlocks.CUT_IRON.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.IRON).mapColor(MapColor.IRON_GRAY).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresTool().strength(5.0F, 6.0F), true);
    public static final Block CUT_IRON_SLAB = register("cut_iron_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.IRON).mapColor(MapColor.IRON_GRAY).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresTool().strength(5.0F, 6.0F), true);
    public static final Block CHISELED_IRON = register("chiseled_iron", Block::new, Block.Settings.create().sounds(BlockSoundGroup.IRON).mapColor(MapColor.IRON_GRAY).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresTool().strength(5.0F, 6.0F), true);
    public static final Block IRON_GRATE = register("iron_grate", GrateBlock::new, Block.Settings.create().sounds(BlockSoundGroup.COPPER_GRATE).mapColor(MapColor.IRON_GRAY).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresTool().strength(5.0f, 6.0f).nonOpaque(), true);
    public static final Block IRON_BULB = register("iron_bulb", BulbBlock::new, Block.Settings.create().luminance(state -> state.get(BulbBlock.LIT) ? 15 : 0).sounds(BlockSoundGroup.COPPER_BULB).mapColor(MapColor.IRON_GRAY).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresTool().strength(5.0f, 6.0f), true);
    public static final Block CUT_GOLD = register("cut_gold", Block::new, Block.Settings.create().sounds(BlockSoundGroup.METAL).mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL).requiresTool().strength(3.0F, 6.0F), true);
    public static final Block CUT_GOLD_STAIRS = register("cut_gold_stairs", settings -> new StairsBlock(ModBlocks.CUT_GOLD.getDefaultState(), settings), Block.Settings.create().sounds(BlockSoundGroup.METAL).mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL).requiresTool().strength(3.0F, 6.0F), true);
    public static final Block CUT_GOLD_SLAB = register("cut_gold_slab", SlabBlock::new, Block.Settings.create().sounds(BlockSoundGroup.METAL).mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL).requiresTool().strength(3.0F, 6.0F), true);
    public static final Block CHISELED_GOLD = register("chiseled_gold", Block::new, Block.Settings.create().sounds(BlockSoundGroup.METAL).mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL).requiresTool().strength(3.0F, 6.0F), true);
    public static final Block GOLD_GRATE = register("gold_grate", GrateBlock::new, Block.Settings.create().sounds(BlockSoundGroup.COPPER_GRATE).mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL).nonOpaque().requiresTool().strength(3.0f, 6.0f), true);
    public static final Block GOLD_BULB = register("gold_bulb", BulbBlock::new, Block.Settings.create().luminance(state -> state.get(BulbBlock.LIT) ? 15 : 0).sounds(BlockSoundGroup.COPPER_BULB).mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL).requiresTool().strength(3.0f, 6.0f), true);
    public static final Block GOLD_DOOR = register("gold_door", settings -> new DoorBlock(BlockSetType.COPPER, settings), Block.Settings.create().mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL).requiresTool().nonOpaque().pistonBehavior(PistonBehavior.DESTROY).strength(3.0F, 6.0F), true);
    public static final Block GOLD_TRAPDOOR = register("gold_trapdoor", (settings) -> new TrapdoorBlock(BlockSetType.COPPER, settings), Block.Settings.create().mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL).requiresTool().nonOpaque().pistonBehavior(PistonBehavior.DESTROY).strength(3.0F, 6.0F), true);
    public static final Block GOLD_BARS = register("gold_bars", PaneBlock::new, Block.Settings.create().sounds(BlockSoundGroup.METAL).nonOpaque().requiresTool().strength(3.0F, 6.0F), true);
    public static final Block GOLD_CHAIN = register("gold_chain", ChainBlock::new, Block.Settings.create().sounds(BlockSoundGroup.CHAIN).nonOpaque().solid().requiresTool().strength(3.0F, 6.0F), true);
    public static final Block GOLD_LANTERN = register("gold_lantern", LanternBlock::new, Block.Settings.create().luminance(state -> 15).sounds(BlockSoundGroup.LANTERN).mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL).pistonBehavior(PistonBehavior.DESTROY).requiresTool().nonOpaque().strength(3.5f, 3.5f), true);

    public static final Block RAW_SHADOLINE_BLOCK = register("raw_shadoline_block", Block::new, Block.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.TEAL).requiresTool().strength(5.0F, 6.0F), true);
    public static final Block SHADOLINE_BLOCK = register("shadoline_block", Block::new, Block.Settings.create().sounds(ModSounds.SHADOLINE).mapColor(MapColor.TEAL).requiresTool().strength(5.0F, 6.0F), true);
    public static final Block CUT_SHADOLINE = register("cut_shadoline", Block::new, Block.Settings.create().sounds(ModSounds.SHADOLINE).mapColor(MapColor.TEAL).requiresTool().strength(5.0F, 6.0F), true);
    public static final Block CUT_SHADOLINE_STAIRS = register("cut_shadoline_stairs", settings -> new StairsBlock(ModBlocks.CUT_SHADOLINE.getDefaultState(), settings), Block.Settings.create().sounds(ModSounds.SHADOLINE).mapColor(MapColor.TEAL).requiresTool().strength(5.0F, 6.0F), true);
    public static final Block CUT_SHADOLINE_SLAB = register("cut_shadoline_slab", SlabBlock::new, Block.Settings.create().sounds(ModSounds.SHADOLINE).mapColor(MapColor.TEAL).requiresTool().strength(5.0F, 6.0F), true);
    public static final Block CHISELED_SHADOLINE = register("chiseled_shadoline", Block::new, Block.Settings.create().sounds(ModSounds.SHADOLINE).mapColor(MapColor.TEAL).requiresTool().strength(5.0F, 6.0F), true);
    public static final Block SHADOLINE_GRATE = register("shadoline_grate", GrateBlock::new, Block.Settings.create().sounds(BlockSoundGroup.COPPER_GRATE).mapColor(MapColor.TEAL).nonOpaque().requiresTool().strength(5.0f, 6.0f), true);
    public static final Block SHADOLINE_BULB = register("shadoline_bulb", BulbBlock::new, Block.Settings.create().luminance(state -> state.get(BulbBlock.LIT) ? 15 : 0).sounds(BlockSoundGroup.COPPER_BULB).mapColor(MapColor.TEAL).requiresTool().strength(5.0f, 6.0f), true);
    public static final Block SHADOLINE_DOOR = register("shadoline_door", settings -> new DoorBlock(BlockSetType.COPPER, settings), Block.Settings.create().mapColor(MapColor.TEAL).requiresTool().nonOpaque().pistonBehavior(PistonBehavior.DESTROY).strength(5.0F, 6.0F), true);
    public static final Block SHADOLINE_TRAPDOOR = register("shadoline_trapdoor", (settings) -> new TrapdoorBlock(BlockSetType.COPPER, settings), Block.Settings.create().mapColor(MapColor.TEAL).requiresTool().nonOpaque().pistonBehavior(PistonBehavior.DESTROY).strength(5.0F, 6.0F), true);
    public static final Block SHADOLINE_BARS = register("shadoline_bars", PaneBlock::new, Block.Settings.create().sounds(ModSounds.SHADOLINE).nonOpaque().requiresTool().strength(5.0F, 6.0F), true);
    public static final Block SHADOLINE_CHAIN = register("shadoline_chain", ChainBlock::new, Block.Settings.create().sounds(BlockSoundGroup.CHAIN).nonOpaque().solid().requiresTool().strength(5.0F, 6.0F), true);
    public static final Block SHADOLINE_LANTERN = register("shadoline_lantern", LanternBlock::new, Block.Settings.create().luminance(state -> 15).sounds(BlockSoundGroup.LANTERN).mapColor(MapColor.TEAL).pistonBehavior(PistonBehavior.DESTROY).requiresTool().nonOpaque().strength(3.5f, 3.5f), true);

    // DECORATIVE BLOCKS

    public static final Block SOUL_GLASS = register("soul_glass", TransparentBlock::new, Block.Settings.create().sounds(BlockSoundGroup.GLASS).instrument(NoteBlockInstrument.HAT).strength(0.6F, 0.6F).nonOpaque().allowsSpawning(Blocks::never).solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never), true);

    public static void initialize() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register((itemgroup) -> {
            itemgroup.add(POLISHED_STONE);
            itemgroup.add(POLISHED_STONE_STAIRS);
            itemgroup.add(POLISHED_STONE_SLAB);
            itemgroup.add(POLISHED_STONE_WALL);
            itemgroup.add(CRACKED_STONE_BRICK_STAIRS);
            itemgroup.add(CRACKED_STONE_BRICK_SLAB);
            itemgroup.add(CRACKED_STONE_BRICK_WALL);
            itemgroup.add(SMOOTH_STONE_STAIRS);
            itemgroup.add(SMOOTH_STONE_WALL);
            itemgroup.add(POLISHED_ANDESITE_WALL);
            itemgroup.add(ANDESITE_BRICKS);
            itemgroup.add(ANDESITE_BRICK_STAIRS);
            itemgroup.add(ANDESITE_BRICK_SLAB);
            itemgroup.add(ANDESITE_BRICK_WALL);
            itemgroup.add(CRACKED_ANDESITE_BRICKS);
            itemgroup.add(CRACKED_ANDESITE_BRICK_STAIRS);
            itemgroup.add(CRACKED_ANDESITE_BRICK_SLAB);
            itemgroup.add(CRACKED_ANDESITE_BRICK_WALL);
            itemgroup.add(CHISELED_ANDESITE);
            itemgroup.add(POLISHED_DIORITE_WALL);
            itemgroup.add(DIORITE_BRICKS);
            itemgroup.add(DIORITE_BRICK_STAIRS);
            itemgroup.add(DIORITE_BRICK_SLAB);
            itemgroup.add(DIORITE_BRICK_WALL);
            itemgroup.add(CRACKED_DIORITE_BRICKS);
            itemgroup.add(CRACKED_DIORITE_BRICK_STAIRS);
            itemgroup.add(CRACKED_DIORITE_BRICK_SLAB);
            itemgroup.add(CRACKED_DIORITE_BRICK_WALL);
            itemgroup.add(CHISELED_DIORITE);
            itemgroup.add(POLISHED_GRANITE_WALL);
            itemgroup.add(GRANITE_BRICKS);
            itemgroup.add(GRANITE_BRICK_STAIRS);
            itemgroup.add(GRANITE_BRICK_SLAB);
            itemgroup.add(GRANITE_BRICK_WALL);
            itemgroup.add(CRACKED_GRANITE_BRICKS);
            itemgroup.add(CRACKED_GRANITE_BRICK_STAIRS);
            itemgroup.add(CRACKED_GRANITE_BRICK_SLAB);
            itemgroup.add(CRACKED_GRANITE_BRICK_WALL);
            itemgroup.add(CHISELED_GRANITE);
            itemgroup.add(CRACKED_DEEPSLATE_BRICK_STAIRS);
            itemgroup.add(CRACKED_DEEPSLATE_BRICK_SLAB);
            itemgroup.add(CRACKED_DEEPSLATE_BRICK_WALL);
            itemgroup.add(CRACKED_DEEPSLATE_TILE_STAIRS);
            itemgroup.add(CRACKED_DEEPSLATE_TILE_SLAB);
            itemgroup.add(CRACKED_DEEPSLATE_TILE_WALL);
            itemgroup.add(CRACKED_TUFF_BRICKS);
            itemgroup.add(CRACKED_TUFF_BRICK_STAIRS);
            itemgroup.add(CRACKED_TUFF_BRICK_SLAB);
            itemgroup.add(CRACKED_TUFF_BRICK_WALL);
            itemgroup.add(POLISHED_DRIPSTONE);
            itemgroup.add(POLISHED_DRIPSTONE_STAIRS);
            itemgroup.add(POLISHED_DRIPSTONE_SLAB);
            itemgroup.add(POLISHED_DRIPSTONE_WALL);
            itemgroup.add(DRIPSTONE_BRICKS);
            itemgroup.add(DRIPSTONE_BRICK_STAIRS);
            itemgroup.add(DRIPSTONE_BRICK_SLAB);
            itemgroup.add(DRIPSTONE_BRICK_WALL);
            itemgroup.add(CRACKED_DRIPSTONE_BRICKS);
            itemgroup.add(CRACKED_DRIPSTONE_BRICK_STAIRS);
            itemgroup.add(CRACKED_DRIPSTONE_BRICK_SLAB);
            itemgroup.add(CRACKED_DRIPSTONE_BRICK_WALL);
            itemgroup.add(CHISELED_DRIPSTONE);
            itemgroup.add(CALCITE_BRICKS);
            itemgroup.add(CALCITE_BRICK_STAIRS);
            itemgroup.add(CALCITE_BRICK_SLAB);
            itemgroup.add(CALCITE_BRICK_WALL);
            itemgroup.add(CRACKED_CALCITE_BRICKS);
            itemgroup.add(CRACKED_CALCITE_BRICK_STAIRS);
            itemgroup.add(CRACKED_CALCITE_BRICK_SLAB);
            itemgroup.add(CRACKED_CALCITE_BRICK_WALL);
            itemgroup.add(POLISHED_SANDSTONE_WALL);
            itemgroup.add(SANDSTONE_BRICK_STAIRS);
            itemgroup.add(SANDSTONE_BRICK_WALL);
            itemgroup.add(CRACKED_SANDSTONE_BRICKS);
            itemgroup.add(CRACKED_SANDSTONE_BRICK_STAIRS);
            itemgroup.add(CRACKED_SANDSTONE_BRICK_SLAB);
            itemgroup.add(CRACKED_SANDSTONE_BRICK_WALL);
            itemgroup.add(POLISHED_RED_SANDSTONE_WALL);
            itemgroup.add(RED_SANDSTONE_BRICK_STAIRS);
            itemgroup.add(RED_SANDSTONE_BRICK_WALL);
            itemgroup.add(CRACKED_RED_SANDSTONE_BRICKS);
            itemgroup.add(CRACKED_RED_SANDSTONE_BRICK_STAIRS);
            itemgroup.add(CRACKED_RED_SANDSTONE_BRICK_SLAB);
            itemgroup.add(CRACKED_RED_SANDSTONE_BRICK_WALL);
            itemgroup.add(PRISMARINE_BRICK_WALL);
            itemgroup.add(DARK_PRISMARINE_WALL);
            itemgroup.add(CRACKED_BLACKSTONE_BRICK_STAIRS);
            itemgroup.add(CRACKED_BLACKSTONE_BRICK_SLAB);
            itemgroup.add(CRACKED_BLACKSTONE_BRICK_WALL);
            itemgroup.add(POLISHED_BASALT);
            itemgroup.add(POLISHED_BASALT_STAIRS);
            itemgroup.add(POLISHED_BASALT_SLAB);
            itemgroup.add(POLISHED_BASALT_WALL);
            itemgroup.add(BASALT_BRICKS);
            itemgroup.add(BASALT_BRICK_STAIRS);
            itemgroup.add(BASALT_BRICK_SLAB);
            itemgroup.add(BASALT_BRICK_WALL);
            itemgroup.add(CRACKED_BASALT_BRICKS);
            itemgroup.add(CRACKED_BASALT_BRICK_STAIRS);
            itemgroup.add(CRACKED_BASALT_BRICK_SLAB);
            itemgroup.add(CRACKED_BASALT_BRICK_WALL);
            itemgroup.add(BASALT_TILES);
            itemgroup.add(BASALT_TILE_STAIRS);
            itemgroup.add(BASALT_TILE_SLAB);
            itemgroup.add(BASALT_TILE_WALL);
            itemgroup.add(CRACKED_BASALT_TILES);
            itemgroup.add(CRACKED_BASALT_TILE_STAIRS);
            itemgroup.add(CRACKED_BASALT_TILE_SLAB);
            itemgroup.add(CRACKED_BASALT_TILE_WALL);
            itemgroup.add(CHISELED_BASALT);
            itemgroup.add(ENGRAVED_BASALT);
            itemgroup.add(POLISHED_QUARTZ_WALL);
            itemgroup.add(QUARTZ_BRICK_STAIRS);
            itemgroup.add(QUARTZ_BRICK_SLAB);
            itemgroup.add(QUARTZ_BRICK_WALL);
            itemgroup.add(PURPUR_WALL);
            itemgroup.add(CHISELED_PURPUR);
            itemgroup.add(CRACKED_NETHER_BRICK_STAIRS);
            itemgroup.add(CRACKED_NETHER_BRICK_SLAB);
            itemgroup.add(CRACKED_NETHER_BRICK_WALL);
            itemgroup.add(CRACKED_RED_NETHER_BRICKS);
            itemgroup.add(CRACKED_RED_NETHER_BRICK_STAIRS);
            itemgroup.add(CRACKED_RED_NETHER_BRICK_SLAB);
            itemgroup.add(CRACKED_RED_NETHER_BRICK_WALL);
            itemgroup.add(CHISELED_RED_NETHER_BRICKS);
            itemgroup.add(POLISHED_END_STONE);
            itemgroup.add(POLISHED_END_STONE_STAIRS);
            itemgroup.add(POLISHED_END_STONE_SLAB);
            itemgroup.add(POLISHED_END_STONE_WALL);
            itemgroup.add(POLISHED_KURODITE);
            itemgroup.add(POLISHED_KURODITE_STAIRS);
            itemgroup.add(POLISHED_KURODITE_SLAB);
            itemgroup.add(POLISHED_KURODITE_WALL);
            itemgroup.add(KURODITE_BRICKS);
            itemgroup.add(KURODITE_BRICK_STAIRS);
            itemgroup.add(KURODITE_BRICK_SLAB);
            itemgroup.add(KURODITE_BRICK_WALL);
            itemgroup.add(CRACKED_KURODITE_BRICKS);
            itemgroup.add(CRACKED_KURODITE_BRICK_STAIRS);
            itemgroup.add(CRACKED_KURODITE_BRICK_SLAB);
            itemgroup.add(CRACKED_KURODITE_BRICK_WALL);
            itemgroup.add(CHISELED_KURODITE);
            itemgroup.add(PALLID_MAGNIA);
            itemgroup.add(UMBRAL_MAGNIA);
            itemgroup.add(CUT_IRON);
            itemgroup.add(CUT_IRON_STAIRS);
            itemgroup.add(CUT_IRON_SLAB);
            itemgroup.add(CHISELED_IRON);
            itemgroup.add(IRON_GRATE);
            itemgroup.add(IRON_BULB);
            itemgroup.add(CUT_GOLD);
            itemgroup.add(CUT_GOLD_STAIRS);
            itemgroup.add(CUT_GOLD_SLAB);
            itemgroup.add(CHISELED_GOLD);
            itemgroup.add(GOLD_GRATE);
            itemgroup.add(GOLD_BULB);
            itemgroup.add(GOLD_DOOR);
            itemgroup.add(GOLD_TRAPDOOR);
            itemgroup.add(GOLD_BARS);
            itemgroup.add(GOLD_CHAIN);
            itemgroup.add(RAW_SHADOLINE_BLOCK);
            itemgroup.add(SHADOLINE_BLOCK);
            itemgroup.add(CUT_SHADOLINE);
            itemgroup.add(CUT_SHADOLINE_STAIRS);
            itemgroup.add(CUT_SHADOLINE_SLAB);
            itemgroup.add(CHISELED_SHADOLINE);
            itemgroup.add(SHADOLINE_GRATE);
            itemgroup.add(SHADOLINE_BULB);
            itemgroup.add(SHADOLINE_DOOR);
            itemgroup.add(SHADOLINE_TRAPDOOR);
            itemgroup.add(SHADOLINE_BARS);
            itemgroup.add(SHADOLINE_CHAIN);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register((itemgroup) -> {
            itemgroup.add(SILT);
            itemgroup.add(SOULSLATE);
            itemgroup.add(NETHERRACK_GLOWSTONE_ORE);
            itemgroup.add(KURODITE);
            itemgroup.add(WILD_WHEAT);
            itemgroup.add(WILD_CARROT);
            itemgroup.add(WILD_POTATO);
            itemgroup.add(WILD_BEETROOT);
            itemgroup.add(PONTEDERIA);
            itemgroup.add(HIBISCUS);
            itemgroup.add(POKER);
            itemgroup.add(BLACK_STINKHORN);
            itemgroup.add(WHITE_STINKHORN);
            itemgroup.add(BUSY_LIZZIE);
            itemgroup.add(GOLDENROD);
            itemgroup.add(BARLEY);
            itemgroup.add(SEA_OATS);
            itemgroup.add(PAMPAS);
            itemgroup.add(ICY_IRIS);
            itemgroup.add(TALL_ICY_IRIS);
            itemgroup.add(CLOVERS);
            itemgroup.add(EMBER_SPROUTS);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemgroup) -> {
            itemgroup.add(IRON_BULB);
            itemgroup.add(GOLD_BULB);
            itemgroup.add(SHADOLINE_BULB);
            itemgroup.add(GOLD_LANTERN);
            itemgroup.add(SHADOLINE_LANTERN);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register((itemgroup) -> {
            itemgroup.add(IRON_BULB);
            itemgroup.add(GOLD_BULB);
            itemgroup.add(SHADOLINE_LANTERN);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register((itemgroup) -> {
            itemgroup.add(SOUL_GLASS);
        });

    }

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean registerItem) {

        RegistryKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        if (registerItem) {RegistryKey<Item> itemKey = keyOfItem(name);BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey());Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);

    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, name));
    }

}