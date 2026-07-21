package net.nicolas.calcium.core.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.nicolas.calcium.block.custom.MonitorBlockEntity;

public class MonitorStaticSound extends AbstractTickableSoundInstance {

    private final BlockPos pos;

    public MonitorStaticSound(BlockPos pos) {
        super(net.nicolas.calcium.sound.ModSoundGroups.MONITOR_STATIC, SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
        this.pos = pos;
        this.x = pos.getX() + 0.5;
        this.y = pos.getY() + 0.5;
        this.z = pos.getZ() + 0.5;
        this.looping = true;
        this.volume = 0.5F;
        this.attenuation = SoundInstance.Attenuation.LINEAR;
    }

    @Override public void tick() {
        var level = Minecraft.getInstance().level;
        if (level == null || !(level.getBlockEntity(pos) instanceof MonitorBlockEntity monitor)) {
            this.stop();
            return;
        }
        boolean powered = monitor.getBlockState().getValue(BlockStateProperties.POWERED);
        if (!powered || !monitor.getDisplayedItem().isEmpty()) {
            this.stop();
        }
    }

}