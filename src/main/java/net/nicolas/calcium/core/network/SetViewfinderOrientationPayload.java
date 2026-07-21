package net.nicolas.calcium.core.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public record SetViewfinderOrientationPayload(BlockPos pos, float yaw, float pitch, float zoom, Optional<BlockPos> controllerPos) implements CustomPacketPayload {

    public static final StreamCodec<FriendlyByteBuf, SetViewfinderOrientationPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, SetViewfinderOrientationPayload::pos,
            ByteBufCodecs.FLOAT, SetViewfinderOrientationPayload::yaw,
            ByteBufCodecs.FLOAT, SetViewfinderOrientationPayload::pitch,
            ByteBufCodecs.FLOAT, SetViewfinderOrientationPayload::zoom,
            ByteBufCodecs.optional(BlockPos.STREAM_CODEC), SetViewfinderOrientationPayload::controllerPos,
            SetViewfinderOrientationPayload::new
    );
    public static final CustomPacketPayload.Type<SetViewfinderOrientationPayload> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("calcium", "set_viewfinder_orientation"));

    @Override public CustomPacketPayload.Type<SetViewfinderOrientationPayload> type() {
        return TYPE;
    }

}
