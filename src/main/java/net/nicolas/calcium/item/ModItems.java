package net.nicolas.calcium.item;

import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.TypedEntityData;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.entity.ModEntityTypes;
import net.nicolas.calcium.item.custom.EctoplasmBucketItem;
import net.nicolas.calcium.item.custom.SignalCardItem;
import net.nicolas.calcium.sound.ModSoundGroups;

import java.util.function.Function;

import static net.minecraft.world.item.Items.BOWL;

public class ModItems {

    public static final String MOD_ID = "calcium";

    // BLOCKS (2)

    public static final Item CHORUS_SIGN = register("chorus_sign", settings -> new SignItem(ModBlocks.CHORUS_SIGN, ModBlocks.CHORUS_WALL_SIGN, settings), new Item.Properties().useBlockDescriptionPrefix().stacksTo(64));
    public static final Item CHORUS_HANGING_SIGN = register("chorus_hanging_sign", settings -> new HangingSignItem(ModBlocks.CHORUS_HANGING_SIGN, ModBlocks.CHORUS_WALL_HANGING_SIGN, settings), new Item.Properties().useBlockDescriptionPrefix().stacksTo(64));

    // INGREDIENTS (18)

    public static final Item RAW_SHADOLINE = register("raw_shadoline", Item::new, new Item.Properties().stacksTo(64));
    public static final Item NETHERITE_NUGGET = register("netherite_nugget", Item::new, new Item.Properties().stacksTo(64));
    public static final Item SHADOLINE_NUGGET = register("shadoline_nugget", Item::new, new Item.Properties().stacksTo(64));
    public static final Item SHADOLINE_INGOT = register("shadoline_ingot", Item::new, new Item.Properties().stacksTo(64));
    public static final Item WOODEN_ROD = register("wooden_rod", Item::new, new Item.Properties().stacksTo(64));
    public static final Item PUMPKIN_SLICE = register("pumpkin_slice", Item::new, new Item.Properties().stacksTo(64));
    public static final Item FLOUR = register("flour", Item::new, new Item.Properties().stacksTo(64));
    public static final Item DOUGH = register("dough", Item::new, new Item.Properties().stacksTo(64));
    public static final Item BATTER = register("batter", Item::new, new Item.Properties().stacksTo(64));

    public static final Item HIDE = register("hide", Item::new, new Item.Properties().stacksTo(64));
    public static final Item FUR = register("fur", Item::new, new Item.Properties().stacksTo(64));
    public static final Item PIXIE_DUST = register("pixie_dust", Item::new, new Item.Properties().stacksTo(64));
    public static final Item FOUR_LEAF_CLOVER = register("four_leaf_clover", Item::new, new Item.Properties().stacksTo(64));
    public static final Item DOLPHIN_FIN = register("dolphin_fin", Item::new, new Item.Properties().stacksTo(64));
    public static final Item GHAST_TENTACLE = register("ghast_tentacle", Item::new, new Item.Properties().stacksTo(64));
    public static final Item WARDEN_HEART = register("warden_heart", Item::new, new Item.Properties().stacksTo(64));

    public static final Item WATER_BOWL = register("water_bowl", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.WATER_BOWL).component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK).usingConvertsTo(BOWL).craftRemainder(BOWL));

    public static final Item CRESCENT_BANNER_PATTERN = register("crescent_banner_pattern", Item::new, new Item.Properties().stacksTo(64).delayedComponent(DataComponents.PROVIDES_BANNER_PATTERNS, context -> context.getOrThrow(TagKey.create(Registries.BANNER_PATTERN, Identifier.fromNamespaceAndPath(MOD_ID, "pattern_item/crescent")))));

    // FOOD AND DRINK (18)

    public static final Item FISH = register("fish", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.FISH));
    public static final Item COOKED_FISH = register("cooked_fish", Item::new, new Item.Properties().stacksTo(64).food(ModFoods.COOKED_FISH));
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
    public static final Item CHOCOLATE_CAKE = register("chocolate_cake", settings -> new BlockItem(ModBlocks.CHOCOLATE_CAKE, settings), new Item.Properties().useBlockDescriptionPrefix().stacksTo(64));
    public static final Item PUMPKIN_CAKE_ROLL = register("pumpkin_cake_roll", settings -> new BlockItem(ModBlocks.PUMPKIN_CAKE_ROLL, settings), new Item.Properties().useBlockDescriptionPrefix().stacksTo(64));
    public static final Item CHORUS_CAKE_ROLL = register("chorus_cake_roll", settings -> new BlockItem(ModBlocks.CHORUS_CAKE_ROLL, settings), new Item.Properties().useBlockDescriptionPrefix().stacksTo(64));

    // TOOLS & UTILITY (9)

    public static final Item COPPER_COIN = register("copper_coin", Item::new, new Item.Properties().stacksTo(64));
    public static final Item IRON_COIN = register("iron_coin", Item::new, new Item.Properties().stacksTo(64));
    public static final Item GOLD_COIN = register("gold_coin", Item::new, new Item.Properties().stacksTo(64));
    public static final Item NETHERITE_COIN = register("netherite_coin", Item::new, new Item.Properties().stacksTo(64));
    public static final Item ECTOPLASM_BUCKET = register("ectoplasm_bucket", settings -> new EctoplasmBucketItem(ModBlocks.ECTOPLASM_STILL, settings), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(64));
    public static final Item MUSIC_DISC_BLISS = register("music_disc_bliss", Item::new, new Item.Properties().jukeboxPlayable(ModSoundGroups.BLISS).stacksTo(64));
    public static final Item MUSIC_DISC_DECAY = register("music_disc_decay", Item::new, new Item.Properties().jukeboxPlayable(ModSoundGroups.DECAY).stacksTo(64));
    public static final Item MUSIC_DISC_GLARE = register("music_disc_glare", Item::new, new Item.Properties().jukeboxPlayable(ModSoundGroups.GLARE).stacksTo(64));
    public static final Item SIGNAL_CARD = register("signal_card", SignalCardItem::new, new Item.Properties().stacksTo(64));
    public static final DataComponentType<GlobalPos> LINKED_FEED = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "linked_feed"), DataComponentType.<GlobalPos>builder().persistent(GlobalPos.CODEC).networkSynchronized(GlobalPos.STREAM_CODEC).build());

    // SPAWN EGGS (2)

    public static final Item SEA_COW_SPAWN_EGG = register("sea_cow_spawn_egg", SpawnEggItem::new, new Item.Properties().component(DataComponents.ENTITY_DATA, TypedEntityData.of(ModEntityTypes.SEA_COW, new CompoundTag())).stacksTo(64));
    public static final Item GIANT_CLAM_SPAWN_EGG = register("giant_clam_spawn_egg", SpawnEggItem::new, new Item.Properties().component(DataComponents.ENTITY_DATA, TypedEntityData.of(ModEntityTypes.GIANT_CLAM, new CompoundTag())).stacksTo(64));

    public static void initialize() {}

    private static <T extends Item> T register(String name, Function<Item.Properties, T> constructor, Item.Properties settings) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, name));
        T item = constructor.apply(settings.setId(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

}