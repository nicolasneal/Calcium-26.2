package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.nicolas.calcium.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityFluidInteraction.class)
public abstract class EntityFluidInteractionMixin {

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityFluidInteraction;getOrCreateTrackerFor(Lnet/minecraft/core/Holder;)Lnet/minecraft/world/entity/EntityFluidInteraction$Tracker;"))
    private Holder<Fluid> calcium$treatEctoplasmAsLava(Holder<Fluid> fluid) {
        if (fluid.value() == ModBlocks.ECTOPLASM_STILL || fluid.value() == ModBlocks.ECTOPLASM_FLOWING) {
            return BuiltInRegistries.FLUID.wrapAsHolder(Fluids.LAVA);
        }
        return fluid;
    }

}