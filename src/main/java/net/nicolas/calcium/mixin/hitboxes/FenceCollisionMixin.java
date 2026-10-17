package net.nicolas.calcium.mixin.hitboxes;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({CrossCollisionBlock.class, WallBlock.class, FenceGateBlock.class})
public abstract class FenceCollisionMixin {

    @Unique private static final VoxelShape PLAYER_HEIGHT_LIMIT = Block.column(16.0, 0.0, 16.0);

    @ModifyReturnValue(method = "getCollisionShape", at = @At("RETURN"))
    private VoxelShape calcium$lowerCollisionForPlayers(VoxelShape original, BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if ((Object) this instanceof CrossCollisionBlock && !((Object) this instanceof FenceBlock)) {
            return original;
        }
        if (context instanceof EntityCollisionContext entityContext && entityContext.getEntity() instanceof Player) {
            return Shapes.join(original, PLAYER_HEIGHT_LIMIT, BooleanOp.AND);
        }
        return original;
    }

}