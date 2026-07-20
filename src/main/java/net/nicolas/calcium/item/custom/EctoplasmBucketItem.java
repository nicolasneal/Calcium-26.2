package net.nicolas.calcium.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.nicolas.calcium.sound.ModSoundGroups;
import org.jetbrains.annotations.Nullable;

public class EctoplasmBucketItem extends BucketItem {

    public EctoplasmBucketItem(Fluid fluid, Item.Properties settings) {
        super(fluid, settings);
    }

    @Override protected void playEmptySound(@Nullable LivingEntity user, LevelAccessor world, BlockPos pos) {
        SoundEvent soundEvent = ModSoundGroups.ECTOPLASM_BUCKET_EMPTY;
        world.playSound(user, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.gameEvent(user, GameEvent.FLUID_PLACE, pos);
    }

}