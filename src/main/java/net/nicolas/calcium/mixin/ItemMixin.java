package net.nicolas.calcium.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Shadow
    protected static BlockHitResult getPlayerPOVHitResult(Level world, Player player, ClipContext.Fluid fluidHandling) {
        throw new AssertionError();
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void onUseBowl(Level world, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {

        if ((Object) this != Items.BOWL) {
            return;
        }

        ItemStack itemStack = user.getItemInHand(hand);

        BlockHitResult hitResult = getPlayerPOVHitResult(world, user, ClipContext.Fluid.SOURCE_ONLY);

        if (hitResult.getType() == HitResult.Type.MISS) {
            return;
        }

        if (hitResult.getType() == HitResult.Type.BLOCK) {

            var blockPos = hitResult.getBlockPos();
            if (!world.mayInteract(user, blockPos)) {
                return;
            }
            if (world.getFluidState(blockPos).is(FluidTags.WATER)) {
                world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
                world.gameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
                Item waterBowl = BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath("calcium", "water_bowl"));
                ItemStack filledStack = ItemUtils.createFilledResult(itemStack, user, new ItemStack(waterBowl));
                user.awardStat(Stats.ITEM_USED.get((Item) (Object) this));
                cir.setReturnValue(InteractionResult.SUCCESS.heldItemTransformedTo(filledStack));
            }

        }

    }

}