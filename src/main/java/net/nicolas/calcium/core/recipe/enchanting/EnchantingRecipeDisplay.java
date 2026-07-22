package net.nicolas.calcium.core.recipe.enchanting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.List;

public record EnchantingRecipeDisplay(List<SlotDisplay> ingredients, SlotDisplay tablet, SlotDisplay result, SlotDisplay craftingStation) implements RecipeDisplay {

    public static final MapCodec<EnchantingRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        SlotDisplay.CODEC.listOf().fieldOf("ingredients").forGetter(EnchantingRecipeDisplay::ingredients),
        SlotDisplay.CODEC.fieldOf("tablet").forGetter(EnchantingRecipeDisplay::tablet),
        SlotDisplay.CODEC.fieldOf("result").forGetter(EnchantingRecipeDisplay::result),
        SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(EnchantingRecipeDisplay::craftingStation)
    ).apply(instance, EnchantingRecipeDisplay::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, EnchantingRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
        SlotDisplay.STREAM_CODEC.apply(ByteBufCodecs.list()), EnchantingRecipeDisplay::ingredients,
        SlotDisplay.STREAM_CODEC, EnchantingRecipeDisplay::tablet,
        SlotDisplay.STREAM_CODEC, EnchantingRecipeDisplay::result,
        SlotDisplay.STREAM_CODEC, EnchantingRecipeDisplay::craftingStation,
        EnchantingRecipeDisplay::new
    );

    public static final RecipeDisplay.Type<EnchantingRecipeDisplay> TYPE = new RecipeDisplay.Type<>(MAP_CODEC, STREAM_CODEC);

    @Override public RecipeDisplay.Type<EnchantingRecipeDisplay> type() {
        return TYPE;
    }

}