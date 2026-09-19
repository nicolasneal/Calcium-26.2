package net.nicolas.calcium.mixin.accessors;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.BlockStateBase.class)
public interface BlockStateBaseAccessor {

    @Accessor("offsetFunction") @Mutable void setOffsetFunction(BlockBehaviour.OffsetFunction offsetFunction);

    @Accessor("mapColor") @Mutable void setMapColor(MapColor mapColor);

}