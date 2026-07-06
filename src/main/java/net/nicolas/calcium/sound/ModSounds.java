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

    public static final SoundEvent ECTOPLASM_BUCKET_EMPTY = register("item.bucket.empty_ectoplasm");
    public static final SoundEvent ECTOPLASM_BUCKET_FILL = register("item.bucket.fill_ectoplasm");
    public static final SoundEvent ECTOPLASM_AMBIENT = register("block.ectoplasm.whispering");

    public static final SoundEvent SOULSLATE_BREAK = register("block.soulslate.break");
    public static final SoundEvent SOULSLATE_PLACE = register("block.soulslate.place");
    public static final SoundEvent SOULSLATE_STEP = register("block.soulslate.step");
    public static final SoundType SOULSLATE = new SoundType(1.0F, 1.0F, SOULSLATE_BREAK, SOULSLATE_STEP, SOULSLATE_PLACE, SOULSLATE_STEP, SOULSLATE_STEP);

    public static final SoundEvent END_STONE_BREAK = register("block.end_stone.break");
    public static final SoundEvent END_STONE_STEP = register("block.end_stone.step");
    public static final SoundType END_STONE = new SoundType(1.0F, 1.0F, END_STONE_BREAK, END_STONE_STEP, END_STONE_BREAK, END_STONE_STEP, END_STONE_STEP);
    
    public static final SoundEvent PALLID_MAGNIA_BREAK = register("block.pallid_magnia.break");
    public static final SoundEvent PALLID_MAGNIA_PLACE = register("block.pallid_magnia.place");
    public static final SoundEvent PALLID_MAGNIA_STEP = register("block.pallid_magnia.step");
    public static final SoundType PALLID_MAGNIA = new SoundType(1.0F, 1.0F, PALLID_MAGNIA_BREAK, PALLID_MAGNIA_STEP, PALLID_MAGNIA_PLACE, PALLID_MAGNIA_STEP, PALLID_MAGNIA_STEP);

    public static final SoundEvent UMBRAL_MAGNIA_BREAK = register("block.umbral_magnia.break");
    public static final SoundEvent UMBRAL_MAGNIA_PLACE = register("block.umbral_magnia.place");
    public static final SoundEvent UMBRAL_MAGNIA_STEP = register("block.umbral_magnia.step");
    public static final SoundType UMBRAL_MAGNIA = new SoundType(1.0F, 1.0F, UMBRAL_MAGNIA_BREAK, UMBRAL_MAGNIA_STEP, UMBRAL_MAGNIA_PLACE, UMBRAL_MAGNIA_STEP, UMBRAL_MAGNIA_STEP);

    public static final SoundEvent KURODITE_BREAK = register("block.kurodite.break");
    public static final SoundEvent KURODITE_STEP = register("block.kurodite.step");
    public static final SoundType KURODITE = new SoundType(0.6F, 1.0F, KURODITE_BREAK, KURODITE_STEP, KURODITE_BREAK, KURODITE_STEP, KURODITE_STEP);

    public static final SoundEvent KURODITE_BRICKS_BREAK = register("block.kurodite_bricks.break");
    public static final SoundEvent KURODITE_BRICKS_STEP = register("block.kurodite_bricks.step");
    public static final SoundType KURODITE_BRICKS = new SoundType(0.6F, 1.0F, KURODITE_BRICKS_BREAK, KURODITE_BRICKS_STEP, KURODITE_BRICKS_BREAK, KURODITE_BRICKS_STEP, KURODITE_BRICKS_STEP);

    public static final SoundEvent SHADOLINE_BREAK = register("block.shadoline.break");
    public static final SoundEvent SHADOLINE_PLACE = register("block.shadoline.place");
    public static final SoundEvent SHADOLINE_STEP = register("block.shadoline.step");
    public static final SoundType SHADOLINE = new SoundType(1.0F, 1.0F, SHADOLINE_BREAK, SHADOLINE_STEP, SHADOLINE_PLACE, SHADOLINE_STEP, SHADOLINE_STEP);

    public static final SoundEvent END_ROD_PLACE = register("block.end_rod.place");
    public static final SoundType END_ROD = new SoundType(1.0F, 1.0F, END_ROD_PLACE, END_ROD_PLACE, END_ROD_PLACE, END_ROD_PLACE, END_ROD_PLACE);

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