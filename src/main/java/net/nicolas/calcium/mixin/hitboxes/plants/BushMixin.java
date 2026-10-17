package net.nicolas.calcium.mixin.hitboxes.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({BushBlock.class, DryVegetationBlock.class, FireflyBushBlock.class, SweetBerryBushBlock.class})
public abstract class BushMixin extends Block {

    @Unique private static final VoxelShape BUSH_SHAPE = Block.column(14.0, 0.0, 16.0);
    @Unique private static final VoxelShape SWEET_BERRY_BUSH_SAPLING_SHAPE = Block.column(10.0, 0.0, 8.0);
    @Unique private static final VoxelShape SWEET_BERRY_BUSH_GROWING_SHAPE = Block.column(12.0, 0.0, 13.0);

    public BushMixin(Properties settings) {
        super(settings);
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Object self = this;
        if (self instanceof SweetBerryBushBlock) {
            return switch (state.getValue(SweetBerryBushBlock.AGE)) {
                case 0 -> SWEET_BERRY_BUSH_SAPLING_SHAPE;
                case 3 -> BUSH_SHAPE;
                default -> SWEET_BERRY_BUSH_GROWING_SHAPE;
            };
        }
        return BUSH_SHAPE;
    }

}