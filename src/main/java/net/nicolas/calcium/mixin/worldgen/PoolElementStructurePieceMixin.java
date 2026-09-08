package net.nicolas.calcium.mixin.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PoolElementStructurePiece.class)
public abstract class PoolElementStructurePieceMixin {

    private static final int calcium$FLOATING_VEGETATION_BUFFER = 3;

    @Inject(method = "postProcess", at = @At("TAIL"))
    private void calcium$removeFloatingVegetation(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox chunkBox, ChunkPos chunkPos, BlockPos pos, CallbackInfo ci) {
        for (int x = chunkBox.minX(); x <= chunkBox.maxX(); x++) {
            for (int z = chunkBox.minZ(); z <= chunkBox.maxZ(); z++) {
                for (int y = chunkBox.minY(); y <= chunkBox.maxY() + calcium$FLOATING_VEGETATION_BUFFER; y++) {
                    BlockPos checkPos = new BlockPos(x, y, z);
                    BlockState state = level.getBlockState(checkPos);
                    if (!state.isAir() && !state.canSurvive(level, checkPos)) {
                        level.removeBlock(checkPos, false);
                    }
                }
            }
        }
    }

}
