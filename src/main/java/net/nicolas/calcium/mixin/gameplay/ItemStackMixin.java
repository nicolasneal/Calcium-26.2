package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Unique private static final Set<Item> COMMON_RARITY_ITEMS = Set.of(

        Items.CHAINMAIL_HELMET,
        Items.CHAINMAIL_CHESTPLATE,
        Items.CHAINMAIL_LEGGINGS,
        Items.CHAINMAIL_BOOTS,

        Items.DIAMOND_SWORD,
        Items.DIAMOND_SPEAR,
        Items.DIAMOND_PICKAXE,
        Items.DIAMOND_AXE,
        Items.DIAMOND_SHOVEL,
        Items.DIAMOND_HOE,
        Items.DIAMOND_HELMET,
        Items.DIAMOND_CHESTPLATE,
        Items.DIAMOND_LEGGINGS,
        Items.DIAMOND_BOOTS,
        Items.DIAMOND_HORSE_ARMOR,
        Items.DIAMOND_NAUTILUS_ARMOR

    );

    @Shadow public abstract Item getItem();
    @Shadow public abstract boolean isEnchanted();

    @Inject(method = "getRarity", at = @At("HEAD"), cancellable = true)
    private void calcium$fixRarity(CallbackInfoReturnable<Rarity> cir) {

        if (COMMON_RARITY_ITEMS.contains(this.getItem())) {
            if (this.isEnchanted()) {
                cir.setReturnValue(Rarity.RARE);
            } else {
                cir.setReturnValue(Rarity.COMMON);
            }
        }

    }

}