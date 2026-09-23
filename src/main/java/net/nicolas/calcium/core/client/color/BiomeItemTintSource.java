package net.nicolas.calcium.core.client.color;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public record BiomeItemTintSource(Kind kind) implements ItemTintSource {

    public static final MapCodec<BiomeItemTintSource> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(StringRepresentable.fromEnum(Kind::values).fieldOf("kind").forGetter(BiomeItemTintSource::kind)).apply(instance, BiomeItemTintSource::new));

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        Minecraft minecraft = Minecraft.getInstance();
        BlockState state = Blocks.AIR.defaultBlockState();
        BlockTintSource source = this.kind.source();
        if (minecraft.player == null || minecraft.level == null) {
            return ARGB.opaque(source.color(state));
        }
        return ARGB.opaque(source.colorInWorld(state, minecraft.level, minecraft.player.blockPosition()));
    }

    @Override
    public MapCodec<BiomeItemTintSource> type() {
        return MAP_CODEC;
    }

    public enum Kind implements StringRepresentable {

        DIRT("dirt"),
        GRASS("grass"),
        FOLIAGE("foliage");

        private final String name;
        private @Nullable BlockTintSource source;

        Kind(String name) {
            this.name = name;
        }

        private BlockTintSource source() {
            if (this.source == null) {
                this.source = switch (this) {
                    case DIRT -> ModBlockTintSources.dirt();
                    case GRASS -> BlockTintSources.grass();
                    case FOLIAGE -> BlockTintSources.foliage();
                };
            }
            return this.source;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

    }

}
