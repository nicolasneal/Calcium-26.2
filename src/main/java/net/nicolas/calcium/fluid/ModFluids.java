package net.nicolas.calcium.fluid;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.FlowingFluid;

public class ModFluids {

    public static final FlowingFluid ECTOPLASM_STILL = register("ectoplasm_still", new EctoplasmFluid.Still());
    public static final FlowingFluid ECTOPLASM_FLOWING = register("ectoplasm_flowing", new EctoplasmFluid.Flowing());

    private static FlowingFluid register(String name, FlowingFluid flowableFluid) {
        return Registry.register(BuiltInRegistries.FLUID, Identifier.fromNamespaceAndPath("calcium", name), flowableFluid);
    }

    public static void initialize() {}

}