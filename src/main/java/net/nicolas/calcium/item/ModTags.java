package net.nicolas.calcium.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

    // This class stores the mod's tag registrations

public class ModTags {

    // ITEM TAGS

    public static final TagKey<Item> ENCHANTABLE = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("calcium", "enchantable"));
    public static final TagKey<Item> UNDERWATER_PLANTABLE = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("calcium", "underwater_plantable"));
    public static final TagKey<Item> SEA_COW_FEEDS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("calcium", "sea_cow_feeds"));
    public static final TagKey<Item> SUNFISH_FEEDS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("calcium", "sunfish_feeds"));

    // BLOCK TAGS

    public static final TagKey<Block> END_PLANT_PLACEMENT = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("calcium", "end_plant_placement"));
    public static final TagKey<Block> NETHER_PLANT_PLACEMENT = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("calcium", "nether_plant_placement"));
    public static final TagKey<Block> GIANT_CLAM_ANCHOR = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("calcium", "giant_clam_anchor"));

}