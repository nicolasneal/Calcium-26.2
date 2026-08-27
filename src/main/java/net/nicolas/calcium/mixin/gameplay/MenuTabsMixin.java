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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

    // This mixin overrides the Creative Mode inventory tab icons.

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

    @Inject(method = "getIconItem", at = @At("HEAD"), cancellable = true)
    private void calcium$overrideTabIcon(CallbackInfoReturnable<ItemStack> cir) {
        CreativeModeTab tab = (CreativeModeTab) (Object) this;
        ResourceKey<CreativeModeTab> key = BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(tab).orElse(null);
        ItemLike icon = Icons.TAB_ICONS.get(key);
        if (icon != null) {
            cir.setReturnValue(new ItemStack(icon));
        }
    }

}
