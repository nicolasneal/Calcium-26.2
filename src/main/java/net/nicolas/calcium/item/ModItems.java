package net.nicolas.calcium.item;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumables;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.fluid.ModFluids;
import net.nicolas.calcium.item.custom.EctoplasmBucketItem;
import net.nicolas.calcium.sound.ModSounds;

import java.util.function.Function;

import static net.minecraft.world.item.Items.BOWL;

public class ModItems {

    public static final String MOD_ID = "calcium";

    // INGREDIENTS: MOB DROPS (7)

    public static final Item HIDE = register("hide", Item::new, new Item.Properties().stacksTo(64));
    public static final Item FUR = register("fur", Item::new, new Item.Properties().stacksTo(64));
    public static final Item PIXIE_DUST = register("pixie_dust", Item::new, new Item.Properties().stacksTo(64));
    public static final Item FOUR_LEAF_CLOVER = register("four_leaf_clover", Item::new, new Item.Properties().stacksTo(64));
    public static final Item DOLPHIN_FIN = register("dolphin_fin", Item::new, new Item.Properties().stacksTo(64));
    public static final Item GHAST_TENTACLE = register("ghast_tentacle", Item::new, new Item.Properties().stacksTo(64));
    public static final Item WARDEN_HEART = register("warden_heart", Item::new, new Item.Properties().stacksTo(64));

    // INGREDIENTS: RESOURCES (9)

    public static final Item WOODEN_ROD = register("wooden_rod", Item::new, new Item.Properties().stacksTo(64));
    public static final Item TABLET = register("tablet", Item::new, new Item.Properties().stacksTo(64));
    public static final Item FLOUR = register("flour", Item::new, new Item.Properties().stacksTo(64));
    public static final Item DOUGH = register("dough", Item::new, new Item.Properties().stacksTo(64));
    public static final Item BATTER = register("batter", Item::new, new Item.Properties().stacksTo(64));
    public static final Item PUMPKIN_SLICE = register("pumpkin_slice", Item::new, new Item.Properties().stacksTo(64));
    public static final Item RAW_SHADOLINE = register("raw_shadoline", Item::new, new Item.Properties().stacksTo(64));
    public static final Item SHADOLINE_NUGGET = register("shadoline_nugget", Item::new, new Item.Properties().stacksTo(64));
    public static final Item SHADOLINE_INGOT = register("shadoline_ingot", Item::new, new Item.Properties().stacksTo(64));

    // INGREDIENTS: BANNER PATTERNS (1)

    public static final Item CRESCENT_BANNER_PATTERN = register("crescent_banner_pattern", Item::new, new Item.Properties().stacksTo(64).delayedComponent(DataComponents.PROVIDES_BANNER_PATTERNS, context -> context.getOrThrow(TagKey.create(Registries.BANNER_PATTERN, Identifier.fromNamespaceAndPath(MOD_ID, "pattern_item/crescent")))));

    // FOOD AND DRINK (15)

