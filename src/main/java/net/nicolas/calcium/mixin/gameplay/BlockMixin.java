package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.Block;
import net.nicolas.calcium.block.BlockStrength;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Block.class)
public abstract class BlockMixin {

    // Give vanilla ores the blast resistance of the block they're embedded in (e.g. deepslate ores -> deepslate).
    @ModifyReturnValue(method = "getExplosionResistance", at = @At("RETURN"))
    private float calcium$matchOreBlastResistance(float original) {
        BlockStrength.Strength strength = BlockStrength.of((Block) (Object) this);
        return strength != null ? strength.resistance() : original;
    }

}
