package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.nicolas.calcium.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IceBlock.class)
public abstract class IceBlockMixin {

    @Unique private static final int ECTOPLASM_LIGHT = 12;

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void calcium$ignoreEctoplasmLight(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        int brightness = level.getBrightness(LightLayer.BLOCK, pos);
        if (brightness > 11 - state.getLightDampening() && calcium$ectoplasmAccountsForLight(level, pos, brightness)) {
            ci.cancel();
        }
    }

    @Unique
    private static boolean calcium$ectoplasmAccountsForLight(ServerLevel level, BlockPos pos, int brightness) {
        int reach = ECTOPLASM_LIGHT - brightness;
        for (BlockPos nearby : BlockPos.betweenClosed(pos.offset(-reach, -reach, -reach), pos.offset(reach, reach, reach))) {
            if (nearby.distManhattan(pos) <= reach && calcium$emitsEctoplasmLight(level, nearby)) {
                return true;
            }
        }
        return false;
    }

    @Unique
    private static boolean calcium$emitsEctoplasmLight(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.is(ModBlocks.ECTOPLASM) || state.is(ModBlocks.ECTOPLASM_CAULDRON) || level.getFluidState(pos).getType().isSame(ModBlocks.ECTOPLASM_STILL);
    }

}