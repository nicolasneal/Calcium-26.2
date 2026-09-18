package net.nicolas.calcium.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class SpruceFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<SpruceFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance -> foliagePlacerParts(instance)
        .and(IntProviders.codec(0, 24).fieldOf("trunk_height").forGetter(placer -> placer.trunkHeight))
        .apply(instance, SpruceFoliagePlacer::new));

    private static final int[] RADIUS_BY_LEVEL = {0, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2, 2, 1};

    private final IntProvider trunkHeight;

    public SpruceFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider trunkHeight) {
        super(radius, offset);
        this.trunkHeight = trunkHeight;
    }

    @Override protected FoliagePlacerType<?> type() {
        return ModFoliagePlacers.SPRUCE_FOLIAGE_PLACER;
    }

    @Override protected void createFoliage(WorldGenLevel level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, int treeHeight, FoliageAttachment foliageAttachment, int foliageHeight, int leafRadius, int offset) {

        BlockPos pos = foliageAttachment.pos();
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        int top = offset - 1;
        int bottomOfTable = top - (RADIUS_BY_LEVEL.length - 1);

        for (int i = top; i >= bottomOfTable; i--) {
            int distanceFromTop = top - i;
            int radiusAtLevel = RADIUS_BY_LEVEL[distanceFromTop];
            if (radiusAtLevel == 0) {
                mutablePos.setWithOffset(pos, 0, i, 0);
                tryPlaceLeaf(level, foliageSetter, random, config, mutablePos);
                if (distanceFromTop <= 2 && random.nextInt(4) == 0) {
                    Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                    Direction direction2 = random.nextBoolean() ? direction.getClockWise() : direction.getCounterClockWise();
                    tryPlaceLeaf(level, foliageSetter, random, config, mutablePos.relative(direction, 1).relative(direction2, 1));
                }
            }
            else {
                this.placeRing(level, foliageSetter, random, config, pos, radiusAtLevel, i, foliageAttachment.doubleTrunk(), distanceFromTop, random);
            }
        }

        int extraRadius = random.nextInt(2);
        int rowWidth = 1;
        int flag = 0;
        int minJ = Math.max(-leafRadius, -2);

        for (int j = bottomOfTable - 1; j >= minJ; j--) {
            this.placeLeavesRow(level, foliageSetter, random, config, pos, extraRadius, j, foliageAttachment.doubleTrunk());
            if (extraRadius >= rowWidth) {
                extraRadius = flag;
                flag = 1;
                rowWidth = Math.min(rowWidth + 1, leafRadius + foliageAttachment.radiusOffset());
            } else {
                extraRadius++;
            }
        }

    }

    private void placeRing(WorldGenLevel level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, BlockPos pos, int radius, int y, boolean doubleTrunk, int distanceFromTop, RandomSource ringRandom) {

        int extra = doubleTrunk ? 1 : 0;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        boolean isRadius1 = radius == 1;
        boolean isRadius2 = radius == 2;
        boolean isNearTop = distanceFromTop <= 4;
        int threshold = isRadius1 ? 4 : (isRadius2 ? 6 : 7);

        for (int dx = -radius; dx <= radius + extra; dx++) {

            for (int dz = -radius; dz <= radius + extra; dz++) {

                if (dx == 0 && dz == 0) continue;

                int absDx = Math.abs(dx);
                int absDz = Math.abs(dz);
                if ((float) (absDx * absDx + absDz * absDz) > (radius + 0.25F) * (radius + 0.25F)) continue;

                boolean place;

                if (absDx <= 1 && absDz <= 1) {
                    place = true;
                }
                else if (isNearTop) {
                    place = !isRadius1 || ringRandom.nextInt(threshold + 2) != 0;
                }
                else if (isRadius1) {
                    place = !(absDx == 1 && absDz == 1) || ringRandom.nextInt(threshold + 2) != 0;
                }
                else if (isRadius2) {
                    place = !(absDx == radius || absDz == radius) || ringRandom.nextInt(threshold + 1) != 0;
                }
                else if (absDx == radius && absDz == radius) {
                    place = false;
                }
                else {
                    place = ringRandom.nextInt(threshold + 2) != 0;
                }

                if (place) {
                    mutablePos.setWithOffset(pos, dx, y, dz);
                    tryPlaceLeaf(level, foliageSetter, random, config, mutablePos);
                }

            }

        }

    }

    @Override public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return Math.max(4, (height - this.trunkHeight.sample(random)) - 2);
    }

    @Override protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return localX == range && localZ == range && range > 0;
    }

}