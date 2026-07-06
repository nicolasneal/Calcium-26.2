package net.nicolas.calcium.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.nicolas.calcium.screen.CustomEnchantingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlockMixin {

    @Inject(method = "getMenuProvider", at = @At("HEAD"), cancellable = true)
    private void openCustomScreen(BlockState state, Level world, BlockPos pos, CallbackInfoReturnable<MenuProvider> cir) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof EnchantingTableBlockEntity tableEntity) {
            Component displayName = tableEntity.getDisplayName();
            MenuProvider factory = new SimpleMenuProvider((syncId, inv, player) -> new CustomEnchantingScreenHandler(syncId, inv), displayName);
            cir.setReturnValue(factory);
        }

    }

}