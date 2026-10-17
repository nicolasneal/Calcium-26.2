package net.nicolas.calcium.mixin.accessors;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderPipelines;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderPipelines.class)
public interface RenderPipelinesAccessor {

    @Accessor("PARTICLE_SNIPPET")
    static RenderPipeline.Snippet calcium$getParticleSnippet() {
        throw new AssertionError();
    }

    @Invoker("register")
    static RenderPipeline calcium$register(RenderPipeline pipeline) {
        throw new AssertionError();
    }

}