package net.nicolas.calcium.sound;

import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent ECTOPLASM_BUCKET_EMPTY = register("item.bucket.empty_ectoplasm");
    public static final SoundEvent ECTOPLASM_BUCKET_FILL = register("item.bucket.fill_ectoplasm");
    public static final SoundEvent ECTOPLASM_AMBIENT = register("block.ectoplasm.whispering");

    public static final SoundEvent SOULSLATE_BREAK = register("block.soulslate.break");
    public static final SoundEvent SOULSLATE_PLACE = register("block.soulslate.place");
    public static final SoundEvent SOULSLATE_STEP = register("block.soulslate.step");
    public static final BlockSoundGroup SOULSLATE = new BlockSoundGroup(1.0F, 1.0F, SOULSLATE_BREAK, SOULSLATE_STEP, SOULSLATE_PLACE, SOULSLATE_STEP, SOULSLATE_STEP);

    public static final SoundEvent END_STONE_BREAK = register("block.end_stone.break");
    public static final SoundEvent END_STONE_STEP = register("block.end_stone.step");
    public static final BlockSoundGroup END_STONE = new BlockSoundGroup(1.0F, 1.0F, END_STONE_BREAK, END_STONE_STEP, END_STONE_BREAK, END_STONE_STEP, END_STONE_STEP);
    
    public static final SoundEvent PALLID_MAGNIA_BREAK = register("block.pallid_magnia.break");
    public static final SoundEvent PALLID_MAGNIA_PLACE = register("block.pallid_magnia.place");
    public static final SoundEvent PALLID_MAGNIA_STEP = register("block.pallid_magnia.step");
    public static final BlockSoundGroup PALLID_MAGNIA = new BlockSoundGroup(1.0F, 1.0F, PALLID_MAGNIA_BREAK, PALLID_MAGNIA_STEP, PALLID_MAGNIA_PLACE, PALLID_MAGNIA_STEP, PALLID_MAGNIA_STEP);

    public static final SoundEvent UMBRAL_MAGNIA_BREAK = register("block.umbral_magnia.break");
    public static final SoundEvent UMBRAL_MAGNIA_PLACE = register("block.umbral_magnia.place");
    public static final SoundEvent UMBRAL_MAGNIA_STEP = register("block.umbral_magnia.step");
    public static final BlockSoundGroup UMBRAL_MAGNIA = new BlockSoundGroup(1.0F, 1.0F, UMBRAL_MAGNIA_BREAK, UMBRAL_MAGNIA_STEP, UMBRAL_MAGNIA_PLACE, UMBRAL_MAGNIA_STEP, UMBRAL_MAGNIA_STEP);

    public static final SoundEvent KURODITE_BREAK = register("block.kurodite.break");
    public static final SoundEvent KURODITE_STEP = register("block.kurodite.step");
    public static final BlockSoundGroup KURODITE = new BlockSoundGroup(0.6F, 1.0F, KURODITE_BREAK, KURODITE_STEP, KURODITE_BREAK, KURODITE_STEP, KURODITE_STEP);

    public static final SoundEvent KURODITE_BRICKS_BREAK = register("block.kurodite_bricks.break");
    public static final SoundEvent KURODITE_BRICKS_STEP = register("block.kurodite_bricks.step");
    public static final BlockSoundGroup KURODITE_BRICKS = new BlockSoundGroup(0.6F, 1.0F, KURODITE_BRICKS_BREAK, KURODITE_BRICKS_STEP, KURODITE_BRICKS_BREAK, KURODITE_BRICKS_STEP, KURODITE_BRICKS_STEP);

    public static final SoundEvent SHADOLINE_BREAK = register("block.shadoline.break");
    public static final SoundEvent SHADOLINE_PLACE = register("block.shadoline.place");
    public static final SoundEvent SHADOLINE_STEP = register("block.shadoline.step");
    public static final BlockSoundGroup SHADOLINE = new BlockSoundGroup(1.0F, 1.0F, SHADOLINE_BREAK, SHADOLINE_STEP, SHADOLINE_PLACE, SHADOLINE_STEP, SHADOLINE_STEP);

    public static final SoundEvent END_ROD_PLACE = register("block.end_rod.place");
    public static final BlockSoundGroup END_ROD = new BlockSoundGroup(1.0F, 1.0F, END_ROD_PLACE, END_ROD_PLACE, END_ROD_PLACE, END_ROD_PLACE, END_ROD_PLACE);

    public static final SoundEvent MUSIC_DISC_BLISS = register("music.jukebox.bliss");
    public static final SoundEvent MUSIC_DISC_DECAY = register("music.jukebox.decay");
    public static final SoundEvent MUSIC_DISC_GLARE = register("music.jukebox.glare");

    public static final RegistryKey<JukeboxSong> BLISS = RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of("calcium", "bliss"));
    public static final RegistryKey<JukeboxSong> DECAY = RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of("calcium", "decay"));
    public static final RegistryKey<JukeboxSong> GLARE = RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of("calcium", "glare"));

    private static SoundEvent register(String name) {
        Identifier id = Identifier.of("calcium", name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void initialize() {}

}