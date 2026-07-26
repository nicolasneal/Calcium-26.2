package net.nicolas.calcium.mixin.gameplay;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.nicolas.calcium.core.state.EnchantingTableLapisStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantingTableBlockEntity.class)
public abstract class EnchantingTableBlockEntityMixin extends BlockEntity implements EnchantingTableLapisStorage {

    @Unique private ItemStack calcium$lapis = ItemStack.EMPTY;

    private EnchantingTableBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override public ItemStack calcium$getLapis() {
        return this.calcium$lapis;
    }

    @Override public void calcium$setLapis(ItemStack stack) {
        this.calcium$lapis = stack;
        this.setChanged();
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void calcium$saveLapis(ValueOutput output, CallbackInfo ci) {
        output.store("calcium_lapis", ItemStack.OPTIONAL_CODEC, this.calcium$lapis);
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void calcium$loadLapis(ValueInput input, CallbackInfo ci) {
        this.calcium$lapis = input.read("calcium_lapis", ItemStack.OPTIONAL_CODEC).orElse(ItemStack.EMPTY);
    }

    @Override public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);
        if (!this.calcium$lapis.isEmpty() && this.level != null) {
            Block.popResource(this.level, pos, this.calcium$lapis);
        }
    }

}
