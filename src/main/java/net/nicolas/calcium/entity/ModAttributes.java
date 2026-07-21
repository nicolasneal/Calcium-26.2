package net.nicolas.calcium.entity;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.nicolas.calcium.mixin.accessors.AttributeInstanceAccessor;
import net.nicolas.calcium.mixin.accessors.AttributeSupplierInvoker;

public class ModAttributes {

    public static void initialize() {

        setHealth(EntityTypes.ALLAY, 10.0);
        setHealth(EntityTypes.ARMADILLO, 12.0);
        setHealth(EntityTypes.ARMOR_STAND, 20.0);
        setHealth(EntityTypes.AXOLOTL, 6.0);
        setHealth(EntityTypes.BAT, 4.0);
        setHealth(EntityTypes.BEE, 3.0);
        setHealth(EntityTypes.BLAZE, 20.0);
        setHealth(EntityTypes.BOGGED, 20.0);
        setHealth(EntityTypes.BREEZE, 30.0);
        setHealth(EntityTypes.CAMEL, 30.0);
        setHealth(EntityTypes.CAMEL_HUSK, 30.0);
        setHealth(EntityTypes.CAT, 8.0);
        setHealth(EntityTypes.CAVE_SPIDER, 10.0);
        setHealth(EntityTypes.CHICKEN, 6.0);
        setHealth(EntityTypes.COD, 3.0);
        setHealth(EntityTypes.COPPER_GOLEM, 16.0);
        setHealth(EntityTypes.COW, 16.0);
        setHealth(EntityTypes.CREAKING, 1.0);
        setHealth(EntityTypes.CREEPER, 20.0);
        setHealth(EntityTypes.DOLPHIN, 16.0);
        setHealth(EntityTypes.DONKEY, 53.0);
        setHealth(EntityTypes.DROWNED, 20.0);
        setHealth(EntityTypes.ELDER_GUARDIAN, 100.0);
        setHealth(EntityTypes.ENDERMAN, 40.0);
        setHealth(EntityTypes.ENDERMITE, 6.0);
        setHealth(EntityTypes.ENDER_DRAGON, 200.0);
        setHealth(EntityTypes.EVOKER, 30.0);
        setHealth(EntityTypes.FOX, 8.0);
        setHealth(EntityTypes.FROG, 6.0);
        setHealth(EntityTypes.GHAST, 20.0);
        setHealth(EntityTypes.GIANT, 100.0);
        setHealth(EntityTypes.GLOW_SQUID, 16.0);
        setHealth(EntityTypes.GOAT, 16.0);
        setHealth(EntityTypes.GUARDIAN, 30.0);
        setHealth(EntityTypes.HAPPY_GHAST, 20.0);
        setHealth(EntityTypes.HOGLIN, 30.0);
        setHealth(EntityTypes.HORSE, 53.0);
        setHealth(EntityTypes.HUSK, 20.0);
        setHealth(EntityTypes.ILLUSIONER, 32.0);
        setHealth(EntityTypes.IRON_GOLEM, 100.0);
        setHealth(EntityTypes.LLAMA, 53.0);
        setHealth(EntityTypes.MAGMA_CUBE, 20.0);
        setHealth(EntityTypes.MANNEQUIN, 20.0);
        setHealth(EntityTypes.MOOSHROOM, 16.0);
        setHealth(EntityTypes.MULE, 53.0);
        setHealth(EntityTypes.NAUTILUS, 15.0);
        setHealth(EntityTypes.OCELOT, 8.0);
        setHealth(EntityTypes.PANDA, 20.0);
        setHealth(EntityTypes.PARCHED, 20.0);
        setHealth(EntityTypes.PARROT, 4.0);
        setHealth(EntityTypes.PHANTOM, 10.0);
        setHealth(EntityTypes.PIG, 14.0);
        setHealth(EntityTypes.PIGLIN, 20.0);
        setHealth(EntityTypes.PIGLIN_BRUTE, 30.0);
        setHealth(EntityTypes.PILLAGER, 20.0);
        setHealth(EntityTypes.PLAYER, 20.0);
        setHealth(EntityTypes.POLAR_BEAR, 30.0);
        setHealth(EntityTypes.PUFFERFISH, 3.0);
        setHealth(EntityTypes.RABBIT, 4.0);
        setHealth(EntityTypes.RAVAGER, 60.0);
        setHealth(EntityTypes.SALMON, 3.0);
        setHealth(EntityTypes.SHEEP, 14.0);
        setHealth(EntityTypes.SHULKER, 30.0);
        setHealth(EntityTypes.SILVERFISH, 6.0);
        setHealth(EntityTypes.SKELETON, 20.0);
        setHealth(EntityTypes.SKELETON_HORSE, 20.0);
        setHealth(EntityTypes.SLIME, 20.0);
        setHealth(EntityTypes.SNIFFER, 40.0);
        setHealth(EntityTypes.SNOW_GOLEM, 10.0);
        setHealth(EntityTypes.SPIDER, 16.0);
        setHealth(EntityTypes.SQUID, 16.0);
        setHealth(EntityTypes.STRAY, 20.0);
        setHealth(EntityTypes.STRIDER, 20.0);
        setHealth(EntityTypes.SULFUR_CUBE, 20.0);
        setHealth(EntityTypes.TADPOLE, 1.0);
        setHealth(EntityTypes.TRADER_LLAMA, 53.0);
        setHealth(EntityTypes.TROPICAL_FISH, 3.0);
        setHealth(EntityTypes.TURTLE, 10.0);
        setHealth(EntityTypes.VEX, 10.0);
        setHealth(EntityTypes.VILLAGER, 20.0);
        setHealth(EntityTypes.VINDICATOR, 20.0);
        setHealth(EntityTypes.WANDERING_TRADER, 20.0);
        setHealth(EntityTypes.WARDEN, 200.0);
        setHealth(EntityTypes.WITCH, 20.0);
        setHealth(EntityTypes.WITHER, 300.0);
        setHealth(EntityTypes.WITHER_SKELETON, 24.0);
        setHealth(EntityTypes.WOLF, 8.0);
        setHealth(EntityTypes.ZOGLIN, 30.0);
        setHealth(EntityTypes.ZOMBIE, 20.0);
        setHealth(EntityTypes.ZOMBIE_HORSE, 33.0);
        setHealth(EntityTypes.ZOMBIE_NAUTILUS, 15.0);
        setHealth(EntityTypes.ZOMBIE_VILLAGER, 20.0);
        setHealth(EntityTypes.ZOMBIFIED_PIGLIN, 20.0);

        setAttribute(EntityTypes.CREAKING, Attributes.ATTACK_DAMAGE, 6.0);
        setAttribute(EntityTypes.HUSK, Attributes.SCALE, 0.938967);

    }

    private static void setHealth(EntityType<? extends LivingEntity> type, double health) {
        setAttribute(type, Attributes.MAX_HEALTH, health);
    }

    private static void setAttribute(EntityType<? extends LivingEntity> type, Holder<Attribute> attribute, double value) {
        ((AttributeInstanceAccessor) ((AttributeSupplierInvoker) DefaultAttributes.getSupplier(type)).calcium$getAttributeInstance(attribute)).setBaseValue(value);
    }

}
