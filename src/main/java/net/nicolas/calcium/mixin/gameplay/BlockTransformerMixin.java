package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockTransformer.class)
public abstract class BlockTransformerMixin {

    @Inject(method = "transformBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V", shift = At.Shift.AFTER))
    private void calcium$spawnStripParticles(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir, @Local(name = "transformData") BlockTransformer.BlockTransformData transformData, @Local(name = "level") Level level, @Local(name = "pos") BlockPos pos, @Local(name = "oldBlockState") BlockState oldState) {
        if (level instanceof ServerLevel serverLevel && transformData.sound().value() == SoundEvents.AXE_STRIP.value()) {
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, oldState), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 32, 0.25, 0.25, 0.25, 0.1);
        }
    }

}