    public static final Item CHEVAL = register("cheval", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.CHEVAL));
    public static final Item COOKED_CHEVAL = register("cooked_cheval", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.COOKED_CHEVAL));
    public static final Item BEAR = register("bear", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.BEAR));
    public static final Item COOKED_BEAR = register("cooked_bear", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.COOKED_BEAR));
    public static final Item CAMEL = register("camel", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.CAMEL));
    public static final Item COOKED_CAMEL = register("cooked_camel", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.COOKED_CAMEL));
    public static final Item CHEVON = register("chevon", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.CHEVON));
    public static final Item COOKED_CHEVON = register("cooked_chevon", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.COOKED_CHEVON));
    public static final Item FROG = register("frog", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.FROG));
    public static final Item COOKED_FROG = register("cooked_frog", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.COOKED_FROG));
    public static final Item TENTACLES = register("tentacles", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.TENTACLES));
    public static final Item COOKED_TENTACLES = register("cooked_tentacles", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.COOKED_TENTACLES));
    public static final Item CHOCOLATE = register("chocolate", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.CHOCOLATE));
    public static final Item CHORUS_CAKE_ROLL = register("chorus_cake_roll", settings -> new BlockItem(ModBlocks.CHORUS_CAKE_ROLL, settings), new Item.Properties().useBlockDescriptionPrefix().stacksTo(64));
    public static final Item WATER_BOWL = register("water_bowl", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.WATER_BOWL).component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK).usingConvertsTo(BOWL).craftRemainder(BOWL));

    // TOOLS & UTILITY (8)

    public static final Item COPPER_COIN = register("copper_coin", Item::new, new Item.Properties().stacksTo(64));
    public static final Item IRON_COIN = register("iron_coin", Item::new, new Item.Properties().stacksTo(64));
    public static final Item GOLD_COIN = register("gold_coin", Item::new, new Item.Properties().stacksTo(64));
    public static final Item NETHERITE_COIN = register("netherite_coin", Item::new, new Item.Properties().stacksTo(64));
    public static final Item ECTOPLASM_BUCKET = register("ectoplasm_bucket", settings -> new EctoplasmBucketItem(ModFluids.ECTOPLASM_STILL, settings), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(64));
    public static final Item MUSIC_DISC_BLISS = register("music_disc_bliss", Item::new, new Item.Properties().jukeboxPlayable(ModSounds.BLISS).stacksTo(64));
    public static final Item MUSIC_DISC_DECAY = register("music_disc_decay", Item::new, new Item.Properties().jukeboxPlayable(ModSounds.DECAY).stacksTo(64));
    public static final Item MUSIC_DISC_GLARE = register("music_disc_glare", Item::new, new Item.Properties().jukeboxPlayable(ModSounds.GLARE).stacksTo(64));

    private static <T extends Item> T register(String name, Function<Item.Properties, T> constructor, Item.Properties settings) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, name));
        T item = constructor.apply(settings.setId(key));
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    public static void initialize() {

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register((itemgroup) -> {
            itemgroup.accept(WOODEN_ROD);
            itemgroup.accept(TABLET);
            itemgroup.accept(HIDE);
            itemgroup.accept(FUR);
            itemgroup.accept(PIXIE_DUST);
            itemgroup.accept(FOUR_LEAF_CLOVER);
            itemgroup.accept(DOLPHIN_FIN);
            itemgroup.accept(GHAST_TENTACLE);
            itemgroup.accept(WARDEN_HEART);
            itemgroup.accept(FLOUR);
            itemgroup.accept(DOUGH);
            itemgroup.accept(BATTER);
            itemgroup.accept(PUMPKIN_SLICE);
            itemgroup.accept(RAW_SHADOLINE);
            itemgroup.accept(SHADOLINE_NUGGET);
            itemgroup.accept(SHADOLINE_INGOT);
            itemgroup.accept(CRESCENT_BANNER_PATTERN);
            itemgroup.getDisplayStacks().removeIf(stack -> stack.is(Items.RABBIT_HIDE));
            itemgroup.getSearchTabStacks().removeIf(stack -> stack.is(Items.RABBIT_HIDE));
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(itemgroup -> {
            itemgroup.accept(CHEVAL);
            itemgroup.accept(COOKED_CHEVAL);
            itemgroup.accept(BEAR);
            itemgroup.accept(COOKED_BEAR);
            itemgroup.accept(CAMEL);
            itemgroup.accept(COOKED_CAMEL);
            itemgroup.accept(CHEVON);
            itemgroup.accept(COOKED_CHEVON);
            itemgroup.accept(FROG);
            itemgroup.accept(COOKED_FROG);
            itemgroup.accept(TENTACLES);
            itemgroup.accept(COOKED_TENTACLES);
            itemgroup.accept(CHOCOLATE);
            itemgroup.accept(CHORUS_CAKE_ROLL);
            itemgroup.accept(WATER_BOWL);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(itemgroup -> {
            itemgroup.accept(COPPER_COIN);
            itemgroup.accept(IRON_COIN);
            itemgroup.accept(GOLD_COIN);
            itemgroup.accept(NETHERITE_COIN);
            itemgroup.accept(ECTOPLASM_BUCKET);
            itemgroup.accept(MUSIC_DISC_BLISS);
            itemgroup.accept(MUSIC_DISC_DECAY);
            itemgroup.accept(MUSIC_DISC_GLARE);
        });

    }
}