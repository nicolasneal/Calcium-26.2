package net.nicolas.calcium.mixin;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.BlockStateBase.class)
public interface BlockStateBaseAccessor {

    @Accessor("offsetFunction") @Mutable void setOffsetFunction(BlockBehaviour.OffsetFunction offsetFunction);

}
