package net.nicolas.calcium.mixin;

import net.minecraft.core.Holder;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.nicolas.calcium.screen.CustomBeaconScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin extends ServerCommonPacketListenerImpl {

    @Shadow public ServerPlayer player;

    public ServerGamePacketListenerImplMixin(MinecraftServer server, Connection connection, CommonListenerCookie cookie) {
        super(server, connection, cookie);
    }

    @Inject(method = "handleSetBeaconPacket", at = @At("TAIL"))
    private void calcium$handleCustomBeaconSetEffects(ServerboundSetBeaconPacket packet, CallbackInfo ci) {

        if (!(this.player.containerMenu instanceof CustomBeaconScreenHandler menu)) {
            return;
        }

        if (!menu.stillValid(this.player)) {
            return;
        }

        Holder<MobEffect> primary = packet.primary().orElse(null);
        Holder<MobEffect> secondary = packet.secondary().orElse(null);

        if (!BeaconBlockEntity.validateEffects(primary, secondary, menu.getProperties())) {
            this.disconnect(Component.translatable("multiplayer.disconnect.generic"));
            return;
        }

        menu.setEffects(packet.primary(), packet.secondary());

    }

}
