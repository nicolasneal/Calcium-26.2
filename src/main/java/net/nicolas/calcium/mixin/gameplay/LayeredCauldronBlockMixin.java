package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.nicolas.calcium.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayeredCauldronBlock.class)
public abstract class LayeredCauldronBlockMixin {

    @Inject(method = "entityInside", at = @At("TAIL"))
    private void calcium$freezeInEctoplasmCauldron(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise, CallbackInfo ci) {
        if ((Object) this == ModBlocks.ECTOPLASM_CAULDRON) {
            effectApplier.apply(InsideBlockEffectType.FREEZE);
        }
    }

}
