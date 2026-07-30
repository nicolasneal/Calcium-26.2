package net.nicolas.calcium.mixin.accessors;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CropBlock.class)
public interface CropBlockInvoker {

    @Invoker("getGrowthSpeed") static float calcium$getGrowthSpeed(Block block, BlockGetter level, BlockPos pos) {
        throw new AssertionError();
    }

    @Invoker("hasSufficientLight") static boolean calcium$hasSufficientLight(LevelReader level, BlockPos pos) {
        throw new AssertionError();
    }

}
