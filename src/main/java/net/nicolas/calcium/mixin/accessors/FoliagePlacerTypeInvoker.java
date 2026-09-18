package net.nicolas.calcium.mixin.accessors;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FoliagePlacerType.class)
public interface FoliagePlacerTypeInvoker {

    @Invoker("<init>") static <P extends FoliagePlacer> FoliagePlacerType<P> calcium$create(MapCodec<P> codec) {
        throw new AssertionError();
    }

}
