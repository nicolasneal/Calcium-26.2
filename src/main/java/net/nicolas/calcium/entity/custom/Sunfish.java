package net.nicolas.calcium.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Bucketable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.nicolas.calcium.entity.ModEntities;
import net.nicolas.calcium.item.ModItems;
import net.nicolas.calcium.item.ModTags;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Sunfish extends PathfinderMob implements Bucketable {

    private static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(Sunfish.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BASKING_COOLDOWN = SynchedEntityData.defineId(Sunfish.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Sunfish.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(Sunfish.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_BASKING = SynchedEntityData.defineId(Sunfish.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> AGE_LOCKED = SynchedEntityData.defineId(Sunfish.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState landAnimationState = new AnimationState();
    public float prevBaskingProgress = 0.0F;
    public float baskingProgress = 0.0F;
    private int idleAnimationTimeout = 0;
    private int landAnimationTimeout = 0;
    private int inLove;
    private int lastAgeCategory = Integer.MIN_VALUE;
    private int ageLockParticleTimer = 0;
    private @Nullable UUID loveCause;

    public Sunfish(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.moveControl = new SunfishMoveControl(this);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    @Override protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.0));
        this.goalSelector.addGoal(3, new SunfishBreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new TemptGoal(this, 0.7, stack -> stack.is(ModTags.SUNFISH_FEEDS), false));
        this.goalSelector.addGoal(5, new SunfishBaskingGoal(this));
        this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 0.5, 10));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.8F).add(Attributes.TEMPT_RANGE, 10.0);
    }

    @Override public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnGroupData) {
        if (spawnReason == EntitySpawnReason.BUCKET) {
            this.growUpIfNeeded();
            return spawnGroupData;
        }
        this.setVariant(SunfishVariant.byId(this.random.nextInt(SunfishVariant.values().length)));
        this.growUpIfNeeded();
        return super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
    }

    @Override public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(ModItems.SUNFISH_SPAWN_EGG) && this.level() instanceof ServerLevel serverLevel) {
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            Sunfish baby = ModEntities.SUNFISH.create(serverLevel, EntitySpawnReason.SPAWN_ITEM_USE);
            if (baby != null) {
                baby.setAge(-48000);
                baby.setVariant(this.getVariant());
                baby.growUpIfNeeded();
                baby.snapTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                serverLevel.addFreshEntityWithPassengers(baby);
            }
            return InteractionResult.SUCCESS_SERVER;
        }
        if (AgeableMob.canUseGoldenDandelion(itemStack, this.isBaby(), this.ageLockParticleTimer, this)) {
            AgeableMob.setAgeLocked(this, this::isAgeLocked, player, itemStack, mob -> this.setAgeLockedData());
            return InteractionResult.SUCCESS;
        }
        if (this.getSunfishAge() == -1) {
            var interactionResult = Bucketable.bucketMobPickup(player, hand, this);
            if (interactionResult.isPresent() && interactionResult.get().consumesAction()) {
                return interactionResult.get();
            }
        }
        if (itemStack.is(ModTags.SUNFISH_FEEDS)) {
            int age = this.getAge();
            if (!this.level().isClientSide() && age == 0 && this.canFallInLove()) {
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                this.setInLove(player);
                return InteractionResult.SUCCESS_SERVER;
            }
            if (this.isBaby()) {
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                this.setAge(this.getAge() + 300);
                return InteractionResult.SUCCESS;
            }
            if (this.level().isClientSide()) {
                return InteractionResult.CONSUME;
            }
        }
        return super.mobInteract(player, hand);
    }

    public void spawnChildFromBreeding(ServerLevel level, Sunfish partner) {
        Sunfish baby = ModEntities.SUNFISH.create(level, EntitySpawnReason.BREEDING);
        if (baby != null) {
            ServerPlayer breeder = this.getLoveCause();
            if (breeder == null && partner.getLoveCause() != null) {
                breeder = partner.getLoveCause();
            }
            if (breeder != null) {
                breeder.awardStat(Stats.ANIMALS_BRED);
            }
            this.setAge(6000);
            partner.setAge(6000);
            this.resetLove();
            partner.resetLove();
            baby.setVariant(this.random.nextBoolean() ? this.getVariant() : partner.getVariant());
            baby.setPersistenceRequired();
            baby.setAge(-48000);
            baby.snapTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            level.addFreshEntity(baby);
            level.broadcastEntityEvent(this, (byte) 18);
            baby.growUpIfNeeded();
            if (level.getGameRules().get(GameRules.MOB_DROPS)) {
                level.addFreshEntity(new ExperienceOrb(level, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
            }
        }
    }

    @Override protected void customServerAiStep(ServerLevel level) {
        this.clearLoveIfGrown();
        if (!this.isInWater() && this.onGround() && this.verticalCollisionBelow && this.isBaby()) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, this.getSunfishAge() == -2 ? 0.3F : 0.5, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
            this.setOnGround(false);
            this.needsSync = true;
            this.playSound(SoundEvents.COD_FLOP, this.getSoundVolume(), this.getVoicePitch());
        }
        super.customServerAiStep(level);
    }

    @Override public void aiStep() {
        super.aiStep();
        this.clearLoveIfGrown();
        if (this.inLove > 0) {
            this.inLove--;
            if (this.inLove % 10 == 0) {
                this.spawnHeartParticles(1);
            }
        }
        if (this.isAlive() && !this.isNoAi() && this.getBaskingCooldown() > 0) {
            this.setBaskingCooldown(this.getBaskingCooldown() - 1);
        }
        this.ageLockParticleTimer = AgeableMob.makeAgeLockedParticle(this.level(), this, this.ageLockParticleTimer, this.isAgeLocked());
    }

    @Override public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        this.inLove = 0;
        if (this.isBasking()) {
            this.setBasking(false);
            this.setBaskingCooldown(400);
        }
        return super.hurtServer(level, damageSource, amount);
    }

    private void clearLoveIfGrown() {
        if (this.getAge() != 0) {
            this.inLove = 0;
        }
    }

    private void spawnHeartParticles(int count) {
        for (int i = 0; i < count; i++) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.level().addParticle(ParticleTypes.HEART, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), d, e, f);
        }
    }

    public boolean canFallInLove() {
        return this.inLove <= 0;
    }

    public void setInLove(@Nullable Player player) {
        this.inLove = 600;
        if (player != null) {
            this.loveCause = player.getUUID();
        }
        this.level().broadcastEntityEvent(this, (byte) 18);
    }

    public @Nullable ServerPlayer getLoveCause() {
        if (this.loveCause == null) {
            return null;
        }
        Player player = this.level().getPlayerByUUID(this.loveCause);
        return player instanceof ServerPlayer serverPlayer ? serverPlayer : null;
    }

    public boolean isInLove() {
        return this.inLove > 0;
    }

    public void resetLove() {
        this.inLove = 0;
    }

    public boolean canMate(Sunfish other) {
        return other != this && other.getClass() == this.getClass() && this.isInLove() && other.isInLove();
    }

    @Override public void handleEntityEvent(byte id) {
        if (id == 18) {
            this.spawnHeartParticles(7);
        }
        else {
            super.handleEntityEvent(id);
        }
    }

    @Override public void tick() {
        super.tick();
        if (!this.isBaby()) {
            if (this.isInWater()) {
                if (this.getPose() != Pose.SWIMMING) {
                    this.setPose(Pose.SWIMMING);
                }
            }
            else if (this.getPose() != Pose.STANDING) {
                this.setPose(Pose.STANDING);
            }
        }
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    public void growUpIfNeeded() {
        int currentCategory = this.getSunfishAge();
        if (currentCategory != this.lastAgeCategory) {
            this.lastAgeCategory = currentCategory;
            switch (currentCategory) {
                case -2 -> {
                    Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(1.0);
                    this.setHealth(1.0F);
                }
                case -1 -> {
                    Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(4.0);
                    this.setHealth(4.0F);
                }
                default -> {
                    Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(20.0);
                    this.setHealth(20.0F);
                }
            }
            this.refreshDimensions();
        }
    }

    @Override public EntityDimensions getDefaultDimensions(Pose pose) {
        int age = this.getSunfishAge();
        if (age == -2) {
            return EntityDimensions.scalable(0.3F, 0.3F);
        } else if (age == -1) {
            return EntityDimensions.scalable(0.6F, 0.6F);
        } else {
            return this.isBasking() ? EntityDimensions.scalable(1.5F, 0.5F) : EntityDimensions.scalable(1.2F, 1.5F);
        }
    }

    private void setupAnimationStates() {
        if (!this.isBaby()) {
            this.prevBaskingProgress = this.baskingProgress;
            float target = this.isBasking() ? 1.0F : 0.0F;
            this.baskingProgress = this.baskingProgress + (target - this.baskingProgress) * 0.05F;
            if (this.isInWater()) {
                if (this.idleAnimationTimeout <= 0) {
                    this.idleAnimationTimeout = 80;
                    this.idleAnimationState.start(this.tickCount);
                }
                else {
                    this.idleAnimationTimeout--;
                }
            }
            else if (this.landAnimationTimeout <= 0) {
                this.landAnimationTimeout = 40;
                this.landAnimationState.start(this.tickCount);
            }
            else {
                this.landAnimationTimeout--;
            }
        }
    }

    public int getSunfishAge() {
        if (this.isBaby()) {
            return this.getAge() < -24000 ? -2 : -1;
        }
        return 0;
    }

    @Override public boolean isBaby() {
        return this.getAge() < 0;
    }

    @Override protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(AGE, 0);
        builder.define(FROM_BUCKET, false);
        builder.define(DATA_VARIANT, SunfishVariant.EARL.getId());
        builder.define(IS_BASKING, false);
        builder.define(BASKING_COOLDOWN, this.random.nextInt(200) + 200);
        builder.define(AGE_LOCKED, false);
    }

    @Override public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);
        if (IS_BASKING.equals(accessor)) {
            this.refreshDimensions();
        }
    }

    @Override protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("FromBucket", this.fromBucket());
        output.putBoolean("IsBasking", this.isBasking());
        output.putInt("Age", this.getAge());
        output.putInt("BaskingCooldown", this.getBaskingCooldown());
        output.putBoolean("AgeLocked", this.isAgeLocked());
        output.putInt("InLove", this.inLove);
        output.putString("Variant", this.getVariant().variantName());
        if (this.loveCause != null) {
            output.store("LoveCause", UUIDUtil.CODEC, this.loveCause);
        }
    }

    @Override protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setFromBucket(input.getBooleanOr("FromBucket", false));
        this.setBasking(input.getBooleanOr("IsBasking", false));
        this.setBaskingCooldown(input.getIntOr("BaskingCooldown", 200));
        this.setAgeLocked(input.getBooleanOr("AgeLocked", false));
        this.setAge(input.getIntOr("Age", 0));
        this.inLove = input.getIntOr("InLove", 0);
        this.loveCause = input.read("LoveCause", UUIDUtil.CODEC).orElse(null);
        this.setVariant(SunfishVariant.byName(input.getStringOr("Variant", SunfishVariant.EARL.variantName())));
    }

    public int getAge() {
        return this.entityData.get(AGE);
    }

    public void setAge(int age) {
        this.entityData.set(AGE, age);
    }

    public int getBaskingCooldown() {
        return this.entityData.get(BASKING_COOLDOWN);
    }

    public void setBaskingCooldown(int cooldown) {
        this.entityData.set(BASKING_COOLDOWN, cooldown);
    }

    @Override public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    @Override public void saveToBucketTag(ItemStack itemStack) {
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, tag -> {
            tag.putInt("Age", this.getAge());
            tag.putInt("BaskingCooldown", this.getBaskingCooldown());
            Bucketable.saveDefaultDataToBucketTag(this, itemStack);
            tag.putString("Variant", this.getVariant().variantName());
        });
    }

    @Override public void loadFromBucketTag(CompoundTag tag) {
        this.setAge(tag.getInt("Age").orElse(-24000));
        this.setBaskingCooldown(tag.getInt("BaskingCooldown").orElse(this.random.nextInt(200) + 200));
        this.setVariant(SunfishVariant.byName(tag.getString("Variant").orElse(SunfishVariant.EARL.variantName())));
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
    }

    public void setVariant(SunfishVariant variant) {
        this.entityData.set(DATA_VARIANT, variant.getId());
    }

    public SunfishVariant getVariant() {
        return SunfishVariant.byId(this.entityData.get(DATA_VARIANT));
    }

    public void setBasking(boolean basking) {
        if (basking != this.isBasking()) {
            this.entityData.set(IS_BASKING, basking);
            this.refreshDimensions();
        }
    }

    public boolean isBasking() {
        return this.entityData.get(IS_BASKING);
    }

    public boolean isAgeLocked() {
        return this.entityData.get(AGE_LOCKED);
    }

    public void setAgeLocked(boolean locked) {
        this.entityData.set(AGE_LOCKED, locked);
    }

    private void setAgeLockedData() {
        this.setAgeLocked(!this.isAgeLocked());
        this.ageLockParticleTimer = 40;
    }

    @Override public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }

    @Override public void travel(Vec3 movementInput) {
        if (this.isBasking()) {
            this.setDeltaMovement(Vec3.ZERO);
            return;
        }
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, movementInput);
            this.move(MoverType.SELF, this.getDeltaMovement());
            Vec3 motion = this.getDeltaMovement().scale(0.9);
            if ((!this.isUnderWater() || this.level().getFluidState(this.blockPosition().above(2)).isEmpty()) && this.getNavigation().isDone()) {
                motion = motion.add(0.0, -0.02, 0.0);
            }
            this.setDeltaMovement(motion);
        }
        else {
            super.travel(movementInput);
        }
    }

    @Override public void baseTick() {
        int air = this.getAirSupply();
        super.baseTick();
        int age = this.getAge();
        this.growUpIfNeeded();
        if (age < 0 && !this.isAgeLocked()) {
            this.setAge(age + 1);
        }
        this.handleAirSupply(air);
    }

    protected void handleAirSupply(int air) {
        if (this.isAlive() && !this.isInWater()) {
            this.setAirSupply(air - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                if (this.level() instanceof ServerLevel serverLevel) {
                    this.hurtServer(serverLevel, this.damageSources().drown(), 2.0F);
                }
            }
        }
        else {
            this.setAirSupply(300);
        }
    }

    @Override protected PathNavigation createNavigation(Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    @Override public boolean removeWhenFarAway(double distance) {
        return !this.fromBucket() && !this.hasCustomName();
    }

    @Override public int getAmbientSoundInterval() {
        return 120;
    }

    @Override public int getBaseExperienceReward(ServerLevel level) {
        return 1 + level.getRandom().nextInt(3);
    }

    @Override public boolean isPushedByFluid() {
        return false;
    }

    @Override public boolean canBeLeashed() {
        return true;
    }

    @Override protected boolean canRide(Entity entity) {
        return !(entity instanceof Boat) && super.canRide(entity);
    }

    @Override public ItemStack getBucketItemStack() {
        return ModItems.BABY_SUNFISH_BUCKET.getDefaultInstance();
    }

    @Override public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    @Override protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    @Override protected SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
    }

    @Override protected @Nullable SoundEvent getAmbientSound() {
        return null;
    }

    @Override protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.COD_HURT;
    }

    @Override protected void playStepSound(BlockPos pos, BlockState state) {}

    private static class SunfishBreedGoal extends Goal {

        private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0).ignoreLineOfSight();
        private final Sunfish sunfish;
        private final ServerLevel level;
        private @Nullable Sunfish partner;
        private int loveTime;
        private final double speedModifier;

        SunfishBreedGoal(Sunfish sunfish, double speedModifier) {
            this.sunfish = sunfish;
            this.level = getServerLevel(sunfish);
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override public boolean canUse() {
            if (!this.sunfish.isInLove()) {
                return false;
            }
            this.partner = this.getFreePartner();
            return this.partner != null;
        }

        @Override public boolean canContinueToUse() {
            return this.partner != null && this.partner.isAlive() && this.partner.isInLove() && this.loveTime < 60;
        }

        @Override public void stop() {
            this.partner = null;
            this.loveTime = 0;
        }

        @Override public void tick() {
            if (this.partner == null) {
                return;
            }
            this.sunfish.getLookControl().setLookAt(this.partner, 10.0F, this.sunfish.getMaxHeadXRot());
            this.sunfish.getNavigation().moveTo(this.partner, this.speedModifier);
            this.loveTime++;
            if (this.loveTime >= this.adjustedTickDelay(60) && this.sunfish.distanceToSqr(this.partner) < 9.0) {
                this.breed();
            }
        }

        private @Nullable Sunfish getFreePartner() {
            List<Sunfish> nearby = this.level.getNearbyEntities(Sunfish.class, PARTNER_TARGETING, this.sunfish, this.sunfish.getBoundingBox().inflate(8.0));
            double closest = Double.MAX_VALUE;
            Sunfish result = null;
            for (Sunfish candidate : nearby) {
                double distance = this.sunfish.distanceToSqr(candidate);
                if (this.sunfish.canMate(candidate) && distance < closest) {
                    result = candidate;
                    closest = distance;
                }
            }
            return result;
        }

        private void breed() {
            int litterSize = this.sunfish.random.nextInt(4) + 3;
            for (int i = 0; i < litterSize; i++) {
                this.sunfish.spawnChildFromBreeding(this.level, this.partner);
            }
        }

    }

    private static class SunfishBaskingGoal extends Goal {

        private final Sunfish sunfish;
        private int baskDuration;
        private int failureGracePeriod;

        SunfishBaskingGoal(Sunfish sunfish) {
            this.sunfish = sunfish;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        private boolean canBask() {
            return this.sunfish.isUnderWater()
                && !this.sunfish.isInLove()
                && this.sunfish.level().isBrightOutside()
                && !this.sunfish.isBaby()
                && this.sunfish.level().getBrightness(LightLayer.SKY, this.sunfish.blockPosition()) > 7;
        }

        @Override public boolean canUse() {
            return this.sunfish.getBaskingCooldown() == 0 && this.sunfish.getNavigation().isDone() && this.canBask();
        }

        @Override public void start() {
            this.baskDuration = 1000 + this.sunfish.random.nextInt(200);
            this.failureGracePeriod = 20;
            this.sunfish.setBasking(true);
            this.sunfish.getNavigation().stop();
        }

        @Override public boolean canContinueToUse() {
            if (!this.canBask()) {
                this.failureGracePeriod--;
            } else {
                this.failureGracePeriod = 20;
            }
            return this.failureGracePeriod > 0 && this.baskDuration > 0;
        }

        @Override public void tick() {
            this.baskDuration--;
        }

        @Override public void stop() {
            this.sunfish.setBasking(false);
            this.sunfish.setBaskingCooldown(1000 + this.sunfish.random.nextInt(1000));
        }

    }

    private static class SunfishMoveControl extends SmoothSwimmingMoveControl {

        private final Sunfish sunfish;

        SunfishMoveControl(Sunfish sunfish) {
            super(sunfish, 85, 10, 0.02F, 0.1F, false);
            this.sunfish = sunfish;
        }

        @Override public void tick() {
            if (!this.sunfish.isBasking()) {
                super.tick();
            }
        }

    }

}