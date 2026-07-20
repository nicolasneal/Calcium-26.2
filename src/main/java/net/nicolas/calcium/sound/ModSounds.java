package net.nicolas.calcium.sound;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.nicolas.calcium.mixin.accessors.AbstractBlockAccessor;

public class ModSounds {

    public static void initialize() {

        ((AbstractBlockAccessor) Blocks.BRICKS).setSoundGroup(SoundType.NETHER_BRICKS);
        ((AbstractBlockAccessor) Blocks.BRICK_STAIRS).setSoundGroup(SoundType.NETHER_BRICKS);
        ((AbstractBlockAccessor) Blocks.BRICK_SLAB).setSoundGroup(SoundType.NETHER_BRICKS);
        ((AbstractBlockAccessor) Blocks.BRICK_WALL).setSoundGroup(SoundType.NETHER_BRICKS);

        ((AbstractBlockAccessor) Blocks.END_STONE).setSoundGroup(ModSoundGroups.END_STONE);
        ((AbstractBlockAccessor) Blocks.END_STONE_BRICKS).setSoundGroup(ModSoundGroups.END_STONE);
        ((AbstractBlockAccessor) Blocks.END_STONE_BRICK_STAIRS).setSoundGroup(ModSoundGroups.END_STONE);
        ((AbstractBlockAccessor) Blocks.END_STONE_BRICK_SLAB).setSoundGroup(ModSoundGroups.END_STONE);
        ((AbstractBlockAccessor) Blocks.END_STONE_BRICK_WALL).setSoundGroup(ModSoundGroups.END_STONE);

        ((AbstractBlockAccessor) Blocks.PURPUR_BLOCK).setSoundGroup(ModSoundGroups.PURPUR);
        ((AbstractBlockAccessor) Blocks.PURPUR_STAIRS).setSoundGroup(ModSoundGroups.PURPUR);
        ((AbstractBlockAccessor) Blocks.PURPUR_SLAB).setSoundGroup(ModSoundGroups.PURPUR);
        ((AbstractBlockAccessor) Blocks.PURPUR_PILLAR).setSoundGroup(ModSoundGroups.PURPUR);

        ((AbstractBlockAccessor) Blocks.DIRT).setSoundGroup(SoundType.ROOTED_DIRT);
        ((AbstractBlockAccessor) Blocks.COARSE_DIRT).setSoundGroup(SoundType.ROOTED_DIRT);
        ((AbstractBlockAccessor) Blocks.PODZOL).setSoundGroup(SoundType.ROOTED_DIRT);
        ((AbstractBlockAccessor) Blocks.FARMLAND).setSoundGroup(SoundType.ROOTED_DIRT);
        ((AbstractBlockAccessor) Blocks.DIRT_PATH).setSoundGroup(SoundType.ROOTED_DIRT);
        ((AbstractBlockAccessor) Blocks.CLAY).setSoundGroup(SoundType.ROOTED_DIRT);

        ((AbstractBlockAccessor) Blocks.OAK_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);
        ((AbstractBlockAccessor) Blocks.SPRUCE_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);
        ((AbstractBlockAccessor) Blocks.BIRCH_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);
        ((AbstractBlockAccessor) Blocks.JUNGLE_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);
        ((AbstractBlockAccessor) Blocks.ACACIA_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);
        ((AbstractBlockAccessor) Blocks.DARK_OAK_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);
        ((AbstractBlockAccessor) Blocks.MANGROVE_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);
        ((AbstractBlockAccessor) Blocks.PALE_OAK_LEAVES).setSoundGroup(SoundType.CHERRY_LEAVES);

        ((AbstractBlockAccessor) Blocks.OAK_SAPLING).setSoundGroup(SoundType.CHERRY_SAPLING);
        ((AbstractBlockAccessor) Blocks.SPRUCE_SAPLING).setSoundGroup(SoundType.CHERRY_SAPLING);
        ((AbstractBlockAccessor) Blocks.BIRCH_SAPLING).setSoundGroup(SoundType.CHERRY_SAPLING);
        ((AbstractBlockAccessor) Blocks.JUNGLE_SAPLING).setSoundGroup(SoundType.CHERRY_SAPLING);
        ((AbstractBlockAccessor) Blocks.ACACIA_SAPLING).setSoundGroup(SoundType.CHERRY_SAPLING);
        ((AbstractBlockAccessor) Blocks.DARK_OAK_SAPLING).setSoundGroup(SoundType.CHERRY_SAPLING);
        ((AbstractBlockAccessor) Blocks.MANGROVE_PROPAGULE).setSoundGroup(SoundType.CHERRY_SAPLING);
        ((AbstractBlockAccessor) Blocks.PALE_OAK_SAPLING).setSoundGroup(SoundType.CHERRY_SAPLING);

        ((AbstractBlockAccessor) Blocks.RED_MUSHROOM_BLOCK).setSoundGroup(SoundType.WART_BLOCK);
        ((AbstractBlockAccessor) Blocks.BROWN_MUSHROOM_BLOCK).setSoundGroup(SoundType.WART_BLOCK);
        ((AbstractBlockAccessor) Blocks.MUSHROOM_STEM).setSoundGroup(SoundType.WART_BLOCK);

        ((AbstractBlockAccessor) Blocks.RAW_COPPER_BLOCK).setSoundGroup(SoundType.STONE);

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
        ((AbstractBlockAccessor) Blocks.PINK_TULIP).setSoundGroup(SoundType.AZALEA);
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

        ((AbstractBlockAccessor) Blocks.ANVIL).setSoundGroup(SoundType.METAL);
        ((AbstractBlockAccessor) Blocks.CHIPPED_ANVIL).setSoundGroup(SoundType.METAL);
        ((AbstractBlockAccessor) Blocks.DAMAGED_ANVIL).setSoundGroup(SoundType.METAL);
        ((AbstractBlockAccessor) Blocks.CAULDRON).setSoundGroup(SoundType.METAL);
        ((AbstractBlockAccessor) Blocks.COAL_BLOCK).setSoundGroup(SoundType.METAL);
        ((AbstractBlockAccessor) Blocks.LAPIS_BLOCK).setSoundGroup(SoundType.METAL);

        ((AbstractBlockAccessor) Blocks.CHORUS_FLOWER).setSoundGroup(ModSoundGroups.CHORUS_FLOWER);
        ((AbstractBlockAccessor) Blocks.CHORUS_PLANT).setSoundGroup(ModSoundGroups.CHORUS_STALK);

        ((AbstractBlockAccessor) Blocks.END_GATEWAY).setSoundGroup(ModSoundGroups.END_GATEWAY);
        ((AbstractBlockAccessor) Blocks.END_ROD).setSoundGroup(ModSoundGroups.END_ROD);

        ((AbstractBlockAccessor) Blocks.BLACKSTONE).setSoundGroup(ModSoundGroups.BLACKSTONE);
        ((AbstractBlockAccessor) Blocks.BLACKSTONE_STAIRS).setSoundGroup(ModSoundGroups.BLACKSTONE);
        ((AbstractBlockAccessor) Blocks.BLACKSTONE_SLAB).setSoundGroup(ModSoundGroups.BLACKSTONE);
        ((AbstractBlockAccessor) Blocks.BLACKSTONE_WALL).setSoundGroup(ModSoundGroups.BLACKSTONE);
        ((AbstractBlockAccessor) Blocks.CHISELED_POLISHED_BLACKSTONE).setSoundGroup(ModSoundGroups.BLACKSTONE);
        ((AbstractBlockAccessor) Blocks.POLISHED_BLACKSTONE).setSoundGroup(ModSoundGroups.BLACKSTONE);
        ((AbstractBlockAccessor) Blocks.POLISHED_BLACKSTONE_STAIRS).setSoundGroup(ModSoundGroups.BLACKSTONE);
        ((AbstractBlockAccessor) Blocks.POLISHED_BLACKSTONE_SLAB).setSoundGroup(ModSoundGroups.BLACKSTONE);
        ((AbstractBlockAccessor) Blocks.POLISHED_BLACKSTONE_WALL).setSoundGroup(ModSoundGroups.BLACKSTONE);
        ((AbstractBlockAccessor) Blocks.POLISHED_BLACKSTONE_BRICKS).setSoundGroup(ModSoundGroups.BLACKSTONE);
        ((AbstractBlockAccessor) Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS).setSoundGroup(ModSoundGroups.BLACKSTONE);
        ((AbstractBlockAccessor) Blocks.POLISHED_BLACKSTONE_BRICK_SLAB).setSoundGroup(ModSoundGroups.BLACKSTONE);
        ((AbstractBlockAccessor) Blocks.POLISHED_BLACKSTONE_BRICK_WALL).setSoundGroup(ModSoundGroups.BLACKSTONE);
        ((AbstractBlockAccessor) Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS).setSoundGroup(ModSoundGroups.BLACKSTONE);
        ((AbstractBlockAccessor) Blocks.QUARTZ_BLOCK).setSoundGroup(ModSoundGroups.QUARTZ);
        ((AbstractBlockAccessor) Blocks.SMOOTH_QUARTZ).setSoundGroup(ModSoundGroups.QUARTZ);
        ((AbstractBlockAccessor) Blocks.SMOOTH_QUARTZ_STAIRS).setSoundGroup(ModSoundGroups.QUARTZ);
        ((AbstractBlockAccessor) Blocks.SMOOTH_QUARTZ_SLAB).setSoundGroup(ModSoundGroups.QUARTZ);
        ((AbstractBlockAccessor) Blocks.QUARTZ_BRICKS).setSoundGroup(ModSoundGroups.QUARTZ);
        ((AbstractBlockAccessor) Blocks.CHISELED_QUARTZ_BLOCK).setSoundGroup(ModSoundGroups.QUARTZ);
        ((AbstractBlockAccessor) Blocks.QUARTZ_PILLAR).setSoundGroup(ModSoundGroups.QUARTZ);

        ((AbstractBlockAccessor) Blocks.OBSIDIAN).setSoundGroup(ModSoundGroups.OBSIDIAN);
        ((AbstractBlockAccessor) Blocks.CRYING_OBSIDIAN).setSoundGroup(ModSoundGroups.OBSIDIAN);
        ((AbstractBlockAccessor) Blocks.MAGMA_BLOCK).setSoundGroup(ModSoundGroups.MAGMA);
        ((AbstractBlockAccessor) Blocks.GLOWSTONE).setSoundGroup(ModSoundGroups.GLOWSTONE);

    }

}