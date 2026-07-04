package net.nicolas.calcium.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.jukebox.JukeboxSongs;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.nicolas.calcium.fluid.ModFluids;
import net.nicolas.calcium.item.custom.EctoplasmBucketItem;
import net.nicolas.calcium.sound.ModSounds;

import java.util.function.Function;

import static net.minecraft.item.Items.BOWL;

public class ModItems {

    public static final String MOD_ID = "calcium";

    // INGREDIENTS (MOB DROPS)

    public static final Item HIDE = register("hide", Item::new, new Item.Settings().maxCount(64));
    public static final Item FUR = register("fur", Item::new, new Item.Settings().maxCount(64));
    public static final Item PIXIE_DUST = register("pixie_dust", Item::new, new Item.Settings().maxCount(64));
    public static final Item FOUR_LEAF_CLOVER = register("four_leaf_clover", Item::new, new Item.Settings().maxCount(64));
    public static final Item DOLPHIN_FIN = register("dolphin_fin", Item::new, new Item.Settings().maxCount(64));
    public static final Item GHAST_TENTACLE = register("ghast_tentacle", Item::new, new Item.Settings().maxCount(64));
    public static final Item WARDEN_HEART = register("warden_heart", Item::new, new Item.Settings().maxCount(64));

    // INGREDIENTS (RESOURCES)

    public static final Item WOODEN_ROD = register("wooden_rod", Item::new, new Item.Settings().maxCount(64));
    public static final Item TABLET = register("tablet", Item::new, new Item.Settings().maxCount(64));
    public static final Item FLOUR = register("flour", Item::new, new Item.Settings().maxCount(64));
    public static final Item DOUGH = register("dough", Item::new, new Item.Settings().maxCount(64));
    public static final Item COOKIE_DOUGH = register("cookie_dough", Item::new, new Item.Settings().maxCount(64));
    public static final Item CAKE_BATTER = register("cake_batter", Item::new, new Item.Settings().maxCount(64));
    public static final Item PUMPKIN_SLICE = register("pumpkin_slice", Item::new, new Item.Settings().maxCount(64));
    public static final Item SHADOLINE_NUGGET = register("shadoline_nugget", Item::new, new Item.Settings().maxCount(64));
    public static final Item SHADOLINE_INGOT = register("shadoline_ingot", Item::new, new Item.Settings().maxCount(64));

    // FOOD AND DRINK

