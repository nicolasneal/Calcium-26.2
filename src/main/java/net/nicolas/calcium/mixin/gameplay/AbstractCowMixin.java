package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.cow.AbstractCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.nicolas.calcium.core.util.MilkingHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCow.class)
public abstract class AbstractCowMixin extends Animal {

    @Unique private int calcium$milkCooldown;

    protected AbstractCowMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override public void aiStep() {
        super.aiStep();
        if (this.calcium$milkCooldown > 0) {
            this.calcium$milkCooldown--;
        }
    }

    @Override protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("CalciumMilkCooldown", this.calcium$milkCooldown);
    }

    @Override protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.calcium$milkCooldown = input.getIntOr("CalciumMilkCooldown", 0);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void calcium$gateMilking(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!itemStack.is(Items.BUCKET) || this.isBaby()) {
            return;
        }
        if (this.calcium$milkCooldown > 0) {
            cir.setReturnValue(InteractionResult.PASS);
            return;
        }
        this.calcium$milkCooldown = MilkingHelper.MILK_COOLDOWN_TICKS;
    }

}