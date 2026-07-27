package net.nicolas.calcium.mixin.worldgen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.nicolas.calcium.mixin.accessors.MultiNoiseBiomeSourceParameterListAccessor;
import net.nicolas.calcium.worldgen.CalciumOverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(MappedRegistry.class)
public abstract class MappedRegistryMixin<T> {

    @Inject(method = "register", at = @At("HEAD"))
    private void calcium$patchOverworldBiomeTable(ResourceKey<T> key, T value, RegistrationInfo registrationInfo, CallbackInfoReturnable<Holder.Reference<T>> cir) {
        if (value instanceof MultiNoiseBiomeSourceParameterList list && key.identifier().equals(Identifier.withDefaultNamespace("overworld"))) {
            MultiNoiseBiomeSourceParameterListAccessor accessor = (MultiNoiseBiomeSourceParameterListAccessor) list;
            Map<ResourceKey<Biome>, Holder<Biome>> biomeLookup = new HashMap<>();
            for (Pair<Climate.ParameterPoint, Holder<Biome>> pair : accessor.calcium$getParameters().values()) {
                pair.getSecond().unwrapKey().ifPresent(biomeKey -> biomeLookup.put(biomeKey, pair.getSecond()));
            }
            List<Pair<Climate.ParameterPoint, Holder<Biome>>> entries = new ArrayList<>();
            new CalciumOverworldBiomeBuilder().addBiomes(point -> entries.add(point.mapSecond(biomeLookup::get)));
            accessor.calcium$setParameters(new Climate.ParameterList<>(entries));
        }
    }

}
