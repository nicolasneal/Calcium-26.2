package net.nicolas.calcium.core.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class ModParticleTypes {

    public static final SimpleParticleType ECTOPLASM_RAYS = register("ectoplasm_rays");
    public static final SimpleParticleType ECTOPLASM_SPLASH = register("ectoplasm_splash");

    private static SimpleParticleType register(String name) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath("calcium", name), FabricParticleTypes.simple());
    }

    public static void initialize() {}

}
