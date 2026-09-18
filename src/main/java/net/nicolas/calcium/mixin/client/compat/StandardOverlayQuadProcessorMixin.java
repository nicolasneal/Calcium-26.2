package net.nicolas.calcium.mixin.client.compat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pepperbell.continuity.client.processor.overlay.StandardOverlayQuadProcessor;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StandardOverlayQuadProcessor.class)
public abstract class StandardOverlayQuadProcessorMixin {

    @ModifyExpressionValue(method = "appliesOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isCollisionShapeFullBlock(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean calcium$allowNonFullConnectingBlocks(boolean original) {
        return true;
    }

    @Inject(method = "appliesOverlay", at = @At("HEAD"), cancellable = true)
    private void calcium$onlyDirectlyAbove(BlockPos otherPos, BlockState otherAppearanceState, BlockState otherState, BlockAndTintGetter level, BlockPos pos, BlockState state, BlockState appearanceState, Direction face, TextureAtlasSprite sprite, CallbackInfoReturnable<Boolean> cir) {
        if (otherState.is(Blocks.SNOW) && !otherPos.equals(pos.above())) {
            cir.setReturnValue(false);
        }
    }

}
