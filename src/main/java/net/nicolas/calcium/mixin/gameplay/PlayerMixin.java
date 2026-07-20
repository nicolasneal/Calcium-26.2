package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.nicolas.calcium.screen.ExtraSlotsAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(Player.class)
public abstract class PlayerMixin implements ExtraSlotsAccess {

    @Unique private final SimpleContainer calcium$extraSlots = new SimpleContainer(3);

    @Override public SimpleContainer calcium$getExtraSlots() {
        return this.calcium$extraSlots;
    }

    @ModifyReturnValue(method = "getProjectile", at = @At("RETURN"))
    private ItemStack calcium$includeExtraSlotAmmo(ItemStack original, ItemStack weapon) {
        if (!original.isEmpty() || !(weapon.getItem() instanceof ProjectileWeaponItem projectileWeapon)) {
            return original;
        }
        Predicate<ItemStack> supportedProjectiles = projectileWeapon.getAllSupportedProjectiles();
        for (int i = 0; i < this.calcium$extraSlots.getContainerSize(); i++) {
            ItemStack stack = this.calcium$extraSlots.getItem(i);
            if (supportedProjectiles.test(stack)) {
                return stack;
            }
        }
        return original;
    }

    @Inject(method = "destroyVanishingCursedItems", at = @At("TAIL"))
    private void calcium$destroyVanishingCursedExtraSlotItems(CallbackInfo ci) {
        for (int i = 0; i < this.calcium$extraSlots.getContainerSize(); i++) {
            ItemStack itemStack = this.calcium$extraSlots.getItem(i);
            if (!itemStack.isEmpty() && EnchantmentHelper.has(itemStack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)) {
                this.calcium$extraSlots.removeItemNoUpdate(i);
            }
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void calcium$saveExtraSlots(ValueOutput output, CallbackInfo ci) {
        this.calcium$extraSlots.storeAsItemList(output.list("CalciumExtraSlots", ItemStack.CODEC));
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void calcium$loadExtraSlots(ValueInput input, CallbackInfo ci) {
        this.calcium$extraSlots.fromItemList(input.listOrEmpty("CalciumExtraSlots", ItemStack.CODEC));
    }

}