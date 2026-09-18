package net.nicolas.calcium.worldgen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import net.minecraft.world.level.levelgen.NoiseRouterData;

import java.util.List;
import java.util.function.Consumer;

public final class CalciumOverworldBiomeBuilder {

   public static final float HIGH_START = 0.4F;
   public static final float PEAK_START = 0.5667F;
   public static final float NEAR_INLAND_START = -0.11F;
   public static final float MID_INLAND_START = 0.03F;
   public static final float FAR_INLAND_START = 0.3F;
   public static final float EROSION_INDEX_1_START = -0.78F;
   public static final float EROSION_INDEX_2_START = -0.375F;
   private final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);

   private static final int FRIGID = 0;
   private static final int BITTER = 1;
   private static final int COLD = 2;
   private static final int COOL = 3;
   private static final int CLEMENT = 4;
   private static final int WARM = 5;
   private static final int HOT = 6;
   private static final int ARID = 7;
   private static final int TORRID = 8;

   private final Climate.Parameter[] temperatures = new Climate.Parameter[]{
      Climate.Parameter.point(-1.0F),
      Climate.Parameter.span(-0.999F, -0.75F),
      Climate.Parameter.span(-0.75F, -0.5F),
      Climate.Parameter.span(-0.5F, -0.25F),
      Climate.Parameter.span(-0.25F, 0.25F),
      Climate.Parameter.span(0.25F, 0.5F),
      Climate.Parameter.span(0.5F, 0.75F),
      Climate.Parameter.span(0.75F, 0.999F),
      Climate.Parameter.point(1.0F)
   };
   private final Climate.Parameter[] humidities = new Climate.Parameter[]{
      Climate.Parameter.span(-1.0F, -0.35F),
      Climate.Parameter.span(-0.35F, -0.1F),
      Climate.Parameter.span(-0.1F, 0.1F),
      Climate.Parameter.span(0.1F, 0.3F),
      Climate.Parameter.span(0.3F, 1.0F)
   };
   private final Climate.Parameter[] erosions = new Climate.Parameter[]{
      Climate.Parameter.span(-1.0F, -0.78F),
      Climate.Parameter.span(-0.78F, -0.375F),
      Climate.Parameter.span(-0.375F, -0.2225F),
      Climate.Parameter.span(-0.2225F, 0.05F),
      Climate.Parameter.span(0.05F, 0.45F),
      Climate.Parameter.span(0.45F, 0.55F),
      Climate.Parameter.span(0.55F, 1.0F)
   };

   private final Climate.Parameter deepOceanContinentalness = Climate.Parameter.span(-1.2F, -0.455F);
   private final Climate.Parameter oceanContinentalness = Climate.Parameter.span(-0.455F, -0.19F);
   private final Climate.Parameter coastContinentalness = Climate.Parameter.span(-0.19F, -0.11F);
   private final Climate.Parameter inlandContinentalness = Climate.Parameter.span(-0.11F, 0.55F);
   private final Climate.Parameter nearInlandContinentalness = Climate.Parameter.span(-0.11F, 0.03F);
   private final Climate.Parameter midInlandContinentalness = Climate.Parameter.span(0.03F, 0.3F);
   private final Climate.Parameter farInlandContinentalness = Climate.Parameter.span(0.3F, 1.0F);

   private final Climate.Parameter[] oceanTemperatures = new Climate.Parameter[]{
      Climate.Parameter.span(this.temperatures[FRIGID], this.temperatures[BITTER]),
      Climate.Parameter.span(this.temperatures[COLD], this.temperatures[COOL]), this.temperatures[CLEMENT],
      Climate.Parameter.span(this.temperatures[WARM], this.temperatures[HOT]),
      Climate.Parameter.span(this.temperatures[ARID], this.temperatures[TORRID])
   };
   
   private final ResourceKey<Biome>[][] OCEANS = new ResourceKey[][]{
      {Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.WARM_OCEAN},
      {Biomes.FROZEN_OCEAN, Biomes.COLD_OCEAN, Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN}
   };
   private final ResourceKey<Biome>[][] MIDDLE_BIOMES = new ResourceKey[][]{
      {ModBiomes.TUNDRA, ModBiomes.TUNDRA, ModBiomes.TUNDRA, ModBiomes.TUNDRA, ModBiomes.TUNDRA},
      {Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA},
      {Biomes.TAIGA, Biomes.TAIGA, Biomes.TAIGA, Biomes.TAIGA, Biomes.TAIGA},
      {Biomes.PLAINS, Biomes.PLAINS, Biomes.WINDSWEPT_FOREST, Biomes.TAIGA, Biomes.TAIGA},
      {Biomes.BIRCH_FOREST, Biomes.PLAINS, Biomes.PLAINS, Biomes.FOREST, Biomes.DARK_FOREST},
      {ModBiomes.PRAIRIE, ModBiomes.PRAIRIE, ModBiomes.WOODLAND, ModBiomes.WOODLAND, ModBiomes.WOODLAND},
      {Biomes.SAVANNA, Biomes.SAVANNA, Biomes.SAVANNA, Biomes.SPARSE_JUNGLE, Biomes.JUNGLE},
      {Biomes.DESERT, Biomes.DESERT, Biomes.DESERT, ModBiomes.OASIS, ModBiomes.OASIS},
      {ModBiomes.DUNES, ModBiomes.DUNES, ModBiomes.DUNES, ModBiomes.DUNES, ModBiomes.DUNES}
   };
   private final ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT = new ResourceKey[][]{
      {null, null, null, null, null},
      {null, null, null, null, null},
      {null, null, null, null, null},
      {null, null, null, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA},
      {Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.SUNFLOWER_PLAINS, null, null, Biomes.PALE_GARDEN},
      {null, null, null, null, null},
      {null, null, null, null, Biomes.BAMBOO_JUNGLE},
      {null, null, null, null, null},
      {null, null, null, null, null}
   };
   private final ResourceKey<Biome>[][] PLATEAU_BIOMES = new ResourceKey[][]{
      {ModBiomes.TUNDRA, ModBiomes.TUNDRA, ModBiomes.TUNDRA, ModBiomes.TUNDRA, ModBiomes.TUNDRA},
      {Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA},
      {Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS},
      {Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS},
      {Biomes.MEADOW, Biomes.MEADOW, Biomes.MEADOW, Biomes.MEADOW, Biomes.MEADOW},
      {ModBiomes.PRAIRIE, ModBiomes.PRAIRIE, ModBiomes.WOODLAND, ModBiomes.WOODLAND, ModBiomes.WOODLAND},
      {Biomes.WINDSWEPT_SAVANNA, Biomes.WINDSWEPT_SAVANNA, Biomes.WINDSWEPT_SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.SAVANNA_PLATEAU},
      {ModBiomes.MESA, ModBiomes.MESA, Biomes.BADLANDS, Biomes.BADLANDS, Biomes.WOODED_BADLANDS},
      {ModBiomes.DUNES, ModBiomes.DUNES, ModBiomes.DUNES, ModBiomes.DUNES, ModBiomes.DUNES}
   };
   private final ResourceKey<Biome>[][] PLATEAU_BIOMES_VARIANT = new ResourceKey[][]{
      {null, null, null, null, null},
      {Biomes.ICE_SPIKES, null, null, null, null},
      {Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.GROVE, Biomes.GROVE, Biomes.GROVE},
      {null, null, null, Biomes.GROVE, Biomes.GROVE},
      {Biomes.CHERRY_GROVE, Biomes.CHERRY_GROVE, Biomes.CHERRY_GROVE, Biomes.CHERRY_GROVE, Biomes.CHERRY_GROVE},
      {null, null, null, null, null},
      {null, null, null, null, null},
      {null, null, Biomes.ERODED_BADLANDS, Biomes.ERODED_BADLANDS, Biomes.ERODED_BADLANDS},
      {null, null, null, null, null}
   };
   private final ResourceKey<Biome>[] PEAK_BIOMES = new ResourceKey[]{
      Biomes.FROZEN_PEAKS,
      Biomes.FROZEN_PEAKS,
      Biomes.SNOWY_SLOPES,
      Biomes.GROVE,
      Biomes.STONY_PEAKS,
      Biomes.STONY_PEAKS,
      Biomes.STONY_PEAKS,
      Biomes.STONY_PEAKS,
      Biomes.STONY_PEAKS
   };

   public List<Climate.ParameterPoint> spawnTarget() {

      Climate.Parameter surfaceDepth = Climate.Parameter.point(0.0F);
      float riverClearance = 0.16F;

      return List.of(
         new Climate.ParameterPoint(this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.FULL_RANGE), this.FULL_RANGE, surfaceDepth, Climate.Parameter.span(-1.0F, -0.16F), 0L),
         new Climate.ParameterPoint(this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.FULL_RANGE), this.FULL_RANGE, surfaceDepth, Climate.Parameter.span(0.16F, 1.0F), 0L)
      );

   }

   public void addBiomes(final Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes) {
      this.addOffCoastBiomes(biomes);
      this.addInlandBiomes(biomes);
      this.addUndergroundBiomes(biomes);
   }

   private void addOffCoastBiomes(final Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes) {

      for (int oceanIndex = 0; oceanIndex < this.oceanTemperatures.length; oceanIndex++) {
         Climate.Parameter temperature = this.oceanTemperatures[oceanIndex];
         this.addSurfaceBiome(biomes, temperature, this.FULL_RANGE, this.deepOceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, this.OCEANS[0][oceanIndex]);
         this.addSurfaceBiome(biomes, temperature, this.FULL_RANGE, this.oceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, this.OCEANS[1][oceanIndex]);
      }

   }

   private void addInlandBiomes(final Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes) {

      this.addMidSlice(biomes, Climate.Parameter.span(-1.0F, -0.9333F));
      this.addHighSlice(biomes, Climate.Parameter.span(-0.9333F, -0.7667F));
      this.addPeaks(biomes, Climate.Parameter.span(-0.7667F, -0.5667F));
      this.addHighSlice(biomes, Climate.Parameter.span(-0.5667F, -0.4F));
      this.addMidSlice(biomes, Climate.Parameter.span(-0.4F, -0.2667F));
      this.addLowSlice(biomes, Climate.Parameter.span(-0.2667F, -0.05F));
      this.addLowSlice(biomes, Climate.Parameter.span(-0.05F, 0.05F));
      this.addLowSlice(biomes, Climate.Parameter.span(0.05F, 0.2667F));
      this.addMidSlice(biomes, Climate.Parameter.span(0.2667F, 0.4F));
      this.addHighSlice(biomes, Climate.Parameter.span(0.4F, 0.5667F));
      this.addPeaks(biomes, Climate.Parameter.span(0.5667F, 0.7667F));
      this.addHighSlice(biomes, Climate.Parameter.span(0.7667F, 0.9333F));
      this.addMidSlice(biomes, Climate.Parameter.span(0.9333F, 1.0F));

   }

   private void addPeaks(final Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes, final Climate.Parameter weirdness) {

      for (int temperatureIndex = 0; temperatureIndex < this.temperatures.length; temperatureIndex++) {

         Climate.Parameter temperature = this.temperatures[temperatureIndex];

         for (int humidityIndex = 0; humidityIndex < this.humidities.length; humidityIndex++) {

            Climate.Parameter humidity = this.humidities[humidityIndex];

            ResourceKey<Biome> middleBiome = this.pickMiddleBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> middleBiomeOrBadlandsIfHot = this.pickMiddleBiomeOrBadlandsIfHot(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> middleBiomeOrBadlandsIfHotOrSlopeIfCold = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> plateauBiome = this.pickPlateauBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> peakBiome = this.pickPeakBiome(temperatureIndex, humidityIndex, weirdness);

            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[0], weirdness, 0.0F, peakBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[1], weirdness, 0.0F, middleBiomeOrBadlandsIfHotOrSlopeIfCold);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[1], weirdness, 0.0F, peakBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), weirdness, 0.0F, middleBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], weirdness, 0.0F, plateauBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, this.midInlandContinentalness, this.erosions[3], weirdness, 0.0F, middleBiomeOrBadlandsIfHot);
            this.addSurfaceBiome(biomes, temperature, humidity, this.farInlandContinentalness, this.erosions[3], weirdness, 0.0F, plateauBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], weirdness, 0.0F, middleBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[5], weirdness, 0.0F, middleBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, middleBiome);

         }

      }

   }

   private void addHighSlice(final Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes, final Climate.Parameter weirdness) {

      for (int temperatureIndex = 0; temperatureIndex < this.temperatures.length; temperatureIndex++) {

         Climate.Parameter temperature = this.temperatures[temperatureIndex];

         for (int humidityIndex = 0; humidityIndex < this.humidities.length; humidityIndex++) {

            Climate.Parameter humidity = this.humidities[humidityIndex];

            ResourceKey<Biome> middleBiome = this.pickMiddleBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> middleBiomeOrBadlandsIfHot = this.pickMiddleBiomeOrBadlandsIfHot(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> middleBiomeOrBadlandsIfHotOrSlopeIfCold = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> plateauBiome = this.pickPlateauBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> slopeBiome = this.pickSlopeBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> peakBiome = this.pickPeakBiome(temperatureIndex, humidityIndex, weirdness);

            this.addSurfaceBiome(biomes, temperature, humidity, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), weirdness, 0.0F, middleBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, this.nearInlandContinentalness, this.erosions[0], weirdness, 0.0F, slopeBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[0], weirdness, 0.0F, peakBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, this.nearInlandContinentalness, this.erosions[1], weirdness, 0.0F, middleBiomeOrBadlandsIfHotOrSlopeIfCold);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[1], weirdness, 0.0F, slopeBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), weirdness, 0.0F, middleBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], weirdness, 0.0F, plateauBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, this.midInlandContinentalness, this.erosions[3], weirdness, 0.0F, middleBiomeOrBadlandsIfHot);
            this.addSurfaceBiome(biomes, temperature, humidity, this.farInlandContinentalness, this.erosions[3], weirdness, 0.0F, plateauBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], weirdness, 0.0F, middleBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[5], weirdness, 0.0F, middleBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, middleBiome);

         }

      }

   }

   private void addMidSlice(final Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes, final Climate.Parameter weirdness) {

      this.addSurfaceBiome(biomes, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[2]), weirdness, 0.0F, Biomes.BEACH);

      for (int temperatureIndex = 0; temperatureIndex < this.temperatures.length; temperatureIndex++) {

         Climate.Parameter temperature = this.temperatures[temperatureIndex];

         for (int humidityIndex = 0; humidityIndex < this.humidities.length; humidityIndex++) {

            Climate.Parameter humidity = this.humidities[humidityIndex];
            ResourceKey<Biome> middleBiome = this.pickMiddleBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> middleBiomeOrBadlandsIfHot = this.pickMiddleBiomeOrBadlandsIfHot(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> middleBiomeOrBadlandsIfHotOrSlopeIfCold = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> plateauBiome = this.pickPlateauBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> beachBiome = this.pickBeachBiome(temperatureIndex, humidityIndex);
            ResourceKey<Biome> slopeBiome = this.pickSlopeBiome(temperatureIndex, humidityIndex, weirdness);

            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[0], weirdness, 0.0F, slopeBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[1], weirdness, 0.0F, middleBiomeOrBadlandsIfHotOrSlopeIfCold);
            this.addSurfaceBiome(biomes, temperature, humidity, this.farInlandContinentalness, this.erosions[1], weirdness, 0.0F, temperatureIndex == FRIGID ? slopeBiome : plateauBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, this.nearInlandContinentalness, this.erosions[2], weirdness, 0.0F, middleBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, this.midInlandContinentalness, this.erosions[2], weirdness, 0.0F, middleBiomeOrBadlandsIfHot);
            this.addSurfaceBiome(biomes, temperature, humidity, this.farInlandContinentalness, this.erosions[2], weirdness, 0.0F, plateauBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[3], weirdness, 0.0F, middleBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[3], weirdness, 0.0F, middleBiomeOrBadlandsIfHot);

            if (weirdness.max() < 0L) {
               this.addSurfaceBiome(biomes, temperature, humidity, this.coastContinentalness, Climate.Parameter.span(this.erosions[4], this.erosions[5]), weirdness, 0.0F, beachBiome);
               this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[4], this.erosions[5]), weirdness, 0.0F, middleBiome);
            } else {
               this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[4], this.erosions[5]), weirdness, 0.0F, middleBiome);
            }

            if (weirdness.max() < 0L) {
               this.addSurfaceBiome(biomes, temperature, humidity, this.coastContinentalness, this.erosions[6], weirdness, 0.0F, beachBiome);
            } else {
               this.addSurfaceBiome(biomes, temperature, humidity, this.coastContinentalness, this.erosions[6], weirdness, 0.0F, middleBiome);
            }

            if (temperatureIndex == FRIGID) {
               this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, middleBiome);
            }

         }

      }

   }

   private void addLowSlice(final Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes, final Climate.Parameter weirdness) {

      this.addSurfaceBiome(biomes, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[2]), weirdness, 0.0F, Biomes.BEACH);

      for (int temperatureIndex = 0; temperatureIndex < this.temperatures.length; temperatureIndex++) {

         Climate.Parameter temperature = this.temperatures[temperatureIndex];

         for (int humidityIndex = 0; humidityIndex < this.humidities.length; humidityIndex++) {

            Climate.Parameter humidity = this.humidities[humidityIndex];

            ResourceKey<Biome> middleBiome = this.pickMiddleBiome(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> middleBiomeOrBadlandsIfHot = this.pickMiddleBiomeOrBadlandsIfHot(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> middleBiomeOrBadlandsIfHotOrSlopeIfCold = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(temperatureIndex, humidityIndex, weirdness);
            ResourceKey<Biome> beachBiome = this.pickBeachBiome(temperatureIndex, humidityIndex);

            this.addSurfaceBiome(biomes, temperature, humidity, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), weirdness, 0.0F, middleBiomeOrBadlandsIfHot);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[0], this.erosions[1]), weirdness, 0.0F, middleBiomeOrBadlandsIfHotOrSlopeIfCold);
            this.addSurfaceBiome(biomes, temperature, humidity, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[2], this.erosions[3]), weirdness, 0.0F, middleBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), weirdness, 0.0F, middleBiomeOrBadlandsIfHot);
            this.addSurfaceBiome(biomes, temperature, humidity, this.coastContinentalness, Climate.Parameter.span(this.erosions[3], this.erosions[6]), weirdness, 0.0F, beachBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[4], weirdness, 0.0F, middleBiome);
            this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[5], weirdness, 0.0F, middleBiome);

            if (temperatureIndex == FRIGID) {
               this.addSurfaceBiome(biomes, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, middleBiome);
            }

         }

      }

   }

   private void addUndergroundBiomes(final Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes) {

      this.addUndergroundBiome(biomes, this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(0.8F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, 0.0F, Biomes.DRIPSTONE_CAVES);
      this.addUndergroundBiome(biomes, this.FULL_RANGE, Climate.Parameter.span(0.7F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, this.FULL_RANGE, 0.0F, Biomes.LUSH_CAVES);
      this.addUndergroundBiome(biomes, this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.coastContinentalness, this.inlandContinentalness), Climate.Parameter.span(this.erosions[5], this.erosions[6]), Climate.Parameter.span(-1.1F, -0.85F), 0.0F, Biomes.SULFUR_CAVES);
      this.addBottomBiome(biomes, this.FULL_RANGE, this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.erosions[0], this.erosions[1]), this.FULL_RANGE, 0.0F, Biomes.DEEP_DARK);

   }

   private ResourceKey<Biome> pickMiddleBiome(final int temperatureIndex, final int humidityIndex, final Climate.Parameter weirdness) {
      if (weirdness.max() < 0L) {
         return this.MIDDLE_BIOMES[temperatureIndex][humidityIndex];
      }
      else {
         ResourceKey<Biome> variant = this.MIDDLE_BIOMES_VARIANT[temperatureIndex][humidityIndex];
         return variant == null ? this.MIDDLE_BIOMES[temperatureIndex][humidityIndex] : variant;
      }
   }

   private ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHot(final int temperatureIndex, final int humidityIndex, final Climate.Parameter weirdness) {
      return temperatureIndex == ARID || temperatureIndex == TORRID
         ? this.pickPlateauBiome(temperatureIndex, humidityIndex, weirdness)
         : this.pickMiddleBiome(temperatureIndex, humidityIndex, weirdness);
   }

   private ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(final int temperatureIndex, final int humidityIndex, final Climate.Parameter weirdness) {
      return temperatureIndex == FRIGID
         ? this.pickSlopeBiome(temperatureIndex, humidityIndex, weirdness)
         : this.pickMiddleBiomeOrBadlandsIfHot(temperatureIndex, humidityIndex, weirdness);
   }

   private ResourceKey<Biome> pickBeachBiome(final int temperatureIndex, final int humidityIndex) {
      if (temperatureIndex == FRIGID || temperatureIndex == BITTER) {
         return Biomes.SNOWY_BEACH;
      }
      else if (temperatureIndex == ARID || temperatureIndex == TORRID) {
         return this.MIDDLE_BIOMES[temperatureIndex][humidityIndex];
      }
      else {
         return Biomes.BEACH;
      }
   }

   private ResourceKey<Biome> pickPlateauBiome(final int temperatureIndex, final int humidityIndex, final Climate.Parameter weirdness) {
      if (weirdness.max() >= 0L) {
         ResourceKey<Biome> variant = this.PLATEAU_BIOMES_VARIANT[temperatureIndex][humidityIndex];
         if (variant != null) {
            return variant;
         }
      }

      return this.PLATEAU_BIOMES[temperatureIndex][humidityIndex];
   }

   private ResourceKey<Biome> pickPeakBiome(final int temperatureIndex, final int humidityIndex, final Climate.Parameter weirdness) {
      ResourceKey<Biome> peak = this.PEAK_BIOMES[temperatureIndex];
      return peak == null ? this.pickPlateauBiome(temperatureIndex, humidityIndex, weirdness) : peak;
   }

   private ResourceKey<Biome> pickSlopeBiome(final int temperatureIndex, final int humidityIndex, final Climate.Parameter weirdness) {
      return this.pickPlateauBiome(temperatureIndex, humidityIndex, weirdness);
   }

   private void addSurfaceBiome(
      final Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes,
      final Climate.Parameter temperature,
      final Climate.Parameter humidity,
      final Climate.Parameter continentalness,
      final Climate.Parameter erosion,
      final Climate.Parameter weirdness,
      final float offset,
      final ResourceKey<Biome> second
   ) {
      biomes.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.point(0.0F), weirdness, offset), second));
      biomes.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.point(1.0F), weirdness, offset), second));
   }

   private void addUndergroundBiome(
      final Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes,
      final Climate.Parameter temperature,
      final Climate.Parameter humidity,
      final Climate.Parameter continentalness,
      final Climate.Parameter erosion,
      final Climate.Parameter weirdness,
      final float offset,
      final ResourceKey<Biome> biome
   ) {
      biomes.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.span(0.2F, 0.9F), weirdness, offset), biome));
   }

   private void addBottomBiome(
      final Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes,
      final Climate.Parameter temperature,
      final Climate.Parameter humidity,
      final Climate.Parameter continentalness,
      final Climate.Parameter erosion,
      final Climate.Parameter weirdness,
      final float offset,
      final ResourceKey<Biome> biome
   ) {
      biomes.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.point(1.1F), weirdness, offset), biome));
   }

   public static boolean isDeepDarkRegion(final DensityFunction erosion, final DensityFunction depth, final FunctionContext context) {
      return erosion.compute(context) < -0.225F && depth.compute(context) > 0.9F;
   }

   public static String getDebugStringForPeaksAndValleys(final double peaksAndValleys) {
      if (peaksAndValleys < NoiseRouterData.peaksAndValleys(0.05F)) {
         return "Valley";
      }
      else if (peaksAndValleys < NoiseRouterData.peaksAndValleys(0.2667F)) {
         return "Low";
      }
      else if (peaksAndValleys < NoiseRouterData.peaksAndValleys(0.4F)) {
         return "Mid";
      }
      else {
         return peaksAndValleys < NoiseRouterData.peaksAndValleys(0.5667F) ? "High" : "Peak";
      }
   }

   public String getDebugStringForContinentalness(final double continentalness) {

      double continentalnessQuantized = Climate.quantizeCoord((float)continentalness);

      if (continentalnessQuantized < this.deepOceanContinentalness.max()) {
         return "Deep ocean";
      }
      else if (continentalnessQuantized < this.oceanContinentalness.max()) {
         return "Ocean";
      }
      else if (continentalnessQuantized < this.coastContinentalness.max()) {
         return "Coast";
      }
      else if (continentalnessQuantized < this.nearInlandContinentalness.max()) {
         return "Near inland";
      }
      else {
         return continentalnessQuantized < this.midInlandContinentalness.max() ? "Mid inland" : "Far inland";
      }

   }

   public String getDebugStringForErosion(final double erosion) {
      return getDebugStringForNoiseValue(erosion, this.erosions);
   }

   public String getDebugStringForTemperature(final double temperature) {
      return getDebugStringForNoiseValue(temperature, this.temperatures);
   }

   public String getDebugStringForHumidity(final double humidity) {
      return getDebugStringForNoiseValue(humidity, this.humidities);
   }

   private static String getDebugStringForNoiseValue(final double noiseValue, final Climate.Parameter[] array) {
      double noiseValueQuantized = Climate.quantizeCoord((float)noiseValue);
      for (int i = 0; i < array.length; i++) {
         if (noiseValueQuantized < array[i].max()) {
            return i + "";
         }
      }
      return "?";
   }

   @VisibleForDebug public Climate.Parameter[] getTemperatureThresholds() {
      return this.temperatures;
   }

   @VisibleForDebug public Climate.Parameter[] getHumidityThresholds() {
      return this.humidities;
   }

   @VisibleForDebug public Climate.Parameter[] getErosionThresholds() {
      return this.erosions;
   }

   @VisibleForDebug public Climate.Parameter[] getContinentalnessThresholds() {
      return new Climate.Parameter[] {
         this.deepOceanContinentalness,
         this.oceanContinentalness,
         this.coastContinentalness,
         this.nearInlandContinentalness,
         this.midInlandContinentalness,
         this.farInlandContinentalness
      };
   }

   @VisibleForDebug public Climate.Parameter[] getPeaksAndValleysThresholds() {
      return new Climate.Parameter[]{
         Climate.Parameter.span(-2.0F, NoiseRouterData.peaksAndValleys(0.05F)),
         Climate.Parameter.span(NoiseRouterData.peaksAndValleys(0.05F), NoiseRouterData.peaksAndValleys(0.2667F)),
         Climate.Parameter.span(NoiseRouterData.peaksAndValleys(0.2667F), NoiseRouterData.peaksAndValleys(0.4F)),
         Climate.Parameter.span(NoiseRouterData.peaksAndValleys(0.4F), NoiseRouterData.peaksAndValleys(0.5667F)),
         Climate.Parameter.span(NoiseRouterData.peaksAndValleys(0.5667F), 2.0F)
      };
   }

   @VisibleForDebug public Climate.Parameter[] getWeirdnessThresholds() {
      return new Climate.Parameter[]{Climate.Parameter.span(-2.0F, 0.0F), Climate.Parameter.span(0.0F, 2.0F)};
   }

}