package net.nicolas.calcium.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.nicolas.calcium.block.tag.ModBlockTags;
import net.nicolas.calcium.sound.ModSoundGroups;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class GiantClam extends Mob {

    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(GiantClam.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ANCHORED = SynchedEntityData.defineId(GiantClam.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> OPEN_TICKS = SynchedEntityData.defineId(GiantClam.class, EntityDataSerializers.INT);
    private static final byte EVENT_PLAY_OPEN_ANIMATION = 8;

    public final AnimationState anchoringAnimationState = new AnimationState();
    public final AnimationState openAnimationState = new AnimationState();

    public GiantClam(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
        Objects.requireNonNull(this.getAttribute(Attributes.STEP_HEIGHT)).setBaseValue(1.0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.ARMOR, 5.0).add(Attributes.MOVEMENT_SPEED, 1.2F).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
        RandomSource random = level.getRandom();
        GiantClamVariant.BaseColor baseColor = Util.getRandom(GiantClamVariant.BaseColor.values(), random);
        GiantClamVariant.Pattern pattern = Util.getRandom(GiantClamVariant.Pattern.values(), random);
        DyeColor dyeColor = Util.getRandom(DyeColor.values(), random);
        this.setVariant(new GiantClamVariant.Variant(baseColor, pattern, dyeColor));
        this.setAnchored(true);
        return result;
    }

    @Override public boolean canBeCollidedWith(@Nullable Entity other) {
        return this.isAlive() && this.onGround();
    }

    @Override public void push(Entity entity) {}

    @Override protected void tickHeadTurn(float yBodyRotT) {
        this.yBodyRotO = this.yRotO;
        this.yBodyRot = this.getYRot();
    }

    @Override public void aiStep() {

        super.aiStep();
        if (this.level().isClientSide()) {
            return;
        }

        BlockPos below = this.blockPosition().below();
        if (this.level().getBlockState(below).is(ModBlockTags.GIANT_CLAM_ANCHOR)) {
            if (!this.isAnchored()) {
                this.setAnchored(true);
                this.anchoringAnimationState.start(this.tickCount);
            }
        } else {
            this.setAnchored(false);
            this.anchoringAnimationState.stop();
        }

        if (this.isAnchored() && this.isInWater() && this.isAlive() && this.level() instanceof ServerLevel serverLevel) {
            if (this.random.nextInt(600) == 0 && this.getOpenTicks() == 0) {
                this.level().broadcastEntityEvent(this, EVENT_PLAY_OPEN_ANIMATION);
                this.setOpenTicks(120);
                this.playSound(ModSoundGroups.GIANT_CLAM_BUBBLES, 1.0F, 1.0F);
            }

            if (this.getOpenTicks() > 0) {
                this.setOpenTicks(this.getOpenTicks() - 1);
                this.refillAirAbove();
            }

            if (this.getOpenTicks() > 18 && this.getOpenTicks() < 110 && this.random.nextFloat() < 0.5F) {
                serverLevel.sendParticles(ParticleTypes.BUBBLE_COLUMN_UP, this.getRandomX(0.5), this.getY() + 0.5, this.getRandomZ(0.5), 1, 0.0, 0.0, 0.0, 0.0);
            }
        }

    }

    private void refillAirAbove() {
        AABB airPocket = this.getBoundingBox().setMaxY(this.getBoundingBox().maxY + 2.0).setMinY(this.getBoundingBox().maxY);
        for (Player player : this.level().getEntitiesOfClass(Player.class, airPocket)) {
            player.setAirSupply(Math.min(player.getAirSupply() + 4, player.getMaxAirSupply()));
        }
    }

    @Override public void handleEntityEvent(byte id) {
        if (id == EVENT_PLAY_OPEN_ANIMATION) {
            this.openAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override public void travel(Vec3 movementInput) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, movementInput);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.05, 0.0));
        } else {
            super.travel(movementInput);
        }
    }

    @Override protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT, 0);
        builder.define(ANCHORED, false);
        builder.define(OPEN_TICKS, 0);
    }

    @Override protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Variant", this.getPackedVariant());
        output.putInt("OpenTicks", this.getOpenTicks());
        output.putBoolean("Anchored", this.isAnchored());
    }

    @Override protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setPackedVariant(input.getIntOr("Variant", 0));
        this.setOpenTicks(input.getIntOr("OpenTicks", 0));
        this.setAnchored(input.getBooleanOr("Anchored", false));
    }

    public int getOpenTicks() {
        return this.entityData.get(OPEN_TICKS);
    }

    public void setOpenTicks(int ticks) {
        this.entityData.set(OPEN_TICKS, ticks);
    }

    public boolean isAnchored() {
        return this.entityData.get(ANCHORED);
    }

    public void setAnchored(boolean anchored) {
        this.entityData.set(ANCHORED, anchored);
    }

    @Override protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return this.isUnderWater() ? ModSoundGroups.GIANT_CLAM_HURT : ModSoundGroups.GIANT_CLAM_HURT_LAND;
    }

    @Override protected @Nullable SoundEvent getDeathSound() {
        return this.isUnderWater() ? ModSoundGroups.GIANT_CLAM_DEATH : ModSoundGroups.GIANT_CLAM_DEATH_LAND;
    }

    public void setPackedVariant(int packed) {
        this.entityData.set(DATA_VARIANT, packed);
    }

    public int getPackedVariant() {
        return this.entityData.get(DATA_VARIANT);
    }

    public void setVariant(GiantClamVariant.Variant variant) {
        this.setPackedVariant(packVariant(variant.baseColor(), variant.pattern(), variant.dyeColor()));
    }

    public GiantClamVariant.Variant getVariant() {
        return new GiantClamVariant.Variant(this.getBaseColor(), this.getPattern(), this.getDyeColor());
    }

    public static int packVariant(GiantClamVariant.BaseColor baseColor, GiantClamVariant.Pattern pattern, DyeColor dyeColor) {
        return baseColor.getId() << 8 | pattern.getId() << 4 | dyeColor.getId();
    }

    public GiantClamVariant.BaseColor getBaseColor() {
        return getBaseColor(this.getPackedVariant());
    }

    public GiantClamVariant.Pattern getPattern() {
        return getPattern(this.getPackedVariant());
    }

    public DyeColor getDyeColor() {
        return getDyeColor(this.getPackedVariant());
    }

    public static GiantClamVariant.BaseColor getBaseColor(int packed) {
        return GiantClamVariant.BaseColor.byId(packed >> 8 & 15);
    }

    public static GiantClamVariant.Pattern getPattern(int packed) {
        return GiantClamVariant.Pattern.byId(packed >> 4 & 15);
    }

    public static DyeColor getDyeColor(int packed) {
        return DyeColor.byId(packed & 15);
    }

    @Override protected void pushEntities() {
        super.pushEntities();
    }

    @Override public boolean isPushedByFluid() {
        return false;
    }

    @Override public boolean canBeLeashed() {
        return false;
    }

    @Override protected void playStepSound(BlockPos pos, BlockState state) {}

    public static boolean checkSpawnRules(EntityType<GiantClam> entityType, ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        int seaLevel = level.getSeaLevel();
        int minY = level.getMinY();
        boolean inRangeUnderwater = pos.getY() >= minY && pos.getY() < seaLevel;
        boolean waterSpawn = inRangeUnderwater
            && level.getFluidState(pos).is(FluidTags.WATER)
            && level.getBlockState(pos.below()).is(ModBlockTags.GIANT_CLAM_ANCHOR);
        boolean justAboveSea = pos.getY() >= seaLevel && pos.getY() <= seaLevel + 5;
        boolean beachSpawn = justAboveSea
            && level.getBlockState(pos).isAir()
            && (level.getBlockState(pos.below()).is(BlockTags.SAND) || level.getBlockState(pos.below()).is(Blocks.GRAVEL));
        return beachSpawn || waterSpawn;
    }

}