package net.nicolas.calcium.block.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {

    public static final TagKey<Block> END_PLANT_PLACEMENT = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("calcium", "end_plant_placement"));
    public static final TagKey<Block> NETHER_PLANT_PLACEMENT = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("calcium", "nether_plant_placement"));
    public static final TagKey<Block> GIANT_CLAM_ANCHOR = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("calcium", "giant_clam_anchor"));

}