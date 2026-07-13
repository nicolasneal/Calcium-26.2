package net.nicolas.calcium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class ChorusCakeRollBlock extends CakeRollBlock {

    private static final float TELEPORT_DIAMETER = 16.0F;

    public ChorusCakeRollBlock(Properties properties) {
        super(properties);
    }

    @Override protected void calcium$onFullyEaten(Level level, BlockPos pos, Player player) {

        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        for (int attempt = 0; attempt < 16; attempt++) {
            double xx = player.getX() + (player.getRandom().nextDouble() - 0.5) * TELEPORT_DIAMETER;
            double yy = Mth.clamp(player.getY() + (player.getRandom().nextDouble() - 0.5) * TELEPORT_DIAMETER, serverLevel.getMinY(), serverLevel.getMinY() + serverLevel.getLogicalHeight() - 1);
            double zz = player.getZ() + (player.getRandom().nextDouble() - 0.5) * TELEPORT_DIAMETER;
            if (player.isPassenger()) {
                player.stopRiding();
            }
            Vec3 oldPos = player.position();
            if (player.randomTeleport(xx, yy, zz, true)) {
                serverLevel.gameEvent(GameEvent.TELEPORT, oldPos, GameEvent.Context.of(player));
                serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS);
                player.resetFallDistance();
                player.resetCurrentImpulseContext();
                break;
            }
        }

    }

}