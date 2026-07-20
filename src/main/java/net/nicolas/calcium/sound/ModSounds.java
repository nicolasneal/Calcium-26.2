package net.nicolas.calcium.sound;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.block.SoundType;

public class ModSounds {

    public static final SoundEvent SOULSLATE_PLACE = register("block.soulslate.place");
    public static final SoundEvent SOULSLATE_BREAK = register("block.soulslate.break");
    public static final SoundEvent SOULSLATE_STEP = register("block.soulslate.step");
    public static final SoundType SOULSLATE = new SoundType(1.0F, 1.0F, SOULSLATE_BREAK, SOULSLATE_STEP, SOULSLATE_PLACE, SOULSLATE_STEP, SOULSLATE_STEP);

    public static final SoundEvent END_STONE_BREAK = register("block.end_stone.break");
    public static final SoundEvent END_STONE_STEP = register("block.end_stone.step");
    public static final SoundType END_STONE = new SoundType(1.0F, 1.0F, END_STONE_BREAK, END_STONE_STEP, END_STONE_BREAK, END_STONE_STEP, END_STONE_STEP);

    public static final SoundEvent MIRESTONE_PLACE = register("block.mirestone.place");
    public static final SoundEvent MIRESTONE_BREAK = register("block.mirestone.break");
    public static final SoundEvent MIRESTONE_STEP = register("block.mirestone.step");
    public static final SoundType MIRESTONE = new SoundType(1.0F, 1.0F, MIRESTONE_BREAK, MIRESTONE_STEP, MIRESTONE_PLACE, MIRESTONE_STEP, MIRESTONE_STEP);

    public static final SoundEvent KURODITE_BREAK = register("block.kurodite.break");
    public static final SoundEvent KURODITE_STEP = register("block.kurodite.step");
    public static final SoundType KURODITE = new SoundType(0.6F, 1.0F, KURODITE_BREAK, KURODITE_STEP, KURODITE_BREAK, KURODITE_STEP, KURODITE_STEP);

    public static final SoundEvent KURODITE_BRICKS_BREAK = register("block.kurodite_bricks.break");
    public static final SoundEvent KURODITE_BRICKS_STEP = register("block.kurodite_bricks.step");
    public static final SoundType KURODITE_BRICKS = new SoundType(0.6F, 1.0F, KURODITE_BRICKS_BREAK, KURODITE_BRICKS_STEP, KURODITE_BRICKS_BREAK, KURODITE_BRICKS_STEP, KURODITE_BRICKS_STEP);

    public static final SoundEvent VERADITE_BREAK = register("block.veradite.break");
    public static final SoundEvent VERADITE_STEP = register("block.veradite.step");
    public static final SoundType VERADITE = new SoundType(0.6F, 1.0F, VERADITE_BREAK, VERADITE_STEP, VERADITE_BREAK, VERADITE_STEP, VERADITE_STEP);

    public static final SoundEvent VERADITE_BRICKS_BREAK = register("block.veradite_bricks.break");
    public static final SoundEvent VERADITE_BRICKS_STEP = register("block.veradite_bricks.step");
    public static final SoundType VERADITE_BRICKS = new SoundType(0.6F, 1.0F, VERADITE_BRICKS_BREAK, VERADITE_BRICKS_STEP, VERADITE_BRICKS_BREAK, VERADITE_BRICKS_STEP, VERADITE_BRICKS_STEP);

    public static final SoundEvent PALLID_MAGNIA_PLACE = register("block.pallid_magnia.place");
    public static final SoundEvent PALLID_MAGNIA_BREAK = register("block.pallid_magnia.break");
    public static final SoundEvent PALLID_MAGNIA_STEP = register("block.pallid_magnia.step");
    public static final SoundType PALLID_MAGNIA = new SoundType(1.0F, 1.0F, PALLID_MAGNIA_BREAK, PALLID_MAGNIA_STEP, PALLID_MAGNIA_PLACE, PALLID_MAGNIA_STEP, PALLID_MAGNIA_STEP);

