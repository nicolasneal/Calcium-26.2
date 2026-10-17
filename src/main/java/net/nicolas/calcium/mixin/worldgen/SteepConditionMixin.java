package net.nicolas.calcium.mixin.worldgen;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.levelgen.material.MaterialRuleContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.world.level.levelgen.material.condition.SteepCondition$1")
public abstract class SteepConditionMixin extends MaterialRuleContext.LazyXZCondition {

    protected SteepConditionMixin(MaterialRuleContext context) {
        super(context);
    }

    @ModifyReturnValue(method = "compute", at = @At("RETURN"))
    private boolean calcium$fixSteepness(boolean original) {
        return Math.abs(this.context.surfaceGradientX()) >= 4 || Math.abs(this.context.surfaceGradientZ()) >= 4;
    }

}