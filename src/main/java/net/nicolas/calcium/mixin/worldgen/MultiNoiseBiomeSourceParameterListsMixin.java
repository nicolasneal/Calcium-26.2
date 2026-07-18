package net.nicolas.calcium.mixin.worldgen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import net.nicolas.calcium.mixin.accessors.MultiNoiseBiomeSourceParameterListAccessor;
import net.nicolas.calcium.worldgen.CalciumOverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

@Mixin(MultiNoiseBiomeSourceParameterLists.class)
public class MultiNoiseBiomeSourceParameterListsMixin {

    @Redirect(method = "bootstrap", at = @At(value = "NEW", target = "Lnet/minecraft/world/level/biome/MultiNoiseBiomeSourceParameterList;"))
    private static MultiNoiseBiomeSourceParameterList calcium$useCalciumOverworldBuilder(MultiNoiseBiomeSourceParameterList.Preset preset, HolderGetter<Biome> biomes) {
        MultiNoiseBiomeSourceParameterList result = new MultiNoiseBiomeSourceParameterList(preset, biomes);
        if (preset == MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD) {
            List<Pair<Climate.ParameterPoint, Holder<Biome>>> entries = new ArrayList<>();
            new CalciumOverworldBiomeBuilder().addBiomes(point -> entries.add(point.mapSecond(biomes::getOrThrow)));
            ((MultiNoiseBiomeSourceParameterListAccessor) (Object) result).calcium$setParameters(new Climate.ParameterList<>(entries));
        }
        return result;
    }

}