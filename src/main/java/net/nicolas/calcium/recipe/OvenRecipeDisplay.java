package net.nicolas.calcium.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.List;

public record OvenRecipeDisplay(List<SlotDisplay> ingredients, SlotDisplay fuel, SlotDisplay result, SlotDisplay craftingStation) implements RecipeDisplay {

    public static final MapCodec<OvenRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        SlotDisplay.CODEC.listOf().fieldOf("ingredients").forGetter(OvenRecipeDisplay::ingredients),
        SlotDisplay.CODEC.fieldOf("fuel").forGetter(OvenRecipeDisplay::fuel),
        SlotDisplay.CODEC.fieldOf("result").forGetter(OvenRecipeDisplay::result),
        SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(OvenRecipeDisplay::craftingStation)
    ).apply(instance, OvenRecipeDisplay::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, OvenRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
        SlotDisplay.STREAM_CODEC.apply(ByteBufCodecs.list()), OvenRecipeDisplay::ingredients,
        SlotDisplay.STREAM_CODEC, OvenRecipeDisplay::fuel,
        SlotDisplay.STREAM_CODEC, OvenRecipeDisplay::result,
        SlotDisplay.STREAM_CODEC, OvenRecipeDisplay::craftingStation,
        OvenRecipeDisplay::new
    );

    public static final RecipeDisplay.Type<OvenRecipeDisplay> TYPE = new RecipeDisplay.Type<>(MAP_CODEC, STREAM_CODEC);

    @Override public RecipeDisplay.Type<OvenRecipeDisplay> type() {
        return TYPE;
    }

}
