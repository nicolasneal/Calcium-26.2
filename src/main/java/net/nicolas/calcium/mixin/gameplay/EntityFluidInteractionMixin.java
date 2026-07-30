package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.nicolas.calcium.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityFluidInteraction.class)
public abstract class EntityFluidInteractionMixin {

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityFluidInteraction;getTrackerFor(Lnet/minecraft/world/level/material/Fluid;)Lnet/minecraft/world/entity/EntityFluidInteraction$Tracker;"))
    private Fluid calcium$treatEctoplasmAsLava(Fluid fluidType) {
        if (fluidType == ModBlocks.ECTOPLASM_STILL || fluidType == ModBlocks.ECTOPLASM_FLOWING) {
            return Fluids.LAVA;
        }
        return fluidType;
    }

}
