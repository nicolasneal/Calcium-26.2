package net.nicolas.calcium.item;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.nicolas.calcium.block.ModBlocks;

    // This class applies the mod's fuel efficiency values. Each burn time is defined by a context int provider in
    // data/calcium/context_int_provider/cooking, and the items that use it are listed in data/calcium/tags/item/fuel.

public class ModFuels {

    private static final int[] BURN_TIMES = {50, 100, 150, 200, 400, 600, 800, 1600, 2400, 6400, 12800};

    public static void initialize() {

        DefaultItemComponentEvents.MODIFY.register(context -> context.modify(item -> true, (builder, lookup, item) -> {
            HolderLookup.RegistryLookup<Item> items = lookup.lookupOrThrow(Registries.ITEM);
            for (int burnTime : BURN_TIMES) {
                TagKey<Item> tag = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ModBlocks.MOD_ID, "fuel/" + burnTime));
                if (items.get(tag).map(set -> set.contains(item.builtInRegistryHolder())).orElse(false)) {
                    ResourceKey<ContextIntProvider> provider = ResourceKey.create(Registries.CONTEXT_INT_PROVIDER, Identifier.fromNamespaceAndPath(ModBlocks.MOD_ID, "cooking/time_" + burnTime));
                    builder.set(DataComponents.COOKING_FUEL, new CookingFuel(provider, ContextFloatProviders.COOKING_DEFAULT_SPEED_MULTIPLIER));
                }
            }
        }));

    }

}
