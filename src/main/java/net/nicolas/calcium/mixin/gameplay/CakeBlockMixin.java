package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.nicolas.calcium.core.state.CakeEatingState;
import net.nicolas.calcium.block.ModBlocks;
import net.nicolas.calcium.block.custom.CandleChocolateCakeBlock;
import net.nicolas.calcium.item.ModFoods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CakeBlock.class)
public abstract class CakeBlockMixin {

    @ModifyConstant(method = "eat", constant = @Constant(intValue = 2))
    private static int calcium$modifyCakeSliceNutrition(int constant) {
        return ModFoods.CAKE_SLICE_NUTRITION;
    }

    @ModifyConstant(method = "eat", constant = @Constant(floatValue = 0.1F))
    private static float calcium$modifyCakeSliceSaturation(float constant) {
        return ModFoods.CAKE_SLICE_SATURATION;
    }

    @Inject(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;eat(IF)V", shift = At.Shift.AFTER))
    private static void calcium$spawnEatingParticles(LevelAccessor level, BlockPos pos, BlockState state, Player player, CallbackInfoReturnable<InteractionResult> cir) {
        player.spawnItemParticles(new ItemStack(state.getBlock().asItem()), 16);
    }

    @Inject(method = "useWithoutItem", at = @At("HEAD"))
    private void calcium$beginCakeEatAttempt(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        CakeEatingState.begin();
    }

    @Inject(method = "useWithoutItem", at = @At("RETURN"))
    private void calcium$endCakeEatAttempt(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        CakeEatingState.end();
    }

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void calcium$useCandleOnChocolateCake(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if ((Object) this == ModBlocks.CHOCOLATE_CAKE && itemStack.is(ItemTags.CANDLES) && state.getValue(CakeBlock.BITES) == 0 && Block.byItem(itemStack.getItem()) instanceof CandleBlock candleBlock) {
            itemStack.consume(1, player);
            level.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlockAndUpdate(pos, CandleChocolateCakeBlock.byCandle(candleBlock));
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

}