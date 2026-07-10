package net.nicolas.calcium.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin {

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void onUseWithItem(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {

        if (state.getValue(CampfireBlock.LIT)) return;
        if (state.getValue(CampfireBlock.WATERLOGGED)) return;

        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.STICK)) {
            world.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);

            if (!world.isClientSide()) {
                if (world.getRandom().nextFloat() < 0.30F) {
                    world.setBlockAndUpdate(pos, state.setValue(CampfireBlock.LIT, true));
                }
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
            }

            cir.setReturnValue(InteractionResult.SUCCESS);
            cir.cancel();

        }

    }

    @ModifyReturnValue(method = "getStateForPlacement(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", at = @At("RETURN"))
    private BlockState calcium$unlitCampfirePlacementState(BlockState originalState) {
        return originalState.setValue(CampfireBlock.LIT, false);
    }

}