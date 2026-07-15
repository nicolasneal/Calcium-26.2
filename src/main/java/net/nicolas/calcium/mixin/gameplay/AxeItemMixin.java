package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {

    @Inject(method = "evaluateNewBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V", shift = At.Shift.AFTER))
    private void calcium$spawnStripParticles(Level level, BlockPos pos, Player player, BlockState oldState, CallbackInfoReturnable<Optional<BlockState>> cir) {
        if (!level.isClientSide()) {
            ((ServerLevel) level).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, oldState), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 32, 0.25, 0.25, 0.25, 0.1);
        }
    }

}