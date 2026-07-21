package net.nicolas.calcium.mixin.client;

import net.minecraft.client.model.animal.sniffer.SnifferModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SnifferRenderer;
import net.minecraft.client.renderer.entity.state.SnifferRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.nicolas.calcium.core.client.SnifferChestLayer;
import net.nicolas.calcium.core.client.SnifferChestModel;
import net.nicolas.calcium.core.client.SnifferChestRenderAccess;
import net.nicolas.calcium.core.client.SnifferSaddleAccess;
import net.nicolas.calcium.core.client.SnifferSaddleLayer;
import net.nicolas.calcium.screen.SnifferChestAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnifferRenderer.class)
public abstract class SnifferRendererMixin extends AgeableMobRenderer<Sniffer, SnifferRenderState, SnifferModel> {

    public SnifferRendererMixin(EntityRendererProvider.Context context, SnifferModel adultModel, SnifferModel babyModel, float shadow) {
        super(context, adultModel, babyModel, shadow);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void calcium$addSaddleLayer(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.addLayer(new SnifferSaddleLayer(this, new SnifferModel(context.bakeLayer(SnifferSaddleLayer.LAYER))));
        this.addLayer(new SnifferChestLayer(this, new SnifferChestModel(context.bakeLayer(SnifferChestModel.LAYER))));
    }

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void calcium$extractSaddle(Sniffer entity, SnifferRenderState state, float partialTicks, CallbackInfo ci) {
        ((SnifferSaddleAccess) state).calcium$setSaddle(entity.getItemBySlot(EquipmentSlot.SADDLE).copy());
        ((SnifferChestRenderAccess) state).calcium$setHasChest(((SnifferChestAccess) entity).calcium$hasChest());
    }

}
