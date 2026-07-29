package net.nicolas.calcium.mixin.screens.handlers;

import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.Items;
import net.nicolas.calcium.screen.TooltipSlot;
import org.spongepowered.asm.mixin.Mixin;

public class TooltipsMixin {

    @Mixin(value = FurnaceFuelSlot.class, targets = "net.minecraft.world.inventory.BrewingStandMenu$FuelSlot")
    public static abstract class FuelSlotMixin implements TooltipSlot {
        @Override public Component getTooltip() {
            if ((Object) this instanceof FurnaceFuelSlot) {
                return Component.translatable("tooltip.calcium.fuel");
            }
            return Component.translatable("tooltip.calcium.specific_fuel", Component.translatable(Items.BLAZE_POWDER.getDescriptionId()));
        }
    }

    @Mixin(targets = "net.minecraft.world.inventory.BrewingStandMenu$IngredientsSlot")
    public static abstract class ReagentSlotMixin implements TooltipSlot {
        @Override public Component getTooltip() {
            return Component.translatable("tooltip.calcium.brewing_stand_reagent");
        }
    }

    @Mixin(targets = "net.minecraft.world.inventory.LoomMenu$3")
    public static abstract class LoomBannerSlotMixin implements TooltipSlot {
        @Override public Component getTooltip() {
            return Component.translatable("tooltip.calcium.loom_banner");
        }
    }

    @Mixin(targets = "net.minecraft.world.inventory.LoomMenu$4")
    public static abstract class LoomDyeSlotMixin implements TooltipSlot {
        @Override public Component getTooltip() {
            return Component.translatable("tooltip.calcium.loom_dye");
        }
    }

    @Mixin(targets = "net.minecraft.world.inventory.LoomMenu$5")
    public static abstract class LoomPatternSlotMixin implements TooltipSlot {
        @Override public Component getTooltip() {
            return Component.translatable("tooltip.calcium.loom_pattern");
        }
    }

}