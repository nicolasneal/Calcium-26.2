package net.nicolas.calcium.mixin.accessors;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemTintSources.class)
public interface ItemTintSourcesAccessor {

    @Accessor("ID_MAPPER")
    static ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends ItemTintSource>> calcium$getIdMapper() {
        throw new AssertionError();
    }

}