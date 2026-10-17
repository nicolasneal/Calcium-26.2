package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignTextSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SignBlock.class)
public abstract class SignBlockMixin {

    @Redirect(method = "setPlacedBy", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SignBlock;openTextEdit(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/block/entity/SignBlockEntity;Lnet/minecraft/world/level/block/entity/SignTextSlot;)V"))
    private void calcium$dontAutoOpenSignEditOnPlace(SignBlock sign, Player player, SignBlockEntity signEntity, SignTextSlot slot) {}

}