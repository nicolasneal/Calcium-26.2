package net.nicolas.calcium.item;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Compostable;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.nicolas.calcium.block.ModBlocks;

import java.util.Map;

    // This class applies the mod's composting efficiency values. The items in each efficiency tier are listed in
    // data/calcium/tags/item/compostable, and each tier uses the matching vanilla context int provider.

public class ModCompostables {

    private static final Map<String, ResourceKey<ContextIntProvider>> TIERS = Map.of(
        "low", ContextIntProviders.COMPOSTABLE_LOW,
        "low_medium", ContextIntProviders.COMPOSTABLE_LOW_MEDIUM,
        "medium", ContextIntProviders.COMPOSTABLE_MEDIUM,
        "medium_high", ContextIntProviders.COMPOSTABLE_MEDIUM_HIGH,
        "always_add_one", ContextIntProviders.COMPOSTABLE_ALWAYS_ADD_ONE
    );

    public static void initialize() {

        DefaultItemComponentEvents.MODIFY.register(context -> context.modify(item -> true, (builder, lookup, item) -> {
            HolderLookup.RegistryLookup<Item> items = lookup.lookupOrThrow(Registries.ITEM);
            TIERS.forEach((tier, provider) -> {
                TagKey<Item> tag = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ModBlocks.MOD_ID, "compostable/" + tier));
                if (items.get(tag).map(set -> set.contains(item.builtInRegistryHolder())).orElse(false)) {
                    builder.set(DataComponents.COMPOSTABLE, new Compostable(provider));
                }
            });
        }));

    }

}