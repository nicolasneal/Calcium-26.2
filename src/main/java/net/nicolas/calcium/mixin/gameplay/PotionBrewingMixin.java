package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

    // This mixin changes the reagent in the Water Breathing Potion recipe from a Pufferfish to a Pufferfish Bucket.
    // This is necessary because the Pufferfish item was made unobtainable in service of adding Fish, a general mob
    // drop that replaces Cod, Salmon, Tropical Fish, and Pufferfish.

@Mixin(PotionBrewing.class)
public abstract class PotionBrewingMixin {

    @Redirect(method = "addVanillaMixes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/alchemy/PotionBrewing$Builder;addMix(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/Item;Lnet/minecraft/core/Holder;)V"))
    private static void calcium$requirePufferfishBucket(PotionBrewing.Builder builder, Holder<Potion> from, Item ingredient, Holder<Potion> to) {
        builder.addMix(from, ingredient == Items.PUFFERFISH ? Items.PUFFERFISH_BUCKET : ingredient, to);
    }

}