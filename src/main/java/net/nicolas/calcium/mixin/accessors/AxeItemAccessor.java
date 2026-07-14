package net.nicolas.calcium.mixin.accessors;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(AxeItem.class)
public interface AxeItemAccessor {

    @Accessor("STRIPPABLES")
    static Map<Block, Block> calcium$getStrippables() {
        throw new AssertionError();
    }

    @Accessor("STRIPPABLES") @Mutable
    static void calcium$setStrippables(Map<Block, Block> strippables) {
        throw new AssertionError();
    }

}
