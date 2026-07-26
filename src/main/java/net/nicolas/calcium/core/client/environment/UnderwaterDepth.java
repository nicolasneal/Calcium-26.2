package net.nicolas.calcium.core.client.environment;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;

public final class UnderwaterDepth {

    private static final int MAX_SCAN_DEPTH = 96;
    private static final int DARKENING_START_DEPTH = 15;

    private static BlockPos cachedPos = null;
    private static float cachedFactor = 0.0F;

    private UnderwaterDepth() {
    }

    public static float computeFactor(Camera camera, ClientLevel level) {
        BlockPos pos = camera.blockPosition();
        if (pos.equals(cachedPos)) {
            return cachedFactor;
        }

        BlockPos.MutableBlockPos scanPos = pos.mutable();
        int depth = 0;
        while (depth < MAX_SCAN_DEPTH && !level.getBlockState(scanPos).isAir()) {
            depth++;
            scanPos.move(0, 1, 0);
        }
        float progress = (depth - DARKENING_START_DEPTH) / (float) (MAX_SCAN_DEPTH - DARKENING_START_DEPTH);
        cachedFactor = Mth.clamp(progress, 0.0F, 1.0F);
        cachedPos = pos.immutable();
        return cachedFactor;
    }

}
