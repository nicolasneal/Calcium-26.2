package net.nicolas.calcium.client;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.nicolas.calcium.sound.ModSoundGroups;

public class ShulkerBulletLoopSound extends AbstractTickableSoundInstance {

    private final ShulkerBullet bullet;

    public ShulkerBulletLoopSound(ShulkerBullet bullet) {
        super(ModSoundGroups.SHULKER_BULLET_LOOP, SoundSource.HOSTILE, SoundInstance.createUnseededRandom());
        this.bullet = bullet;
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.z = bullet.getZ();
        this.looping = true;
        this.delay = 0;
        this.volume = 0.20F;
        this.attenuation = SoundInstance.Attenuation.LINEAR;
    }

    @Override public void tick() {
        if (this.bullet.isRemoved()) {
            this.stop();
            return;
        }
        this.x = this.bullet.getX();
        this.y = this.bullet.getY();
        this.z = this.bullet.getZ();
    }

}