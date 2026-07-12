package net.nicolas.calcium.mixin.accessors;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CauldronInteraction.Dispatcher.class)
public interface CauldronDispatcherAccessor {

    @Invoker("put") void calcium$put(Item item, CauldronInteraction interaction);

}