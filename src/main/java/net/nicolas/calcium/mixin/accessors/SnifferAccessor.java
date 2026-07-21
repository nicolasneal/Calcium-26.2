package net.nicolas.calcium.mixin.accessors;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Sniffer.class)
public interface SnifferAccessor {

    @Accessor("DATA_STATE")
    static EntityDataAccessor<Sniffer.State> calcium$getDataState() {
        throw new UnsupportedOperationException();
    }

}
