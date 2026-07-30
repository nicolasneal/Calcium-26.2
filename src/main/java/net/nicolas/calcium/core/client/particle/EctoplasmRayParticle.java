package net.nicolas.calcium.core.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class EctoplasmRayParticle extends SingleQuadParticle {

    private static final SingleQuadParticle.Layer ADDITIVE = new SingleQuadParticle.Layer(true, TextureAtlas.LOCATION_PARTICLES, ModRenderPipelines.ADDITIVE_PARTICLE);

    protected EctoplasmRayParticle(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
        super(level, x, y, z, sprite);
        this.gravity = 0.0F;
        this.hasPhysics = false;
        this.quadSize = 4.8F;
        this.setColor(1.0F, 1.0F, 1.0F);
        this.alpha = 0.02F;
        this.lifetime = 120 + this.random.nextInt(60);
    }

    @Override protected int getLightCoords(float partialTick) {
        return 0xF000F0;
    }

    @Override public SingleQuadParticle.FacingCameraMode getFacingCameraMode() {
        return SingleQuadParticle.FacingCameraMode.LOOKAT_Y;
    }

    @Override public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        float progress = (float) this.age / (float) this.lifetime;
        if (progress < 0.5F) {
            this.alpha = Mth.lerp(progress * 2.0F, 0.02F, 1.0F);
        } else {
            this.alpha = Mth.lerp((progress - 0.5F) * 2.0F, 1.0F, 0.0F);
        }
    }

    @Override protected SingleQuadParticle.Layer getLayer() {
        return ADDITIVE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet sprite;

        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xa, double ya, double za, RandomSource random) {
            return new EctoplasmRayParticle(level, x, y, z, this.sprite.get(random));
        }

    }

}
