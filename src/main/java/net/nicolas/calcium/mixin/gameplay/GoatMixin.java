package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.goat.Goat;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Goat.class)
public abstract class GoatMixin extends Animal {

    @Unique private int calcium$milkCooldown;

    protected GoatMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void calcium$tickMilkCooldown(CallbackInfo ci) {
        if (this.calcium$milkCooldown > 0) {
            this.calcium$milkCooldown--;
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void calcium$saveMilkCooldown(ValueOutput output, CallbackInfo ci) {
        output.putInt("CalciumMilkCooldown", this.calcium$milkCooldown);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void calcium$loadMilkCooldown(ValueInput input, CallbackInfo ci) {
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