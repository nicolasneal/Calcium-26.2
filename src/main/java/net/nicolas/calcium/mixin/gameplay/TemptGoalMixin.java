package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.world.entity.ai.goal.TemptGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(TemptGoal.class)
public abstract class TemptGoalMixin {

    @ModifyConstant(method = "stop", constant = @Constant(intValue = 100))
    private int calcium$removeTemptCalmDown(int original) {
        return 0;
    }

}