    public static final SoundEvent UMBRAL_MAGNIA_PLACE = register("block.umbral_magnia.place");
    public static final SoundEvent UMBRAL_MAGNIA_BREAK = register("block.umbral_magnia.break");
    public static final SoundEvent UMBRAL_MAGNIA_STEP = register("block.umbral_magnia.step");
    public static final SoundType UMBRAL_MAGNIA = new SoundType(1.0F, 1.0F, UMBRAL_MAGNIA_BREAK, UMBRAL_MAGNIA_STEP, UMBRAL_MAGNIA_PLACE, UMBRAL_MAGNIA_STEP, UMBRAL_MAGNIA_STEP);

    public static final SoundEvent SHADOLINE_BREAK = register("block.shadoline.break");
    public static final SoundEvent SHADOLINE_PLACE = register("block.shadoline.place");
    public static final SoundEvent SHADOLINE_STEP = register("block.shadoline.step");
    public static final SoundType SHADOLINE = new SoundType(1.0F, 1.0F, SHADOLINE_BREAK, SHADOLINE_STEP, SHADOLINE_PLACE, SHADOLINE_STEP, SHADOLINE_STEP);

    public static final SoundEvent END_STONE_SHADOLINE_ORE_BREAK = register("block.end_stone_shadoline_ore.break");
    public static final SoundEvent END_STONE_SHADOLINE_ORE_STEP = register("block.end_stone_shadoline_ore.step");
    public static final SoundType END_STONE_SHADOLINE_ORE = new SoundType(1.0F, 1.0F, END_STONE_SHADOLINE_ORE_BREAK, END_STONE_SHADOLINE_ORE_STEP, END_STONE_SHADOLINE_ORE_BREAK, END_STONE_SHADOLINE_ORE_STEP, END_STONE_SHADOLINE_ORE_STEP);

    public static final SoundEvent MIRESTONE_SHADOLINE_ORE_PLACE = register("block.mirestone_shadoline_ore.place");
    public static final SoundEvent MIRESTONE_SHADOLINE_ORE_BREAK = register("block.mirestone_shadoline_ore.break");
    public static final SoundEvent MIRESTONE_SHADOLINE_ORE_STEP = register("block.mirestone_shadoline_ore.step");
    public static final SoundType MIRESTONE_SHADOLINE_ORE = new SoundType(1.0F, 1.0F, MIRESTONE_SHADOLINE_ORE_BREAK, MIRESTONE_SHADOLINE_ORE_STEP, MIRESTONE_SHADOLINE_ORE_PLACE, MIRESTONE_SHADOLINE_ORE_STEP, MIRESTONE_SHADOLINE_ORE_STEP);

    public static final SoundEvent END_GROWTH_BREAK = register("block.end_growth.break");
    public static final SoundType END_GROWTH = new SoundType(1.0F, 1.0F, END_GROWTH_BREAK, END_GROWTH_BREAK, END_GROWTH_BREAK, END_GROWTH_BREAK, END_GROWTH_BREAK);

    public static final SoundEvent BLINKVINE_PLACE = register("block.blinkvine.place");
    public static final SoundType BLINKVINE = new SoundType(1.0F, 1.0F, BLINKVINE_PLACE, BLINKVINE_PLACE, BLINKVINE_PLACE, BLINKVINE_PLACE, BLINKVINE_PLACE);

    public static final SoundEvent END_ROD_PLACE = register("block.end_rod.place");
    public static final SoundType END_ROD = new SoundType(1.0F, 1.0F, END_ROD_PLACE, END_ROD_PLACE, END_ROD_PLACE, END_ROD_PLACE, END_ROD_PLACE);

    public static final SoundEvent CHORUS_PLANKS_BREAK = register("block.chorus_planks.break");
    public static final SoundEvent CHORUS_PLANKS_STEP = register("block.chorus_planks.step");
    public static final SoundType CHORUS_PLANKS = new SoundType(1.0F, 1.0F, CHORUS_PLANKS_BREAK, CHORUS_PLANKS_STEP, CHORUS_PLANKS_BREAK, CHORUS_PLANKS_STEP, CHORUS_PLANKS_STEP);

    public static final SoundEvent CHORUS_FENCE_GATE_OPEN = register("block.chorus_fence_gate.open");

    public static final SoundEvent CHORUS_DOOR_OPEN = register("block.chorus_door.open");
    public static final SoundEvent CHORUS_DOOR_CLOSE = register("block.chorus_door.close");

