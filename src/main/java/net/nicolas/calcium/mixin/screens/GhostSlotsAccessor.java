package net.nicolas.calcium.mixin.screens;

import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GhostSlots.class)
public interface GhostSlotsAccessor {

    @Invoker("setInput") void calcium$setInput(Slot slot, ContextMap context, SlotDisplay contents);

    @Invoker("setResult") void calcium$setResult(Slot slot, ContextMap context, SlotDisplay contents);

    @Accessor("ingredients") Reference2ObjectMap<Slot, ?> calcium$getIngredients();

}
