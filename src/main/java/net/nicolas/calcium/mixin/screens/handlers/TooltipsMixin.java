package net.nicolas.calcium.mixin.screens.handlers;

import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.Items;
import net.nicolas.calcium.screen.TooltipSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

public class TooltipsMixin {

    @Mixin(value = FurnaceFuelSlot.class, targets = "net.minecraft.world.inventory.BrewingStandMenu$FuelSlot")
    public static abstract class FuelSlotMixin implements TooltipSlot {
        @Unique private static final Component FUEL_TOOLTIP = Component.translatable("tooltip.calcium.fuel");
        @Unique private static final Component SPECIFIC_FUEL_TOOLTIP = Component.translatable("tooltip.calcium.specific_fuel", Component.translatable(Items.BLAZE_POWDER.getDescriptionId()));
        @Override public Component getTooltip() {
            if ((Object) this instanceof FurnaceFuelSlot) {
                return FUEL_TOOLTIP;
            }
            return SPECIFIC_FUEL_TOOLTIP;
        }
    }

    @Mixin(targets = "net.minecraft.world.inventory.BrewingStandMenu$IngredientsSlot")
    public static abstract class ReagentSlotMixin implements TooltipSlot {
        @Unique private static final Component TOOLTIP = Component.translatable("tooltip.calcium.brewing_stand_reagent");
        @Override public Component getTooltip() {
            return TOOLTIP;
        }
    }

    @Mixin(targets = "net.minecraft.world.inventory.LoomMenu$3")
    public static abstract class LoomBannerSlotMixin implements TooltipSlot {
        @Unique private static final Component TOOLTIP = Component.translatable("tooltip.calcium.loom_banner");
        @Override public Component getTooltip() {
            return TOOLTIP;
        }
    }

    @Mixin(targets = "net.minecraft.world.inventory.LoomMenu$4")
    public static abstract class LoomDyeSlotMixin implements TooltipSlot {
        @Unique private static final Component TOOLTIP = Component.translatable("tooltip.calcium.loom_dye");
        @Override public Component getTooltip() {
            return TOOLTIP;
        }
    }

    @Mixin(targets = "net.minecraft.world.inventory.LoomMenu$5")
    public static abstract class LoomPatternSlotMixin implements TooltipSlot {
        @Unique private static final Component TOOLTIP = Component.translatable("tooltip.calcium.loom_pattern");
        @Override public Component getTooltip() {
            return TOOLTIP;
        }
    }

}