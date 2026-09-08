package net.nicolas.calcium.mixin.accessors;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FireBlock.class)
public interface FireBlockInvoker {

    @Invoker("setFlammable") void calcium$setFlammable(Block block, int igniteOdds, int burnOdds);

}
