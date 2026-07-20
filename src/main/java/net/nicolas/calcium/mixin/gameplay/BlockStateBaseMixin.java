package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.nicolas.calcium.block.ModStrengths;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

    // Give vanilla ores the hardness (mining time) of the block they're embedded in (e.g. deepslate ores -> deepslate).
    @ModifyReturnValue(method = "getDestroySpeed", at = @At("RETURN"))
    private float calcium$matchOreHardness(float original) {
        ModStrengths.Strength strength = ModStrengths.of(((BlockState) (Object) this).getBlock());
        return strength != null ? strength.hardness() : original;
    }

}
