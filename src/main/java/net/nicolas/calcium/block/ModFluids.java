package net.nicolas.calcium.block;

import net.minecraft.world.level.material.FlowingFluid;
import net.nicolas.calcium.block.fluid.EctoplasmFluid;

public final class ModFluids {

    public static final FlowingFluid ECTOPLASM_STILL = new EctoplasmFluid.Still();
    public static final FlowingFluid ECTOPLASM_FLOWING = new EctoplasmFluid.Flowing();

    private ModFluids() {}
}
