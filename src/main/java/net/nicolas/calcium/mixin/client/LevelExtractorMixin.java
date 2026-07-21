package net.nicolas.calcium.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.extract.LevelExtractor;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.nicolas.calcium.core.client.ViewfinderController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelExtractor.class)
public abstract class LevelExtractorMixin {

    // Multi-Part Block Hitbox Unification

    @Shadow private ClientLevel level;

    @Inject(method = "extractBlockOutline", at = @At("HEAD"), cancellable = true)
    private void calcium$hideOutlineInViewfinder(Camera camera, LevelRenderState levelRenderState, CallbackInfo ci) {
        if (ViewfinderController.isActive()) {
            levelRenderState.blockOutlineRenderState = null;
            ci.cancel();
        }
    }

    @ModifyVariable(method = "extractBlockOutline", at = @At("STORE"), name = "shape")
    private VoxelShape calcium$unifyMultiPartOutline(VoxelShape shape, @Local(name = "pos") BlockPos pos, @Local(name = "state") BlockState state, @Local(name = "context") CollisionContext context) {
        BlockPos otherHalfPos = calcium$otherHalfPos(pos, state);
        if (otherHalfPos == null) {
            return shape;
        }
        VoxelShape otherHalfShape = this.level.getBlockState(otherHalfPos).getShape(this.level, otherHalfPos, context);
        return Shapes.or(shape, otherHalfShape.move(otherHalfPos.subtract(pos)));
    }

    @Unique private static BlockPos calcium$otherHalfPos(BlockPos pos, BlockState state) {
        if (state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
            return state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
        }
        if (state.hasProperty(BlockStateProperties.BED_PART)) {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            Direction towardOtherHalf = state.getValue(BlockStateProperties.BED_PART) == BedPart.FOOT ? facing : facing.getOpposite();
            return pos.relative(towardOtherHalf);
        }
        if (state.hasProperty(BlockStateProperties.EXTENDED)) {
            // Piston base: only has a second part (its head) while extended.
            return state.getValue(BlockStateProperties.EXTENDED) ? pos.relative(state.getValue(BlockStateProperties.FACING)) : null;
        }
        if (state.hasProperty(BlockStateProperties.SHORT)) {
            // Piston head: always paired with the base behind it.
            return pos.relative(state.getValue(BlockStateProperties.FACING).getOpposite());
        }
        if (state.hasProperty(BlockStateProperties.CHEST_TYPE)) {
            return state.getValue(BlockStateProperties.CHEST_TYPE) == ChestType.SINGLE ? null : ChestBlock.getConnectedBlockPos(pos, state);
        }
        return null;
    }

}