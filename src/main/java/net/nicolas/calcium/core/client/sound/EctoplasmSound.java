package net.nicolas.calcium.core.client.sound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.nicolas.calcium.block.ModBlocks;

@Environment(EnvType.CLIENT)
public class EctoplasmSound extends AbstractTickableSoundInstance {
    private final LocalPlayer player;
    private int transitionTimer;

    public EctoplasmSound(LocalPlayer player) {
        super(SoundEvents.AMBIENT_UNDERWATER_LOOP, SoundSource.AMBIENT, net.minecraft.util.RandomSource.create());
        this.player = player;
        this.looping = true;
        this.delay = 0;
        this.volume = 1.0F;
        this.relative = true;
    }

    @Override public void tick() {
        net.minecraft.world.level.material.FluidState state = this.player.level().getFluidState(net.minecraft.core.BlockPos.containing(this.player.getEyePosition()));
        if (this.player.isRemoved() || (!state.is(ModBlocks.ECTOPLASM_STILL) && !state.is(ModBlocks.ECTOPLASM_FLOWING))) {
            this.stop();
            return;
        }
        if (this.transitionTimer < 80) {
            this.transitionTimer++;
        }
        this.volume = Math.clamp((float) this.transitionTimer / 40.0F, 0.0F, 1.0F);
    }

}