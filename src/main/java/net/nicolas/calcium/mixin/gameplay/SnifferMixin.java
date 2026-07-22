package net.nicolas.calcium.mixin.gameplay;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.nicolas.calcium.mixin.accessors.SnifferAccessor;
import net.nicolas.calcium.core.network.OpenSnifferInventoryPayload;
import net.nicolas.calcium.screen.sniffer.SnifferChestAccess;
import net.nicolas.calcium.screen.sniffer.SnifferInventoryScreenHandler;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sniffer.class)
public abstract class SnifferMixin extends Animal implements HasCustomInventoryScreen, SnifferChestAccess {

    @Unique private static final EntityDataAccessor<Boolean> DATA_HAS_CHEST = SynchedEntityData.defineId(SnifferMixin.class, EntityDataSerializers.BOOLEAN);

    @Unique private SimpleContainer calcium$inventory;

    protected SnifferMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void calcium$defineChestData(SynchedEntityData.Builder entityData, CallbackInfo ci) {
        entityData.define(DATA_HAS_CHEST, false);
    }

    @Override public boolean canUseSlot(EquipmentSlot slot) {
        if (slot == EquipmentSlot.SADDLE) {
            return this.isAlive() && !this.isBaby();
        }
        return super.canUseSlot(slot);
    }

    @Unique @Override public boolean calcium$hasChest() {
        return this.entityData.get(DATA_HAS_CHEST);
    }

    @Unique private void calcium$setChest(boolean hasChest) {
        this.entityData.set(DATA_HAS_CHEST, hasChest);
    }

    @Unique @Override public Container calcium$getInventory() {
        if (this.calcium$inventory == null) {
            this.calcium$createInventory();
        }
        return this.calcium$inventory;
    }

    @Unique private void calcium$createInventory() {
        SimpleContainer old = this.calcium$inventory;
        this.calcium$inventory = new SimpleContainer(this.calcium$hasChest() ? SnifferInventoryScreenHandler.CHEST_COLUMNS * SnifferInventoryScreenHandler.CHEST_ROWS : 0);
        if (old != null) {
            int max = Math.min(old.getContainerSize(), this.calcium$inventory.getContainerSize());
            for (int slot = 0; slot < max; slot++) {
                ItemStack stack = old.getItem(slot);
                if (!stack.isEmpty()) {
                    this.calcium$inventory.setItem(slot, stack.copy());
                }
            }
        }
    }

    @Unique private void calcium$equipChest(Player player, ItemStack itemStack) {
        this.calcium$setChest(true);
        this.playSound(SoundEvents.DONKEY_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        itemStack.consume(1, player);
        this.calcium$createInventory();
    }

    @Override protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("ChestedSniffer", this.calcium$hasChest());
        if (this.calcium$hasChest()) {
            ValueOutput.TypedOutputList<ItemStackWithSlot> items = output.list("Items", ItemStackWithSlot.CODEC);
            for (int i = 0; i < this.calcium$getInventory().getContainerSize(); i++) {
                ItemStack stack = this.calcium$getInventory().getItem(i);
                if (!stack.isEmpty()) {
                    items.add(new ItemStackWithSlot(i, stack));
                }
            }
        }
    }

