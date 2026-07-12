package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract Item getItem();
    @Shadow public abstract boolean isEnchanted();

    @Inject(method = "getRarity", at = @At("HEAD"), cancellable = true)
    private void calcium$fixChainmailRarity(CallbackInfoReturnable<Rarity> cir) {

        if (this.getItem() == Items.CHAINMAIL_HELMET || this.getItem() == Items.CHAINMAIL_CHESTPLATE || this.getItem() == Items.CHAINMAIL_LEGGINGS || this.getItem() == Items.CHAINMAIL_BOOTS) {
            if (this.isEnchanted()) {
                cir.setReturnValue(Rarity.RARE);
            } else {
                cir.setReturnValue(Rarity.COMMON);
            }
        }

    }

}