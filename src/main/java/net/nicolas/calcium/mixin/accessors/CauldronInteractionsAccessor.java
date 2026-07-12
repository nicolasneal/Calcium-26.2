package net.nicolas.calcium.mixin.accessors;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Predicate;

@Mixin(CauldronInteractions.class)
public interface CauldronInteractionsAccessor {

    @Invoker("fillBucket")
    static InteractionResult calcium$fillBucket(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack itemInHand, ItemStack newItem, Predicate<BlockState> canFill, SoundEvent soundEvent) {
        throw new AssertionError();
    }

    @Invoker("emptyBucket")
    static InteractionResult calcium$emptyBucket(Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack itemInHand, BlockState newState, SoundEvent soundEvent) {
        throw new AssertionError();
    }

}