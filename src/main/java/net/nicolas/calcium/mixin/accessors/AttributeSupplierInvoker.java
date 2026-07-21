package net.nicolas.calcium.mixin.accessors;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AttributeSupplier.class)
public interface AttributeSupplierInvoker {

    @Invoker("getAttributeInstance") AttributeInstance calcium$getAttributeInstance(Holder<Attribute> attribute);

}
