package net.nicolas.calcium.item.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {

    public static final TagKey<Item> ENCHANTABLE = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("calcium", "enchantable"));
    public static final TagKey<Item> UNDERWATER_PLANTABLE = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("calcium", "underwater_plantable"));
    public static final TagKey<Item> SEA_COW_FEEDS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("calcium", "sea_cow_feeds"));

}