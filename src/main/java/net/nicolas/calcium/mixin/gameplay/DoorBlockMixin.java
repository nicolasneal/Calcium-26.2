package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DoorBlock.class)
public abstract class DoorBlockMixin {

    @ModifyReturnValue(method = "canSurvive", at = @At("RETURN"))
    private boolean calcium$noSupportNeeded(boolean original, BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

}