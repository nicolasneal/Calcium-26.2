package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public abstract class WheatCropGrowthMixin extends VegetationBlock {

    @Unique private static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    @Unique private static final int UPPER_MIN_AGE = 6;

    @Shadow public abstract void growCrops(Level level, BlockPos pos, BlockState state);

    protected WheatCropGrowthMixin(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void calcium$lowerHalfDefault(BlockBehaviour.Properties properties, CallbackInfo ci) {
        if (this.defaultBlockState().hasProperty(HALF)) {
            this.registerDefaultState(this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER));
        }
    }

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    private void calcium$addHalfProperty(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(HALF);
    }

    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void calcium$upperNeedsRipeLower(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (calcium$isUpper(state)) {
            BlockState below = level.getBlockState(pos.below());
            cir.setReturnValue(below.is(Blocks.WHEAT) && !calcium$isUpper(below) && below.getValue(CropBlock.AGE) >= UPPER_MIN_AGE);
        }
    }

    @Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
    private void calcium$upperDoesNotTick(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (calcium$isUpper(state)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void calcium$blockTickWithoutRoom(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (calcium$lacksRoom(level, pos, state)) {
            ci.cancel();
        }
    }

    @Inject(method = "randomTick", at = @At("TAIL"))
    private void calcium$topAfterTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        calcium$syncUpper(level, pos);
    }

    @Inject(method = "growCrops", at = @At("HEAD"), cancellable = true)
    private void calcium$growLowerHalfInstead(Level level, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (calcium$isUpper(state)) {
            this.growCrops(level, pos.below(), level.getBlockState(pos.below()));
            ci.cancel();
        } else if (calcium$lacksRoom(level, pos, state)) {
            ci.cancel();
        }
    }

    @Inject(method = "growCrops", at = @At("TAIL"))
    private void calcium$topAfterBonemeal(Level level, BlockPos pos, BlockState state, CallbackInfo ci) {
        calcium$syncUpper(level, pos);
    }

    @Inject(method = "isValidBonemealTarget", at = @At("HEAD"), cancellable = true)
    private void calcium$upperFollowsLower(LevelReader level, BlockPos pos, BlockState state, BonemealSource source, CallbackInfoReturnable<Boolean> cir) {
        if (calcium$isUpper(state)) {
            BlockState below = level.getBlockState(pos.below());
            cir.setReturnValue(below.getBlock() instanceof CropBlock crop && !crop.isMaxAge(below));
        }
    }

    @Unique
    private static boolean calcium$isUpper(BlockState state) {
        return state.is(Blocks.WHEAT) && state.getValue(HALF) == DoubleBlockHalf.UPPER;
    }

    @Unique
    private static boolean calcium$lacksRoom(BlockGetter level, BlockPos pos, BlockState state) {
        if (!state.is(Blocks.WHEAT) || state.getValue(CropBlock.AGE) < UPPER_MIN_AGE - 1) {
            return false;
        }
        BlockState above = level.getBlockState(pos.above());
        return !above.isAir() && !calcium$isUpper(above);
    }

    @Unique
    private static void calcium$syncUpper(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!state.is(Blocks.WHEAT) || state.getValue(CropBlock.AGE) < UPPER_MIN_AGE) {
            return;
        }
        BlockState above = level.getBlockState(pos.above());
        if (above.isAir() || calcium$isUpper(above)) {
            level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
        }
    }

}
