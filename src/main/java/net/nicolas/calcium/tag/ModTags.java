package net.nicolas.calcium.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {

    public static final TagKey<Item> ENCHANTABLE = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("calcium", "enchantable"));

}