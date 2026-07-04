package net.nicolas.calcium.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.nicolas.calcium.fluid.ModFluids;

@Environment(EnvType.CLIENT)
public class EctoplasmSound extends MovingSoundInstance {
    private final ClientPlayerEntity player;
    private int transitionTimer;

    public EctoplasmSound(ClientPlayerEntity player) {
        super(SoundEvents.AMBIENT_UNDERWATER_LOOP, SoundCategory.AMBIENT, net.minecraft.util.math.random.Random.create());
        this.player = player;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 1.0F;
        this.relative = true;
    }

    @Override public void tick() {
        net.minecraft.fluid.FluidState state = this.player.getEntityWorld().getFluidState(net.minecraft.util.math.BlockPos.ofFloored(this.player.getEyePos()));
        if (this.player.isRemoved() || (!state.isOf(ModFluids.ECTOPLASM_STILL) && !state.isOf(ModFluids.ECTOPLASM_FLOWING))) {
            this.setDone();
            return;
        }
        if (this.transitionTimer < 80) {
            this.transitionTimer++;
        }
        this.volume = Math.max(0.0F, Math.min((float)this.transitionTimer / 40.0F, 1.0F));
    }
}