package net.nicolas.calcium.mixin.gameplay.waxing;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SpeleothemBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.SpeleothemThickness;
import net.nicolas.calcium.event.CropWaxing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpeleothemBlock.class)
public abstract class SpeleothemBlockMixin extends Block implements CropWaxing.Waxable {

    protected SpeleothemBlockMixin(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void calcium$addWaxedDefault(BlockState blockToGrowOn, BlockBehaviour.Properties properties, CallbackInfo ci) {
        this.registerDefaultState(this.defaultBlockState().setValue(WAXED, false));
    }

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    private void calcium$addWaxedProperty(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(WAXED);
    }

    @Override public boolean canBeWaxed(BlockState state) {
        return state.getValue(SpeleothemBlock.THICKNESS) == SpeleothemThickness.TIP;
    }

    @ModifyReturnValue(method = "updateShape", at = @At("RETURN"))
    private BlockState calcium$clearWaxWhenNoLongerTip(BlockState original) {
        if (original.is(this) && original.getValue(WAXED) && !this.canBeWaxed(original)) {
            return original.setValue(WAXED, false);
        }
        return original;
    }

    @Inject(method = "grow", at = @At("HEAD"), cancellable = true)
    private void calcium$stopGrowthWhileWaxed(ServerLevel level, BlockPos growFromPos, Direction growToDirection, CallbackInfo ci) {
        if (this.calcium$isWaxedTip(level.getBlockState(growFromPos)) || this.calcium$isWaxedTip(level.getBlockState(growFromPos.relative(growToDirection)))) {
            ci.cancel();
        }
    }

    @Inject(method = "growStalagmiteBelow", at = @At("HEAD"), cancellable = true)
    private void calcium$stopStalagmiteWhileStalactiteWaxed(ServerLevel level, BlockPos posAboveStalagmite, CallbackInfo ci) {
        if (this.calcium$isWaxedTip(level.getBlockState(posAboveStalagmite))) {
            ci.cancel();
        }
    }

    @Unique
    private boolean calcium$isWaxedTip(BlockState state) {
        return state.is(this) && state.getValue(WAXED);
    }

}