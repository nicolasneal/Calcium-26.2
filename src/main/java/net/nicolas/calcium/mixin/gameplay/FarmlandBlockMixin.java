package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin {

    @Invoker("isNearWater") private static boolean invokeIsWaterNearby(LevelReader world, BlockPos pos) {
        throw new AssertionError();
    }

    @Invoker("shouldMaintainFarmland") private static boolean invokeHasCrop(BlockGetter world, BlockPos pos) {
        throw new AssertionError();
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void onRandomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {

        int i = state.getValue(FarmlandBlock.MOISTURE);

        if (!invokeIsWaterNearby(world, pos) && !world.isRainingAt(pos.above())) {
            if (i > 0) {
                world.setBlock(pos, state.setValue(FarmlandBlock.MOISTURE, i - 1), 2);
            } else if (!invokeHasCrop(world, pos)) {
                FarmlandBlock.turnToDirt(null, state, world, pos);
            }
        } else if (i < 7) {
            world.setBlock(pos, state.setValue(FarmlandBlock.MOISTURE, i + 1), 2);
        }

        ci.cancel();

    }

}