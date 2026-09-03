package net.nicolas.calcium.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopperCollection;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import java.util.IdentityHashMap;
import java.util.Map;

// This class stores overrides for Vanilla block note block instrument assignments.

public final class ModNotes {

    private static final Map<Block, NoteBlockInstrument> BLOCK_INSTRUMENT = new IdentityHashMap<>();

    static {

        putCopper(Blocks.COPPER_GRATE);
        putCopper(Blocks.COPPER_BULB);
        putCopper(Blocks.COPPER_DOOR);
        putCopper(Blocks.COPPER_TRAPDOOR);
        putCopper(Blocks.COPPER_BARS);
        putCopper(Blocks.COPPER_CHAIN);
        putCopper(Blocks.COPPER_LANTERN);

        BLOCK_INSTRUMENT.put(Blocks.IRON_DOOR, NoteBlockInstrument.IRON_XYLOPHONE);
        BLOCK_INSTRUMENT.put(Blocks.IRON_TRAPDOOR, NoteBlockInstrument.IRON_XYLOPHONE);
        BLOCK_INSTRUMENT.put(Blocks.IRON_BARS, NoteBlockInstrument.IRON_XYLOPHONE);
        BLOCK_INSTRUMENT.put(Blocks.IRON_CHAIN, NoteBlockInstrument.IRON_XYLOPHONE);
        BLOCK_INSTRUMENT.put(Blocks.LANTERN, NoteBlockInstrument.IRON_XYLOPHONE);

    }

    private static void putCopper(WeatheringCopperCollection<Block> collection) {
        putCopperState(collection.weathering());
        putCopperState(collection.waxed());
    }

    private static void putCopperState(WeatheringCopperCollection.ByState<Block> state) {

        BLOCK_INSTRUMENT.put(state.unaffected(), NoteBlockInstrument.TRUMPET);
        BLOCK_INSTRUMENT.put(state.exposed(), NoteBlockInstrument.TRUMPET_EXPOSED);
        BLOCK_INSTRUMENT.put(state.weathered(), NoteBlockInstrument.TRUMPET_WEATHERED);
        BLOCK_INSTRUMENT.put(state.oxidized(), NoteBlockInstrument.TRUMPET_OXIDIZED);

    }

    private ModNotes() {}

    public static NoteBlockInstrument of(Block block) {
        return BLOCK_INSTRUMENT.get(block);
    }

}