package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.nicolas.calcium.block.ModBlocks;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {

    @Shadow @Final protected Fluid content;

    @Invoker("playEmptySound") abstract void calcium$playEmptySound(@Nullable LivingEntity user, LevelAccessor level, BlockPos pos);

    @Redirect(method = "use", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/material/Fluids;WATER:Lnet/minecraft/world/level/material/FlowingFluid;"))
    private FlowingFluid calcium$targetClickedLiquidContainer() {
        return this.content instanceof FlowingFluid flowingFluid ? flowingFluid : Fluids.WATER;
    }

    @Inject(method = "emptyContents", at = @At("HEAD"), cancellable = true)
    private void calcium$placeNonWaterLiquid(@Nullable LivingEntity user, Level level, BlockPos pos, @Nullable BlockHitResult hitResult, CallbackInfoReturnable<Boolean> cir) {

        if (this.content == Fluids.WATER || !(this.content instanceof FlowingFluid flowingFluid)) {
            return;
        }

        BlockState blockState = level.getBlockState(pos);
        if (blockState.getBlock() instanceof LiquidBlockContainer container
            && container.canPlaceLiquid(user, level, pos, blockState, this.content)
            && container.placeLiquid(level, pos, blockState, flowingFluid.getSource(false))) {
            this.calcium$playEmptySound(user, level, pos);
            cir.setReturnValue(true);
        }

    }

    @Inject(method = "emptyContents", at = @At("HEAD"), cancellable = true)
    private void calcium$evaporateEctoplasm(LivingEntity user, Level world, BlockPos pos, BlockHitResult hitResult, CallbackInfoReturnable<Boolean> cir) {

        if (this.content == ModBlocks.ECTOPLASM_STILL) {

            boolean isOverworld = world.dimension() == Level.OVERWORLD;
            boolean isTheEnd = world.dimension() == Level.END;

            if (isOverworld || isTheEnd) {
                Player playerEntity = (user instanceof Player) ? (Player) user : null;
                world.playSound(playerEntity, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 0.8F, 2.6F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.8F);
                for(int i = 0; i < 8; ++i) {
                    world.addParticle(ParticleTypes.SOUL, (double)pos.getX() + Math.random(), (double)pos.getY() + Math.random(), (double)pos.getZ() + Math.random(), 0.0, 0.0, 0.0);
                }
                cir.setReturnValue(true);
            }

        }

    }

}