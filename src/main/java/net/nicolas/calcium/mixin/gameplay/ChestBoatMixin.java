package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.boat.AbstractChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractChestBoat.class)
public abstract class ChestBoatMixin {

    @WrapOperation(method = "destroy(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/boat/AbstractChestBoat;destroy(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/Item;)V"))
    private void calcium$dropBoatAndChestSeparately(AbstractChestBoat instance, ServerLevel level, Item dropItem, Operation<Void> original) {
        original.call(instance, level, calcium$boatItem(dropItem));
        if (level.getGameRules().get(GameRules.ENTITY_DROPS)) {
            instance.spawnAtLocation(level, new ItemStack(Items.CHEST));
        }
    }

    @Unique
    private static Item calcium$boatItem(Item chestBoatItem) {
        Identifier key = BuiltInRegistries.ITEM.getKey(chestBoatItem);
        String path = key.getPath();
        String boatPath;
        if (path.endsWith("_chest_boat")) {
            boatPath = path.substring(0, path.length() - "_chest_boat".length()) + "_boat";
        } else if (path.endsWith("_chest_raft")) {
            boatPath = path.substring(0, path.length() - "_chest_raft".length()) + "_raft";
        } else {
            return chestBoatItem;
        }
        return BuiltInRegistries.ITEM.getOptional(Identifier.fromNamespaceAndPath(key.getNamespace(), boatPath)).orElse(chestBoatItem);
    }

}
