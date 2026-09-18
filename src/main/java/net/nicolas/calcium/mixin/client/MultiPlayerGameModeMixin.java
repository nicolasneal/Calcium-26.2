package net.nicolas.calcium.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {

    @Unique
    private static final int CALCIUM$INSTAMINE_HOLD_TICKS = 20;

    @Shadow @Final private Minecraft minecraft;

    @Shadow private int destroyDelay;

    @Unique
    private int calcium$holdTicks;

    @Inject(method = "tick", at = @At("HEAD"))
    private void calcium$trackMiningKeyHold(final CallbackInfo ci) {
        this.calcium$holdTicks = this.minecraft.options.keyAttack.isDown() ? this.calcium$holdTicks + 1 : 0;
    }

    @Inject(method = "continueDestroyBlock", at = @At("HEAD"))
    private void calcium$removeCreativeMiningDelay(final BlockPos pos, final Direction direction, final CallbackInfoReturnable<Boolean> cir) {
        if (this.calcium$holdTicks >= CALCIUM$INSTAMINE_HOLD_TICKS && this.minecraft.player.getAbilities().instabuild) {
            this.destroyDelay = 0;
        }
    }

}
