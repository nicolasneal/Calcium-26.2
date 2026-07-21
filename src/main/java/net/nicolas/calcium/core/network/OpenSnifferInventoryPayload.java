package net.nicolas.calcium.core.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record OpenSnifferInventoryPayload(int containerId, int entityId) implements CustomPacketPayload {

    public static final StreamCodec<FriendlyByteBuf, OpenSnifferInventoryPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, OpenSnifferInventoryPayload::containerId,
            ByteBufCodecs.VAR_INT, OpenSnifferInventoryPayload::entityId,
            OpenSnifferInventoryPayload::new
    );
    public static final CustomPacketPayload.Type<OpenSnifferInventoryPayload> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("calcium", "open_sniffer_inventory"));

    @Override public CustomPacketPayload.Type<OpenSnifferInventoryPayload> type() {
        return TYPE;
    }

}