    public static final SoundEvent CHORUS_TRAPDOOR_OPEN = register("block.chorus_trapdoor.open");
    public static final SoundEvent CHORUS_TRAPDOOR_CLOSE = register("block.chorus_trapdoor.close");

    public static final SoundEvent CHORUS_HANGING_SIGN_BREAK = register("block.chorus_hanging_sign.break");
    public static final SoundEvent CHORUS_HANGING_SIGN_STEP = register("block.chorus_hanging_sign.step");
    public static final SoundType CHORUS_HANGING_SIGN = new SoundType(1.0F, 1.0F, CHORUS_HANGING_SIGN_BREAK, CHORUS_HANGING_SIGN_STEP, CHORUS_HANGING_SIGN_BREAK, CHORUS_HANGING_SIGN_STEP, CHORUS_HANGING_SIGN_STEP);

    public static final SoundEvent CAKE_ROLL_EAT = register("block.cake_roll.eat");
    public static final SoundEvent CAKE_ROLL_PLACE = register("block.cake_roll.place");
    public static final SoundEvent CAKE_ROLL_STEP = register("block.cake_roll.step");
    public static final SoundType CAKE_ROLL = new SoundType(1.0F, 1.0F, CAKE_ROLL_PLACE, CAKE_ROLL_STEP, CAKE_ROLL_PLACE, CAKE_ROLL_STEP, CAKE_ROLL_STEP);

    public static final SoundEvent CHORUS_FLOWER_BREAK = register("block.chorus_flower.break");
    public static final SoundEvent CHORUS_FLOWER_IDLE = register("block.chorus_flower.idle");
    public static final SoundType CHORUS_FLOWER = new SoundType(1.0F, 1.0F, CHORUS_FLOWER_BREAK, CHORUS_FLOWER_BREAK, CHORUS_FLOWER_BREAK, CHORUS_FLOWER_BREAK, CHORUS_FLOWER_BREAK);

    public static final SoundEvent CHORUS_STALK_BREAK = register("block.chorus_stalk.break");
    public static final SoundEvent CHORUS_STALK_STEP = register("block.chorus_stalk.step");
    public static final SoundType CHORUS_STALK = new SoundType(1.0F, 1.0F, CHORUS_STALK_BREAK, CHORUS_STALK_STEP, CHORUS_STALK_BREAK, CHORUS_STALK_STEP, CHORUS_STALK_STEP);

    public static final SoundEvent PURPUR_BREAK = register("block.purpur.break");
    public static final SoundEvent PURPUR_STEP = register("block.purpur.step");
    public static final SoundType PURPUR = new SoundType(1.0F, 1.0F, PURPUR_BREAK, PURPUR_STEP, PURPUR_BREAK, PURPUR_STEP, PURPUR_STEP);

    public static final SoundEvent END_GATEWAY_BREAK = register("block.end_gateway.break");
    public static final SoundType END_GATEWAY = new SoundType(1.0F, 1.0F, END_GATEWAY_BREAK, END_GATEWAY_BREAK, END_GATEWAY_BREAK, END_GATEWAY_BREAK, END_GATEWAY_BREAK);

    public static final SoundEvent FLOWERING_WISP_BREAK = register("block.flowering_wisp.break");
    public static final SoundType FLOWERING_WISP = new SoundType(1.0F, 1.0F, FLOWERING_WISP_BREAK, FLOWERING_WISP_BREAK, FLOWERING_WISP_BREAK, FLOWERING_WISP_BREAK, FLOWERING_WISP_BREAK);

    public static final SoundEvent WISP_BREAK = register("block.wisp.break");
    public static final SoundType WISP = new SoundType(1.0F, 1.0F, WISP_BREAK, WISP_BREAK, WISP_BREAK, WISP_BREAK, WISP_BREAK);

    public static final SoundEvent BLACKSTONE_BREAK = register("block.blackstone.break");
    public static final SoundEvent BLACKSTONE_STEP = register("block.blackstone.step");
    public static final SoundType BLACKSTONE = new SoundType(0.8F, 1.0F, BLACKSTONE_BREAK, BLACKSTONE_STEP, BLACKSTONE_BREAK, BLACKSTONE_STEP, BLACKSTONE_STEP);

