package net.nicolas.calcium.item;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Items;

public class ModStacks {

    public static void initialize() {

        DefaultItemComponentEvents.MODIFY.register(context -> {

            context.modify(Items.OAK_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.SPRUCE_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BIRCH_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.JUNGLE_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.ACACIA_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.DARK_OAK_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MANGROVE_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.CHERRY_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.PALE_OAK_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BAMBOO_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.CRIMSON_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.WARPED_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));

            context.modify(Items.OAK_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.SPRUCE_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BIRCH_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.JUNGLE_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.ACACIA_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.DARK_OAK_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MANGROVE_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.CHERRY_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.PALE_OAK_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BAMBOO_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.CRIMSON_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.WARPED_HANGING_SIGN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));

            Items.BED.forEach(bed -> context.modify(bed, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64)));
            Items.BANNER.forEach(banner -> context.modify(banner, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64)));

            context.modify(Items.MUSIC_DISC_13, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_CAT, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_BLOCKS, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_CHIRP, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_FAR, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_MALL, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_MELLOHI, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_STAL, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_STRAD, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_WARD, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_11, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_WAIT, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_PIGSTEP, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_5, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_RELIC, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_CREATOR, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_CREATOR_MUSIC_BOX, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_PRECIPICE, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_TEARS, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_LAVA_CHICKEN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSIC_DISC_BOUNCE, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));

            context.modify(Items.FIELD_MASONED_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BORDURE_INDENTED_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.FLOWER_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.CREEPER_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.SKULL_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MOJANG_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.GLOBE_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.PIGLIN_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.FLOW_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.GUSTER_BANNER_PATTERN, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));

            context.modify(Items.ENDER_PEARL, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.SNOWBALL, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BLUE_EGG, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.EGG, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BROWN_EGG, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));

            context.modify(Items.BUCKET, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.WATER_BUCKET, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.LAVA_BUCKET, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.POWDER_SNOW_BUCKET, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MILK_BUCKET, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.POTION, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.SPLASH_POTION, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.LINGERING_POTION, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.HONEY_BOTTLE, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.CAKE, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.MUSHROOM_STEW, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.BEETROOT_SOUP, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.RABBIT_STEW, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.SUSPICIOUS_STEW, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.ARMOR_STAND, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.WRITTEN_BOOK, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));
            context.modify(Items.ENCHANTED_BOOK, builder -> builder.set(DataComponents.MAX_STACK_SIZE, 64));

        });

    }

}