package net.nicolas.calcium.mixin.worldgen;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(TreeFeature.class)
public abstract class TreeFeatureMixin {

    @Unique private static final BlockState CALCIUM_SNOW = Blocks.SNOW.defaultBlockState();

    @Inject(method = "place", at = @At("RETURN"))
    private void calcium$snowOnLeaves(FeaturePlaceContext<TreeConfiguration> context, CallbackInfoReturnable<Boolean> cir, @Local(name = "foliage") Set<BlockPos> foliage) {

        WorldGenLevel level = context.level();
        if (!(level instanceof WorldGenRegion)) {
            return;
        }

        for (BlockPos leafPos : foliage) {
            BlockPos abovePos = leafPos.above();
            if (level.isEmptyBlock(abovePos) && CALCIUM_SNOW.canSurvive(level, abovePos) && level.getBiome(abovePos).value().shouldSnow(level, abovePos)) {
                level.setBlock(abovePos, CALCIUM_SNOW, 19);
            }
        }

    }

}