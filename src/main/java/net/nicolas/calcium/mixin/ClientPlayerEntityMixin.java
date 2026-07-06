package net.nicolas.calcium.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.material.FluidState;
import net.nicolas.calcium.client.EctoplasmSound;
import net.nicolas.calcium.fluid.ModFluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public abstract class ClientPlayerEntityMixin {

    @Shadow @Final protected net.minecraft.client.Minecraft minecraft;

    @Unique private boolean submergedInEctoplasm;

    @Inject(method = "updateIsUnderwater", at = @At("RETURN"))
    private void calcium$tickEctoplasmSounds(CallbackInfoReturnable<Boolean> cir) {

        LocalPlayer player = (LocalPlayer) (Object) this;
        boolean wasSubmerged = this.submergedInEctoplasm;
        FluidState state = player.level().getFluidState(BlockPos.containing(player.getEyePosition()));
        boolean isSubmerged = state.is(ModFluids.ECTOPLASM_STILL) || state.is(ModFluids.ECTOPLASM_FLOWING);
        this.submergedInEctoplasm = isSubmerged;

        if (!wasSubmerged && isSubmerged) {
            this.minecraft.getSoundManager().play(new EctoplasmSound(player));
            player.level().playLocalSound(
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.AMBIENT_UNDERWATER_ENTER,
                SoundSource.AMBIENT, 1.0F, 1.0F, false
            );
        }
        else if (wasSubmerged && !isSubmerged) {
            player.level().playLocalSound(
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.AMBIENT_UNDERWATER_EXIT,
                SoundSource.AMBIENT, 1.0F, 1.0F, false
            );
        }

    }

}