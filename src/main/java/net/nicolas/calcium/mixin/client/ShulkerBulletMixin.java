package net.nicolas.calcium.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.nicolas.calcium.core.client.sound.ShulkerBulletLoopSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBullet.class)
public abstract class ShulkerBulletMixin {

    @Unique private boolean calcium$startedLoopSound;

    @Inject(method = "tick", at = @At("HEAD"))
    private void calcium$startLoopSound(CallbackInfo ci) {
        ShulkerBullet self = (ShulkerBullet) (Object) this;
        if (!self.level().isClientSide() || this.calcium$startedLoopSound) return;
        this.calcium$startedLoopSound = true;
        Minecraft.getInstance().getSoundManager().play(new ShulkerBulletLoopSound(self));
    }

}