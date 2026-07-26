package net.nicolas.calcium.mixin.hitboxes;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class TallSeagrassOffsetMixin {

    private static final float MAX_VERTICAL_OFFSET = 0.2F;

    @Shadow public abstract Block getBlock();

    @ModifyReturnValue(method = "getOffset", at = @At("RETURN"))
    private Vec3 calcium$addVerticalOffset(Vec3 original, BlockPos pos) {
        if (!(this.getBlock() instanceof TallSeagrassBlock)) {
            return original;
        }
        long seed = Mth.getSeed(pos.getX(), 0, pos.getZ());
        double y = ((float) (seed >> 4 & 15L) / 15.0F - 1.0) * MAX_VERTICAL_OFFSET;
        return original.add(0.0, y, 0.0);
    }

}
