package net.nicolas.calcium.mixin.gameplay.logging;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.nicolas.calcium.core.util.LoggingHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StainedGlassPaneBlock.class)
public abstract class StainedGlassPaneBlockMixin extends IronBarsBlock {

    protected StainedGlassPaneBlockMixin(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void calcium$restoreUnloggedDefault(DyeColor color, BlockBehaviour.Properties properties, CallbackInfo ci) {
        this.registerDefaultState(LoggingHelper.unlogged(this.defaultBlockState()));
    }

}
