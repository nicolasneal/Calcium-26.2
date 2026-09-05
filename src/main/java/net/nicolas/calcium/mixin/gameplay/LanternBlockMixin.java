package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LanternBlock.class)
public abstract class LanternBlockMixin {

    @ModifyReturnValue(method = "canSurvive", at = @At("RETURN"))
    private boolean calcium$noSupportNeeded(boolean original, BlockState state, LevelReader level, BlockPos pos) {
        return !state.getValue(LanternBlock.HANGING) || original;
    }

}