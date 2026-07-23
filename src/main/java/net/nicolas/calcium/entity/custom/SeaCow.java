package net.nicolas.calcium.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.nicolas.calcium.entity.ModEntityTypes;
import net.nicolas.calcium.item.tag.ModTags;
import net.nicolas.calcium.sound.ModSoundGroups;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class SeaCow extends Animal {

    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(SeaCow.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_MOISTNESS = SynchedEntityData.defineId(SeaCow.class, EntityDataSerializers.INT);
    private static final EntityDimensions BABY_DIMENSIONS = EntityDimensions.scalable(0.8F, 0.6F);
    private static final int TOTAL_MOISTNESS_LEVEL = 2400;

    public SeaCow(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
        Objects.requireNonNull(this.getAttribute(Attributes.STEP_HEIGHT)).setBaseValue(1.0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 1.2F).add(Attributes.KNOCKBACK_RESISTANCE, 0.6F);
    }

    @Override protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.0, stack -> stack.is(ModTags.SEA_COW_FEEDS), false));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Guardian.class, 8.0F, 1.0, 1.0));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0));
        this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1.0, 10));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    @Override protected PathNavigation createNavigation(Level level) {
        return new AmphibiousPathNavigation(this, level);
    }

    @Override public boolean isFood(ItemStack stack) {
        return stack.is(ModTags.SEA_COW_FEEDS);
    }

    @Override public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!this.isBaby() && itemStack.is(Items.BUCKET) && this.isInWater()) {
            player.playSound(ModSoundGroups.SEA_COW_MILK, 1.0F, 1.0F);
            ItemStack milkBucket = ItemUtils.createFilledResult(itemStack, player, Items.MILK_BUCKET.getDefaultInstance());
            player.setItemInHand(hand, milkBucket);
            this.setPersistenceRequired();
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
        Holder<Biome> biome = level.getBiome(this.blockPosition());
        SeaCowVariant variant = biome.is(BiomeTags.SPAWNS_COLD_VARIANT_FARM_ANIMALS) ? SeaCowVariant.COLD
            : biome.is(BiomeTags.SPAWNS_WARM_VARIANT_FARM_ANIMALS) ? SeaCowVariant.WARM
            : SeaCowVariant.TEMPERATE;
        this.setVariant(variant);
        return result;
    }

    @Override public @Nullable SeaCow getBreedOffspring(ServerLevel level, AgeableMob partner) {
        SeaCow baby = ModEntityTypes.SEA_COW.create(level, EntitySpawnReason.BREEDING);
        if (baby != null && partner instanceof SeaCow partnerCow) {
            baby.setVariant(this.random.nextBoolean() ? this.getVariant() : partnerCow.getVariant());
        }
        return baby;
    }

    @Override public EntityDimensions getDefaultDimensions(Pose pose) {
        return this.isBaby() ? BABY_DIMENSIONS : super.getDefaultDimensions(pose);
    }

    public SeaCowVariant getVariant() {
        return SeaCowVariant.byId(this.entityData.get(DATA_VARIANT));
    }

    public void setVariant(SeaCowVariant variant) {
        this.entityData.set(DATA_VARIANT, variant.getId());
    }

    @Override protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT, SeaCowVariant.TEMPERATE.getId());
        builder.define(DATA_MOISTNESS, TOTAL_MOISTNESS_LEVEL);
    }

    @Override protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putString("variant", this.getVariant().variantName());
        output.putInt("Moistness", this.getMoistnessLevel());
    }

    @Override protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setVariant(SeaCowVariant.byName(input.getStringOr("variant", SeaCowVariant.TEMPERATE.variantName())));
        this.setMoistnessLevel(input.getIntOr("Moistness", TOTAL_MOISTNESS_LEVEL));
    }

    @Override public boolean canBeLeashed() {
        return true;
    }

    public int getMoistnessLevel() {
        return this.entityData.get(DATA_MOISTNESS);
    }

    public void setMoistnessLevel(int level) {
        this.entityData.set(DATA_MOISTNESS, level);
    }

    @Override public void tick() {
        super.tick();
        if (this.isInWaterOrRain()) {
            this.setMoistnessLevel(TOTAL_MOISTNESS_LEVEL);
        } else {
            this.setMoistnessLevel(this.getMoistnessLevel() - 1);
            if (this.getMoistnessLevel() <= 0 && this.level() instanceof ServerLevel serverLevel) {
                this.hurtServer(serverLevel, this.damageSources().dryOut(), 1.0F);
            }
        }
    }

    @Override protected SoundEvent getAmbientSound() {
        if (this.isBaby()) {
            return this.isInWater() ? ModSoundGroups.SEA_COW_BABY_AMBIENT : ModSoundGroups.SEA_COW_BABY_AMBIENT_LAND;
        }
        return this.isInWater() ? ModSoundGroups.SEA_COW_AMBIENT : ModSoundGroups.SEA_COW_AMBIENT_LAND;
    }

    @Override protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isBaby()) {
            return this.isInWater() ? ModSoundGroups.SEA_COW_BABY_HURT : ModSoundGroups.SEA_COW_BABY_HURT_LAND;
        }
        return this.isInWater() ? ModSoundGroups.SEA_COW_HURT : ModSoundGroups.SEA_COW_HURT_LAND;
    }

    @Override protected SoundEvent getDeathSound() {
        if (this.isBaby()) {
            return this.isInWater() ? ModSoundGroups.SEA_COW_BABY_DEATH : ModSoundGroups.SEA_COW_BABY_DEATH_LAND;
        }
        return this.isInWater() ? ModSoundGroups.SEA_COW_DEATH : ModSoundGroups.SEA_COW_DEATH_LAND;
    }

    @Override protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(ModSoundGroups.SEA_COW_STEP, 0.15F, this.isBaby() ? 2.0F : 1.0F);
    }

}