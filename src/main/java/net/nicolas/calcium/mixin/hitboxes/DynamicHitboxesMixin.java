package net.nicolas.calcium.mixin.hitboxes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HangingRootsBlock;
import net.minecraft.world.level.block.NetherRootsBlock;
import net.minecraft.world.level.block.NetherSproutsBlock;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.ShortDryGrassBlock;
import net.minecraft.world.level.block.TallDryGrassBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({
    ShortDryGrassBlock.class,
    TallDryGrassBlock.class,
    TallGrassBlock.class,
    NetherSproutsBlock.class,
    NetherRootsBlock.class,
    HangingRootsBlock.class,
    SeagrassBlock.class,
    TallSeagrassBlock.class
})
public abstract class DynamicHitboxesMixin extends Block {

    @Shadow(remap = false) @Final private static VoxelShape SHAPE;

    public DynamicHitboxesMixin(Properties settings) {
        super(settings);
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE.move(state.getOffset(pos));
    }

}
