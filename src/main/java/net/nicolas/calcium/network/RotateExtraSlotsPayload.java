package net.nicolas.calcium.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record RotateExtraSlotsPayload() implements CustomPacketPayload {

    public static final StreamCodec<FriendlyByteBuf, RotateExtraSlotsPayload> STREAM_CODEC = StreamCodec.unit(new RotateExtraSlotsPayload());
    public static final CustomPacketPayload.Type<RotateExtraSlotsPayload> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("calcium", "rotate_extra_slots"));

    @Override public CustomPacketPayload.Type<RotateExtraSlotsPayload> type() {
        return TYPE;
    }

}