    @Override protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.calcium$setChest(input.getBooleanOr("ChestedSniffer", false));
        this.calcium$createInventory();
        if (this.calcium$hasChest()) {
            for (ItemStackWithSlot item : input.listOrEmpty("Items", ItemStackWithSlot.CODEC)) {
                if (item.isValidInContainer(this.calcium$getInventory().getContainerSize())) {
                    this.calcium$getInventory().setItem(item.slot(), item.stack());
                }
            }
        }
    }

    @Override protected void dropEquipment(ServerLevel level) {
        super.dropEquipment(level);
        if (this.calcium$hasChest()) {
            for (int i = 0; i < this.calcium$getInventory().getContainerSize(); i++) {
                ItemStack stack = this.calcium$getInventory().getItem(i);
                if (!stack.isEmpty() && !EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)) {
                    this.spawnAtLocation(level, stack);
                }
            }
            this.spawnAtLocation(level, Blocks.CHEST);
            this.calcium$setChest(false);
        }
    }

    @Override public void openCustomInventoryScreen(Player player) {
        if (this.isBaby() || !(player instanceof ServerPlayer serverPlayer) || this.level().isClientSide()) {
            return;
        }
        Sniffer self = (Sniffer) (Object) this;
        serverPlayer.openMenu(new SimpleMenuProvider((containerId, inventory, openingPlayer) -> new SnifferInventoryScreenHandler(containerId, inventory, self), self.getDisplayName())).ifPresent(containerId -> ServerPlayNetworking.send(serverPlayer, new OpenSnifferInventoryPayload(containerId, self.getId())));
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void calcium$rideOrEquip(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (player.isSecondaryUseActive() && !this.isBaby()) {
            this.openCustomInventoryScreen(player);
            cir.setReturnValue(InteractionResult.SUCCESS);
            return;
        }

        if (this.isBaby() || this.isVehicle()) {
            return;
        }

        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(ItemTags.SNIFFER_FOOD)) {
            return;
        }

        if (!this.calcium$hasChest() && itemStack.is(Items.CHEST)) {
            this.calcium$equipChest(player, itemStack);
            cir.setReturnValue(InteractionResult.SUCCESS);
            return;
        }

        if (!itemStack.isEmpty()) {
            InteractionResult interactionResult = itemStack.interactLivingEntity(player, this, hand);
            if (interactionResult.consumesAction()) {
                cir.setReturnValue(interactionResult);
                return;
            }
        }

        if (!this.level().isClientSide()) {
            player.setYRot(this.getYRot());
            player.setXRot(this.getXRot());
            player.startRiding(this);
        }
        cir.setReturnValue(InteractionResult.SUCCESS);
    }

    @Override public @Nullable LivingEntity getControllingPassenger() {
        return this.isSaddled() && this.getFirstPassenger() instanceof Player passenger ? passenger : super.getControllingPassenger();
    }

    @Override protected Vec3 getRiddenInput(Player controller, Vec3 selfInput) {
        float sideways = controller.xxa * 0.5F;
        float forward = controller.zza;
        if (forward <= 0.0F) {
            forward *= 0.25F;
        }
        return new Vec3(sideways, 0.0, forward);
    }

    @Override protected float getRiddenSpeed(Player controller) {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.5F;
    }

    @Unique private Vec2 calcium$getRiddenRotation(Player controller) {
        boolean digging = this.entityData.get(SnifferAccessor.calcium$getDataState()) == Sniffer.State.DIGGING;
        return digging ? new Vec2(this.getXRot(), this.getYRot()) : new Vec2(controller.getXRot() * 0.5F, controller.getYRot());
    }

    @Override protected void tickRidden(Player controller, Vec3 riddenInput) {
        super.tickRidden(controller, riddenInput);
        Vec2 rotation = this.calcium$getRiddenRotation(controller);
        this.setRot(rotation.y, rotation.x);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
    }

    @Override protected void positionRider(Entity passenger, Entity.MoveFunction moveFunction) {
        super.positionRider(passenger, moveFunction);
        if (passenger instanceof LivingEntity livingEntity) {
            livingEntity.yBodyRot = this.yBodyRot;
        }
    }

    @Unique private @Nullable Vec3 calcium$getDismountLocationInDirection(Vec3 direction, LivingEntity passenger) {
        double targetX = this.getX() + direction.x;
        double targetY = this.getBoundingBox().minY;
        double targetZ = this.getZ() + direction.z;
        BlockPos.MutableBlockPos targetBlockPos = new BlockPos.MutableBlockPos();
        for (Pose dismountPose : passenger.getDismountPoses()) {
            targetBlockPos.set(targetX, targetY, targetZ);
            double dismountJumpLimit = this.getBoundingBox().maxY + 0.75;
            do {
                double blockFloorHeight = this.level().getBlockFloorHeight(targetBlockPos);
                if (targetBlockPos.getY() + blockFloorHeight > dismountJumpLimit) {
                    break;
                }
                if (DismountHelper.isBlockFloorValid(blockFloorHeight)) {
                    AABB poseCollisionBox = passenger.getLocalBoundsForPose(dismountPose);
                    Vec3 location = new Vec3(targetX, targetBlockPos.getY() + blockFloorHeight, targetZ);
                    if (DismountHelper.canDismountTo(this.level(), passenger, poseCollisionBox.move(location))) {
                        passenger.setPose(dismountPose);
                        return location;
                    }
                }
                targetBlockPos.move(Direction.UP);
            } while (targetBlockPos.getY() < dismountJumpLimit);
        }
        return null;
    }

    @Override public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        Vec3 mainHandDirection = getCollisionHorizontalEscapeVector(this.getBbWidth(), passenger.getBbWidth(), this.getYRot() + (passenger.getMainArm() == HumanoidArm.RIGHT ? 90.0F : -90.0F));
        Vec3 mainHandLocation = this.calcium$getDismountLocationInDirection(mainHandDirection, passenger);
        if (mainHandLocation != null) {
            return mainHandLocation;
        }
        Vec3 offHandDirection = getCollisionHorizontalEscapeVector(this.getBbWidth(), passenger.getBbWidth(), this.getYRot() + (passenger.getMainArm() == HumanoidArm.LEFT ? 90.0F : -90.0F));
        Vec3 offHandLocation = this.calcium$getDismountLocationInDirection(offHandDirection, passenger);
        return offHandLocation != null ? offHandLocation : this.position();
    }

}