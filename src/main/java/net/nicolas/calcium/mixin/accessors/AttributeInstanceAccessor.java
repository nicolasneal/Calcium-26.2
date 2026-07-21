package net.nicolas.calcium.mixin.accessors;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AttributeInstance.class)
public interface AttributeInstanceAccessor {

    @Accessor("baseValue") @Mutable void setBaseValue(double baseValue);

}
