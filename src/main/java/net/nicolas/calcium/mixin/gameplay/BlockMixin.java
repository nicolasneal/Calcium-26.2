package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.nicolas.calcium.block.ModStrengths;
import net.nicolas.calcium.sound.ModSoundGroups;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin {

    // Give vanilla ores the blast resistance of the block they're embedded in (e.g. deepslate ores -> deepslate).
    @ModifyReturnValue(method = "getExplosionResistance", at = @At("RETURN"))
    private float calcium$matchOreBlastResistance(float original) {
        ModStrengths.Strength strength = ModStrengths.of((Block) (Object) this);
        return strength != null ? strength.resistance() : original;
    }

    // Vanilla Chorus Flower has no ambient sound; mirrors EctoplasmFluid's occasional whisper pattern.
    @Inject(method = "animateTick", at = @At("HEAD"))
    private void calcium$chorusFlowerIdleSound(BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if ((Object) this instanceof ChorusFlowerBlock && random.nextInt(20) == 0) {
            level.playSound(null, pos, ModSoundGroups.CHORUS_FLOWER_IDLE, SoundSource.BLOCKS, 1.0F, 0.9F + random.nextFloat() * 0.15F);
        }
    }

}
