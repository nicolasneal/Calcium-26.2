package net.nicolas.calcium.core.client.monitor;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.nicolas.calcium.block.entity.MonitorBlockEntity;

import java.util.HashMap;
import java.util.Map;

public class MonitorStaticSoundHandler {

    private static final Map<BlockPos, MonitorStaticSound> ACTIVE = new HashMap<>();

    public static void tick(MonitorBlockEntity monitor) {

        BlockPos pos = monitor.getBlockPos();
        boolean shouldPlay = monitor.getBlockState().getValue(BlockStateProperties.POWERED) && monitor.getDisplayedItem().isEmpty();

        MonitorStaticSound existing = ACTIVE.get(pos);
        if (existing != null && existing.isStopped()) {
            ACTIVE.remove(pos);
            existing = null;
        }

        if (shouldPlay && existing == null) {
            MonitorStaticSound sound = new MonitorStaticSound(pos);
            ACTIVE.put(pos, sound);
            Minecraft.getInstance().getSoundManager().play(sound);
        }

    }

}