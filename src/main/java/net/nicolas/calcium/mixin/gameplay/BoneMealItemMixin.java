package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.nicolas.calcium.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public abstract class BoneMealItemMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void calcium$spreadAlgalSand(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!level.getBlockState(pos).is(Blocks.SAND) || !level.getFluidState(pos.above()).is(FluidTags.WATER) || !hasAdjacentAlgalSand(level, pos)) {
            return;
        }

        ItemStack stack = context.getItemInHand();
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.setBlockAndUpdate(pos, ModBlocks.ALGAL_SAND.defaultBlockState());
            stack.shrink(1);
            stack.causeUseVibration(context.getPlayer(), GameEvent.ITEM_INTERACT_FINISH);
            level.levelEvent(1505, pos, 15);
        }

        cir.setReturnValue(InteractionResult.SUCCESS);
    }

    private static boolean hasAdjacentAlgalSand(Level level, BlockPos pos) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if ((x != 0 || z != 0) && level.getBlockState(pos.offset(x, 0, z)).is(ModBlocks.ALGAL_SAND)) {
                    return true;
                }
            }
        }
        return false;
    }

}