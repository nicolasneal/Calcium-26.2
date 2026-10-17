package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.TorchflowerCropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TorchflowerCropBlock.class)
public abstract class TorchflowerCropStageMixin extends CropBlock {

    @Shadow @Final @Mutable public static IntegerProperty AGE;

    protected TorchflowerCropStageMixin(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void calcium$threeStages(CallbackInfo ci) {
        AGE = BlockStateProperties.AGE_2;
    }

    @Inject(method = "getStateForAge", at = @At("HEAD"), cancellable = true)
    private void calcium$keepCropAtFinalStage(int age, CallbackInfoReturnable<BlockState> cir) {
        if (age == this.getMaxAge()) {
            cir.setReturnValue(this.defaultBlockState().setValue(AGE, age));
        }
    }

}