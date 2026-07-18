package net.nicolas.calcium.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.nicolas.calcium.block.custom.ViewfinderBlockEntity;
import net.nicolas.calcium.network.SetViewfinderOrientationPayload;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class ViewfinderController {

    private static final float MIN_ZOOM = 1.0F;
    private static final float MAX_ZOOM = 10.0F;
    private static final float ZOOM_STEP = 0.1F;
    private static final int SYNC_INTERVAL_TICKS = 4;

    private static @Nullable BlockPos activePos;
    private static @Nullable Level activeLevel;
    private static @Nullable BlockPos controllingMonitorPos;
    private static @Nullable CameraType previousCameraType;
    private static float panYaw;
    private static float panPitch;
    private static float zoom = MIN_ZOOM;
    private static int ticksSinceLastSync;

    public static void startLooking(ViewfinderBlockEntity viewfinder) {
        startLooking(viewfinder, null);
    }

    public static void startLooking(ViewfinderBlockEntity viewfinder, @Nullable BlockPos controllingMonitorPos) {
        if (isActive()) {
            return;
        }
        activePos = viewfinder.getBlockPos();
        activeLevel = viewfinder.getLevel();
        ViewfinderController.controllingMonitorPos = controllingMonitorPos;
        panYaw = viewfinder.getViewYaw();
        panPitch = viewfinder.getViewPitch();
        zoom = viewfinder.getZoom();
        ticksSinceLastSync = 0;
        previousCameraType = Minecraft.getInstance().options.getCameraType();
        Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
    }

    public static void tick() {
        if (!isActive()) {
            return;
        }
        ticksSinceLastSync++;
        if (ticksSinceLastSync >= SYNC_INTERVAL_TICKS) {
            ticksSinceLastSync = 0;
            sendOrientationUpdate();
        }
    }

    private static void sendOrientationUpdate() {
        if (activePos != null) {
            ClientPlayNetworking.send(new SetViewfinderOrientationPayload(activePos, panYaw, panPitch, zoom, Optional.ofNullable(controllingMonitorPos)));
        }
    }

    public static void addMouseDelta(double xo, double yo) {
        float xDelta = (float) yo * 0.15F;
        float yDelta = (float) xo * 0.15F;
        panPitch = Mth.clamp(panPitch + xDelta, -90.0F, 90.0F);
        panYaw += yDelta;
    }

    public static void addZoom(double delta) {
        zoom = Mth.clamp(zoom + (float) Math.signum(delta) * ZOOM_STEP, MIN_ZOOM, MAX_ZOOM);
    }

    public static float getPanYaw() {
        return panYaw;
    }

    public static float getPanPitch() {
        return panPitch;
    }

    public static float getZoom() {
        return zoom;
    }

    public static void stopLooking() {
        sendOrientationUpdate();
        activePos = null;
        activeLevel = null;
        controllingMonitorPos = null;
        if (previousCameraType != null) {
            Minecraft.getInstance().options.setCameraType(previousCameraType);
            previousCameraType = null;
        }
    }

    public static boolean isActive() {
        return activePos != null;
    }

    public static @Nullable ViewfinderBlockEntity getActiveViewfinder() {
        if (activeLevel == null || activePos == null) {
            return null;
        }
        if (activeLevel.getBlockEntity(activePos) instanceof ViewfinderBlockEntity viewfinder) {
            return viewfinder;
        }
        return null;
    }

}