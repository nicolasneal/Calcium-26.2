package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.gamerules.GameRules;
import net.nicolas.calcium.screen.ExtraSlotsAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

    @Inject(method = "restoreFrom", at = @At("TAIL"))
    private void calcium$restoreExtraSlots(ServerPlayer oldPlayer, boolean restoreAll, CallbackInfo ci) {
        if (restoreAll || (Boolean) oldPlayer.level().getGameRules().get(GameRules.KEEP_INVENTORY) || oldPlayer.isSpectator()) {
            SimpleContainer extraSlots = ((ExtraSlotsAccess) this).calcium$getExtraSlots();
            SimpleContainer oldExtraSlots = ((ExtraSlotsAccess) oldPlayer).calcium$getExtraSlots();
            for (int i = 0; i < extraSlots.getContainerSize(); i++) {
                extraSlots.setItem(i, oldExtraSlots.getItem(i));
            }
        }
    }

}