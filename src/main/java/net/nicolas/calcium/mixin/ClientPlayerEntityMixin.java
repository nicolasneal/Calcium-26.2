package net.nicolas.calcium.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.nicolas.calcium.client.EctoplasmSound;
import net.nicolas.calcium.fluid.ModFluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

    @Shadow @Final protected net.minecraft.client.MinecraftClient client;

    @Unique private boolean submergedInEctoplasm;

    @Inject(method = "updateWaterSubmersionState", at = @At("RETURN"))
    private void calcium$tickEctoplasmSounds(CallbackInfoReturnable<Boolean> cir) {

        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        boolean wasSubmerged = this.submergedInEctoplasm;
        FluidState state = player.getEntityWorld().getFluidState(BlockPos.ofFloored(player.getEyePos()));
        boolean isSubmerged = state.isOf(ModFluids.ECTOPLASM_STILL) || state.isOf(ModFluids.ECTOPLASM_FLOWING);
        this.submergedInEctoplasm = isSubmerged;

        if (!wasSubmerged && isSubmerged) {
            this.client.getSoundManager().play(new EctoplasmSound(player));
            player.getEntityWorld().playSoundClient(
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.AMBIENT_UNDERWATER_ENTER,
                SoundCategory.AMBIENT, 1.0F, 1.0F, false
            );
        }
        else if (wasSubmerged && !isSubmerged) {
            player.getEntityWorld().playSoundClient(
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.AMBIENT_UNDERWATER_EXIT,
                SoundCategory.AMBIENT, 1.0F, 1.0F, false
            );
        }

    }
}