package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.nicolas.calcium.block.ModStrengths;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

    @ModifyReturnValue(method = "getDestroySpeed", at = @At("RETURN"))
    private float calcium$matchOreHardness(float original) {
        ModStrengths.Strength strength = ModStrengths.of(((BlockState) (Object) this).getBlock());
        return strength != null ? strength.hardness() : original;
    }

    @ModifyReturnValue(method = "getLightEmission", at = @At("RETURN"))
    private int calcium$brightenMagma(int original) {
        return ((BlockState) (Object) this).getBlock() instanceof MagmaBlock ? 6 : original;
    }

    @ModifyReturnValue(method = "getLightEmission", at = @At("RETURN"))
    private int calcium$darkenMushrooms(int original) {
        return ((BlockState) (Object) this).getBlock() instanceof MushroomBlock ? 0 : original;
    }

}