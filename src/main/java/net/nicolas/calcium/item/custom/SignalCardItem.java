package net.nicolas.calcium.item.custom;

import net.minecraft.core.GlobalPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.nicolas.calcium.block.entity.ViewfinderBlockEntity;
import net.nicolas.calcium.item.ModItems;

public class SignalCardItem extends Item {

    public SignalCardItem(Properties properties) {
        super(properties);
    }

    @Override public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockEntity be = level.getBlockEntity(context.getClickedPos());
        if (be instanceof ViewfinderBlockEntity) {
            if (!level.isClientSide()) {
                GlobalPos linked = GlobalPos.of(level.dimension(), context.getClickedPos());
                context.getItemInHand().set(ModItems.LINKED_FEED, linked);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

    @Override public boolean isFoil(ItemStack stack) {
        return super.isFoil(stack) || stack.has(ModItems.LINKED_FEED);
    }

}