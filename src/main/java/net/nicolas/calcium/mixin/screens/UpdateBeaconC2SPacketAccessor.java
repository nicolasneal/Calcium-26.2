package net.nicolas.calcium.mixin.screens;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
import net.minecraft.world.effect.MobEffect;

@Mixin(ServerboundSetBeaconPacket.class)
public interface UpdateBeaconC2SPacketAccessor {

    @Accessor("primary")
    Optional<Holder<MobEffect>> getPrimary();

    @Accessor("secondary")
    Optional<Holder<MobEffect>> getSecondary();

}