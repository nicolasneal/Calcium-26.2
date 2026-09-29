package net.nicolas.calcium.item;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Compostable;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.nicolas.calcium.block.ModBlocks;

import java.util.List;

    // This class applies the mod's composting efficiency values. The items in each efficiency tier are listed in
    // data/calcium/tags/item/compostable, and each tier uses the matching vanilla context int provider.

public class ModCompostables {

    private record Tier(TagKey<Item> tag, Compostable component) {

        private Tier(String name, ResourceKey<ContextIntProvider> chance) {
            this(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ModBlocks.MOD_ID, "compostable/" + name)), new Compostable(chance));
        }

    }

    private static final List<Tier> TIERS = List.of(
        new Tier("low", ContextIntProviders.COMPOSTABLE_LOW),
        new Tier("low_medium", ContextIntProviders.COMPOSTABLE_LOW_MEDIUM),
        new Tier("medium", ContextIntProviders.COMPOSTABLE_MEDIUM),
        new Tier("medium_high", ContextIntProviders.COMPOSTABLE_MEDIUM_HIGH),
        new Tier("always_add_one", ContextIntProviders.COMPOSTABLE_ALWAYS_ADD_ONE)
    );

    public static void initialize() {

        DefaultItemComponentEvents.MODIFY.register(context -> context.modify(item -> true, (builder, item) -> {
            Holder<Item> holder = BuiltInRegistries.ITEM.wrapAsHolder(item);
            for (Tier tier : TIERS) {
                if (holder.is(tier.tag())) {
                    builder.set(DataComponents.COMPOSTABLE, tier.component());
                }
            }
        }));

    }

}
