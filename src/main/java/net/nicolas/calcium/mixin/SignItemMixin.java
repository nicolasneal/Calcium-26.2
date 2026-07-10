package net.nicolas.calcium.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SignItem.class)
public abstract class SignItemMixin {

    @Redirect(method = "updateCustomBlockEntityTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SignBlock;openTextEdit(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/block/entity/SignBlockEntity;Z)V"))
    private void calcium$dontAutoOpenSignEditOnPlace(SignBlock sign, Player player, SignBlockEntity signEntity, boolean isFrontText) {}

}
