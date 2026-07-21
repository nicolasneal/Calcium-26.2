package net.nicolas.calcium.mixin.client;

import net.minecraft.client.renderer.entity.state.SnifferRenderState;
import net.minecraft.world.item.ItemStack;
import net.nicolas.calcium.core.client.SnifferChestRenderAccess;
import net.nicolas.calcium.core.client.SnifferSaddleAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SnifferRenderState.class)
public class SnifferRenderStateMixin implements SnifferSaddleAccess, SnifferChestRenderAccess {

    @Unique private ItemStack calcium$saddle = ItemStack.EMPTY;
    @Unique private boolean calcium$hasChest;

    @Override public ItemStack calcium$getSaddle() {
        return this.calcium$saddle;
    }
    @Override public void calcium$setSaddle(ItemStack stack) {
        this.calcium$saddle = stack;
    }
    @Override public boolean calcium$hasChest() {
        return this.calcium$hasChest;
    }
    @Override public void calcium$setHasChest(boolean hasChest) {
        this.calcium$hasChest = hasChest;
    }

}