    public static final Item CHEVAL = register("cheval", Item::new, new Item.Settings().maxCount(64).food(ModFoods.CHEVAL));
    public static final Item COOKED_CHEVAL = register("cooked_cheval", Item::new, new Item.Settings().maxCount(64).food(ModFoods.COOKED_CHEVAL));
    public static final Item BEAR = register("bear", Item::new, new Item.Settings().maxCount(64).food(ModFoods.BEAR));
    public static final Item COOKED_BEAR = register("cooked_bear", Item::new, new Item.Settings().maxCount(64).food(ModFoods.COOKED_BEAR));
    public static final Item CAMEL = register("camel", Item::new, new Item.Settings().maxCount(64).food(ModFoods.CAMEL));
    public static final Item COOKED_CAMEL = register("cooked_camel", Item::new, new Item.Settings().maxCount(64).food(ModFoods.COOKED_CAMEL));
    public static final Item CHEVON = register("chevon", Item::new, new Item.Settings().maxCount(64).food(ModFoods.CHEVON));
    public static final Item COOKED_CHEVON = register("cooked_chevon", Item::new, new Item.Settings().maxCount(64).food(ModFoods.COOKED_CHEVON));
    public static final Item FROG = register("frog", Item::new, new Item.Settings().maxCount(64).food(ModFoods.FROG));
    public static final Item COOKED_FROG = register("cooked_frog", Item::new, new Item.Settings().maxCount(64).food(ModFoods.COOKED_FROG));
    public static final Item TENTACLES = register("tentacles", Item::new, new Item.Settings().maxCount(64).food(ModFoods.TENTACLES));
    public static final Item COOKED_TENTACLES = register("cooked_tentacles", Item::new, new Item.Settings().maxCount(64).food(ModFoods.COOKED_TENTACLES));
    public static final Item CHOCOLATE = register("chocolate", Item::new, new Item.Settings().maxCount(64).food(ModFoods.CHOCOLATE));
    public static final Item WATER_BOWL = register("water_bowl", Item::new, new Item.Settings().maxCount(64).food(ModFoods.WATER_BOWL).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.DRINK).useRemainder(BOWL).recipeRemainder(BOWL));

    // TOOLS & UTILITY

    public static final Item COPPER_COIN = register("copper_coin", Item::new, new Item.Settings().maxCount(64));
    public static final Item IRON_COIN = register("iron_coin", Item::new, new Item.Settings().maxCount(64));
    public static final Item GOLD_COIN = register("gold_coin", Item::new, new Item.Settings().maxCount(64));
    public static final Item NETHERITE_COIN = register("netherite_coin", Item::new, new Item.Settings().maxCount(64));
    public static final Item ECTOPLASM_BUCKET = register("ectoplasm_bucket", settings -> new EctoplasmBucketItem(ModFluids.ECTOPLASM_STILL, settings), new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(64));
    public static final Item MUSIC_DISC_BLISS = register("music_disc_bliss", Item::new, new Item.Settings().jukeboxPlayable(ModSounds.BLISS).maxCount(64));
    public static final Item MUSIC_DISC_DECAY = register("music_disc_decay", Item::new, new Item.Settings().jukeboxPlayable(ModSounds.DECAY).maxCount(64));
    public static final Item MUSIC_DISC_GLARE = register("music_disc_glare", Item::new, new Item.Settings().jukeboxPlayable(ModSounds.GLARE).maxCount(64));

    private static <T extends Item> T register(String name, Function<Item.Settings, T> constructor, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, name));
        T item = constructor.apply(settings.registryKey(key));
        return Registry.register(Registries.ITEM, key, item);
    }

    public static void initialize() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemgroup) -> {
            itemgroup.add(WOODEN_ROD);
            itemgroup.add(TABLET);
            itemgroup.add(HIDE);
            itemgroup.add(FUR);
            itemgroup.add(PIXIE_DUST);
            itemgroup.add(FOUR_LEAF_CLOVER);
            itemgroup.add(DOLPHIN_FIN);
            itemgroup.add(GHAST_TENTACLE);
            itemgroup.add(WARDEN_HEART);
            itemgroup.add(FLOUR);
            itemgroup.add(DOUGH);
            itemgroup.add(COOKIE_DOUGH);
            itemgroup.add(CAKE_BATTER);
            itemgroup.add(PUMPKIN_SLICE);
            itemgroup.add(SHADOLINE_NUGGET);
            itemgroup.add(SHADOLINE_INGOT);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(itemgroup -> {
            itemgroup.add(CHEVAL);
            itemgroup.add(COOKED_CHEVAL);
            itemgroup.add(BEAR);
            itemgroup.add(COOKED_BEAR);
            itemgroup.add(CAMEL);
            itemgroup.add(COOKED_CAMEL);
            itemgroup.add(CHEVON);
            itemgroup.add(COOKED_CHEVON);
            itemgroup.add(FROG);
            itemgroup.add(COOKED_FROG);
            itemgroup.add(TENTACLES);
            itemgroup.add(COOKED_TENTACLES);
            itemgroup.add(CHOCOLATE);
            itemgroup.add(WATER_BOWL);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(itemgroup -> {
            itemgroup.add(COPPER_COIN);
            itemgroup.add(IRON_COIN);
            itemgroup.add(GOLD_COIN);
            itemgroup.add(NETHERITE_COIN);
            itemgroup.add(ECTOPLASM_BUCKET);
            itemgroup.add(MUSIC_DISC_BLISS);
            itemgroup.add(MUSIC_DISC_DECAY);
            itemgroup.add(MUSIC_DISC_GLARE);
        });

    }
}