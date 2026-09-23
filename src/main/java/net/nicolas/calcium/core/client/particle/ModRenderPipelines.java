package net.nicolas.calcium.core.client.particle;

import com.mojang.renderpearl.api.pipeline.BlendFactor;
import com.mojang.renderpearl.api.pipeline.BlendFunction;
import com.mojang.renderpearl.api.pipeline.ColorTargetState;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.resources.Identifier;
import net.nicolas.calcium.mixin.accessors.RenderPipelinesAccessor;

public class ModRenderPipelines {

    private static final BlendFunction ALPHA_SCALED_ADDITIVE = new BlendFunction(BlendFactor.SRC_ALPHA, BlendFactor.ONE);

    public static final RenderPipeline ADDITIVE_PARTICLE = RenderPipelinesAccessor.calcium$register(
        RenderPipeline.builder(RenderPipelinesAccessor.calcium$getParticleSnippet())
            .withLocation(Identifier.fromNamespaceAndPath("calcium", "pipeline/additive_particle"))
            .withColorTargetState(new ColorTargetState(ALPHA_SCALED_ADDITIVE))
            .build()
    );

}
