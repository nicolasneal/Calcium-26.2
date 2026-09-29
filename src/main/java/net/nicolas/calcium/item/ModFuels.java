package net.nicolas.calcium.item;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.nicolas.calcium.block.ModBlocks;

import java.util.List;
import java.util.stream.IntStream;

    // This class applies the mod's fuel efficiency values. Each burn time is defined by a context int provider in
    // data/calcium/context_int_provider/cooking, and the items that use it are listed in data/calcium/tags/item/fuel.

public class ModFuels {

    private record Fuel(TagKey<Item> tag, CookingFuel component) {}

    private static final List<Fuel> FUELS = IntStream.of(50, 100, 150, 200, 400, 600, 800, 1600, 2400, 6400, 12800).mapToObj(burnTime -> new Fuel(
        TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ModBlocks.MOD_ID, "fuel/" + burnTime)),
        new CookingFuel(ResourceKey.create(Registries.CONTEXT_INT_PROVIDER, Identifier.fromNamespaceAndPath(ModBlocks.MOD_ID, "cooking/time_" + burnTime)), ContextFloatProviders.COOKING_DEFAULT_SPEED_MULTIPLIER)
    )).toList();

    public static void initialize() {

        DefaultItemComponentEvents.MODIFY.register(context -> context.modify(item -> true, (builder, item) -> {
            Holder<Item> holder = BuiltInRegistries.ITEM.wrapAsHolder(item);
            if (holder.is(ItemTags.NON_FLAMMABLE_WOOD)) {
                builder.set(DataComponents.COOKING_FUEL, null);
                return;
            }
            for (Fuel fuel : FUELS) {
                if (holder.is(fuel.tag())) {
                    builder.set(DataComponents.COOKING_FUEL, fuel.component());
                }
            }
        }));

    }

}
