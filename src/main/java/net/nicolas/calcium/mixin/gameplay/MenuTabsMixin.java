package net.nicolas.calcium.mixin.gameplay;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

    // This mixin overrides the Creative Mode inventory tab order and icons. Tab positions are set in TAB_SLOTS: TOP or
    // BOTTOM is the row and the number is the column (0 to 6, left to right, with the last column aligned to the right).

@Mixin(CreativeModeTab.class)
public abstract class MenuTabsMixin {

    private static final class Icons {

        static final Map<ResourceKey<CreativeModeTab>, ItemLike> TAB_ICONS = new ImmutableMap.Builder<ResourceKey<CreativeModeTab>, ItemLike>()

            .put(CreativeModeTabs.BUILDING_BLOCKS, Blocks.BRICKS)
            .put(CreativeModeTabs.COLORED_BLOCKS, Blocks.WOOL.cyan())
            .put(CreativeModeTabs.NATURAL_BLOCKS, Blocks.GRASS_BLOCK)
            .put(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.BLAST_FURNACE)
            .put(CreativeModeTabs.REDSTONE_BLOCKS, Items.REDSTONE_BLOCK)
            .put(CreativeModeTabs.TOOLS_AND_UTILITIES, Items.DIAMOND_PICKAXE)
            .put(CreativeModeTabs.COMBAT, Items.MACE)
            .put(CreativeModeTabs.FOOD_AND_DRINKS, Items.BREAD)
            .put(CreativeModeTabs.INGREDIENTS, Items.IRON_INGOT)
            .put(CreativeModeTabs.SPAWN_EGGS, Items.CREEPER_SPAWN_EGG)

            .build();

    }

    private record Slot(CreativeModeTab.Row row, int column) {}

    private static final class Slots {

        static final Map<ResourceKey<CreativeModeTab>, Slot> TAB_SLOTS = new ImmutableMap.Builder<ResourceKey<CreativeModeTab>, Slot>()

            .put(CreativeModeTabs.NATURAL_BLOCKS, new Slot(CreativeModeTab.Row.TOP, 0))
            .put(CreativeModeTabs.BUILDING_BLOCKS, new Slot(CreativeModeTab.Row.TOP, 2))
            .put(CreativeModeTabs.COLORED_BLOCKS, new Slot(CreativeModeTab.Row.TOP, 1))
            .put(CreativeModeTabs.FUNCTIONAL_BLOCKS, new Slot(CreativeModeTab.Row.TOP, 3))
            .put(CreativeModeTabs.REDSTONE_BLOCKS, new Slot(CreativeModeTab.Row.TOP, 4))
            .put(CreativeModeTabs.HOTBAR, new Slot(CreativeModeTab.Row.TOP, 5))
            .put(CreativeModeTabs.SEARCH, new Slot(CreativeModeTab.Row.TOP, 6))

            .put(CreativeModeTabs.TOOLS_AND_UTILITIES, new Slot(CreativeModeTab.Row.BOTTOM, 0))
            .put(CreativeModeTabs.COMBAT, new Slot(CreativeModeTab.Row.BOTTOM, 1))
            .put(CreativeModeTabs.FOOD_AND_DRINKS, new Slot(CreativeModeTab.Row.BOTTOM, 2))
            .put(CreativeModeTabs.INGREDIENTS, new Slot(CreativeModeTab.Row.BOTTOM, 3))
            .put(CreativeModeTabs.SPAWN_EGGS, new Slot(CreativeModeTab.Row.BOTTOM, 4))
            .put(CreativeModeTabs.OP_BLOCKS, new Slot(CreativeModeTab.Row.BOTTOM, 5))
            .put(CreativeModeTabs.INVENTORY, new Slot(CreativeModeTab.Row.BOTTOM, 6))

            .build();

    }

    @Inject(method = "getIconItem", at = @At("HEAD"), cancellable = true)
    private void calcium$overrideTabIcon(CallbackInfoReturnable<ItemStack> cir) {
        ItemLike icon = Icons.TAB_ICONS.get(this.calcium$key());
        if (icon != null) {
            cir.setReturnValue(new ItemStack(icon));
        }
    }

    @Inject(method = "row", at = @At("HEAD"), cancellable = true)
    private void calcium$overrideTabRow(CallbackInfoReturnable<CreativeModeTab.Row> cir) {
        Slot slot = Slots.TAB_SLOTS.get(this.calcium$key());
        if (slot != null) {
            cir.setReturnValue(slot.row());
        }
    }

    @Inject(method = "column", at = @At("HEAD"), cancellable = true)
    private void calcium$overrideTabColumn(CallbackInfoReturnable<Integer> cir) {
        Slot slot = Slots.TAB_SLOTS.get(this.calcium$key());
        if (slot != null) {
            cir.setReturnValue(slot.column());
        }
    }

    @Unique
    private ResourceKey<CreativeModeTab> calcium$key() {
        return BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey((CreativeModeTab) (Object) this).orElse(null);
    }

}
