package net.nicolas.calcium.mixin.screens.handlers;

import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.MerchantResultSlot;
import net.minecraft.world.inventory.ResultSlot;
import net.nicolas.calcium.screen.BigResultSlot;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {ResultSlot.class, FurnaceResultSlot.class, MerchantResultSlot.class}, targets = {"net.minecraft.world.inventory.ItemCombinerMenu$3", "net.minecraft.world.inventory.GrindstoneMenu$4", "net.minecraft.world.inventory.StonecutterMenu$2", "net.minecraft.world.inventory.CartographyTableMenu$5", "net.minecraft.world.inventory.LoomMenu$6"})
public abstract class BigResultSlotMixin implements BigResultSlot {}