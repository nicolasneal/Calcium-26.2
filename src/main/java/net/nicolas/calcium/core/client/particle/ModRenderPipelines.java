package net.nicolas.calcium.core.client.particle;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.BlendFactor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class ModRenderPipelines {

    private static final BlendFunction ALPHA_SCALED_ADDITIVE = new BlendFunction(BlendFactor.SRC_ALPHA, BlendFactor.ONE);

    public static final RenderPipeline ADDITIVE_PARTICLE = RenderPipelines.register(
        RenderPipeline.builder(RenderPipelines.PARTICLE_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath("calcium", "pipeline/additive_particle"))
            .withColorTargetState(new ColorTargetState(ALPHA_SCALED_ADDITIVE))
            .build()
    );

}
