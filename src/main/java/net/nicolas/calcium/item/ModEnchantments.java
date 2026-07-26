package net.nicolas.calcium.item;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

    // This class stores the mod's enchantment registrations.

public class ModEnchantments {

    public static final ResourceKey<Enchantment> ANCHORING = ResourceKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath("calcium", "anchoring"));

    public static int getAnchoringLevel(LivingEntity entity) {
        Holder<Enchantment> holder = entity.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ANCHORING);
        return EnchantmentHelper.getEnchantmentLevel(holder, entity);
    }

}