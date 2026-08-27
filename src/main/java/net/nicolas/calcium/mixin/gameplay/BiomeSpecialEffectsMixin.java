package net.nicolas.calcium.mixin.gameplay;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.nicolas.calcium.core.util.CalciumDirtColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(BiomeSpecialEffects.class)
public abstract class BiomeSpecialEffectsMixin {

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/codecs/RecordCodecBuilder;create(Ljava/util/function/Function;)Lcom/mojang/serialization/Codec;"))
    private static Codec<BiomeSpecialEffects> calcium$addDirtColor(Function<RecordCodecBuilder.Instance<BiomeSpecialEffects>, ? extends App<RecordCodecBuilder.Mu<BiomeSpecialEffects>, BiomeSpecialEffects>> builder) {
        MapCodec<BiomeSpecialEffects> base = RecordCodecBuilder.mapCodec(builder);
        MapCodec<Integer> dirtColor = ExtraCodecs.STRING_RGB_COLOR.optionalFieldOf("dirt_color", CalciumDirtColors.DEFAULT_DIRT_COLOR);
        return Codec.mapPair(base, dirtColor).codec().xmap(
            pair -> {
                CalciumDirtColors.put(pair.getFirst(), pair.getSecond());
                return pair.getFirst();
            },
            effects -> Pair.of(effects, CalciumDirtColors.get(effects))
        );
    }

}
