package net.nicolas.calcium.mixin.accessors;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MultiNoiseBiomeSourceParameterList.class)
public interface MultiNoiseBiomeSourceParameterListAccessor {

    @Accessor("parameters") @Mutable
    void calcium$setParameters(Climate.ParameterList<Holder<Biome>> parameters);

}