    public static final SoundEvent GLOWSTONE_BREAK = register("block.glowstone.break");
    public static final SoundEvent GLOWSTONE_PLACE = register("block.glowstone.place");
    public static final SoundType GLOWSTONE = new SoundType(1.0F, 1.0F, GLOWSTONE_BREAK, GLOWSTONE_PLACE, GLOWSTONE_PLACE, GLOWSTONE_PLACE, GLOWSTONE_PLACE);

    public static final SoundEvent MAGMA_BREAK = register("block.magma.break");
    public static final SoundEvent MAGMA_STEP = register("block.magma.step");
    public static final SoundType MAGMA = new SoundType(1.0F, 1.0F, MAGMA_BREAK, MAGMA_STEP, MAGMA_BREAK, MAGMA_STEP, MAGMA_STEP);

    public static final SoundEvent OBSIDIAN_BREAK = register("block.obsidian.break");
    public static final SoundEvent OBSIDIAN_PLACE = register("block.obsidian.place");
    public static final SoundEvent OBSIDIAN_STEP = register("block.obsidian.step");
    public static final SoundType OBSIDIAN = new SoundType(1.0F, 1.0F, OBSIDIAN_BREAK, OBSIDIAN_STEP, OBSIDIAN_PLACE, OBSIDIAN_STEP, OBSIDIAN_STEP);

    public static final SoundEvent QUARTZ_BREAK = register("block.quartz.break");
    public static final SoundEvent QUARTZ_PLACE = register("block.quartz.place");
    public static final SoundType QUARTZ = new SoundType(1.0F, 1.0F, QUARTZ_BREAK, QUARTZ_PLACE, QUARTZ_PLACE, QUARTZ_PLACE, QUARTZ_BREAK);

    public static final SoundEvent GOLD_CHAIN_BREAK = register("block.gold_chain.break");
    public static final SoundEvent GOLD_CHAIN_STEP = register("block.gold_chain.step");
    public static final SoundType GOLD_CHAIN = new SoundType(1.0F, 1.0F, GOLD_CHAIN_BREAK, GOLD_CHAIN_STEP, GOLD_CHAIN_BREAK, GOLD_CHAIN_STEP, GOLD_CHAIN_STEP);

    public static final SoundEvent SHADOLINE_CHAIN_BREAK = register("block.shadoline_chain.break");
    public static final SoundEvent SHADOLINE_CHAIN_STEP = register("block.shadoline_chain.step");
    public static final SoundType SHADOLINE_CHAIN = new SoundType(0.8F, 1.0F, SHADOLINE_CHAIN_BREAK, SHADOLINE_CHAIN_STEP, SHADOLINE_CHAIN_BREAK, SHADOLINE_CHAIN_STEP, SHADOLINE_CHAIN_STEP);

    // UTILITY

    public static final SoundEvent ECTOPLASM_BUCKET_EMPTY = register("item.bucket.empty_ectoplasm");
    public static final SoundEvent ECTOPLASM_BUCKET_FILL = register("item.bucket.fill_ectoplasm");
    public static final SoundEvent ECTOPLASM_AMBIENT = register("block.ectoplasm.whispering");

    public static final SoundEvent MONITOR_STATIC = register("block.monitor.static");
    public static final SoundEvent SIGNAL_CARD_INSERT = register("item.signal_card.insert");
    public static final SoundEvent SIGNAL_CARD_EJECT = register("item.signal_card.eject");

    // MUSIC DISCS

    public static final SoundEvent MUSIC_DISC_BLISS = register("music.jukebox.bliss");
    public static final SoundEvent MUSIC_DISC_DECAY = register("music.jukebox.decay");
    public static final SoundEvent MUSIC_DISC_GLARE = register("music.jukebox.glare");

    public static final ResourceKey<JukeboxSong> BLISS = ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath("calcium", "bliss"));
    public static final ResourceKey<JukeboxSong> DECAY = ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath("calcium", "decay"));
    public static final ResourceKey<JukeboxSong> GLARE = ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath("calcium", "glare"));

    private static SoundEvent register(String name) {
        Identifier id = Identifier.fromNamespaceAndPath("calcium", name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void initialize() {}

}