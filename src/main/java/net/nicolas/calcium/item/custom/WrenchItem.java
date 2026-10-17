package net.nicolas.calcium.item.custom;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DebugStickState;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.nicolas.calcium.sound.ModSoundGroups;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WrenchItem extends Item {

    private static final Set<String> ALLOWED_PROPERTIES = Set.of("facing", "axis", "rotation", "orientation", "hinge", "half", "shape", "north", "east", "south", "west");

    public WrenchItem(Properties properties) {
        super(properties);
    }

    @Override public boolean canDestroyBlock(ItemStack itemStack, BlockState state, Level level, BlockPos pos, LivingEntity user) {
        return false;
    }

    @Override public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();
        if (player == null || !canEdit(state)) {
            return InteractionResult.PASS;
        }

        ItemStack stack = context.getItemInHand();
        BlockState newState = nextState(level, pos, state, stack, player.isSecondaryUseActive());
        if (newState == null) {
            return InteractionResult.FAIL;
        }
        if (player instanceof ServerPlayer serverPlayer) {
            this.cycleValue(serverPlayer, pos, state, newState, stack, context.getHand());
        }
        return InteractionResult.SUCCESS;
    }

    public static boolean canEdit(BlockState state) {
        return !applicableProperties(state).isEmpty();
    }

    public static boolean canSelect(BlockState state) {
        return applicableProperties(state).size() > 1;
    }

    public static void registerEvents() {
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (!world.isClientSide() && player instanceof ServerPlayer serverPlayer && serverPlayer.getMainHandItem().getItem() instanceof WrenchItem wrench) {
                if (serverPlayer.isWithinBlockInteractionRange(pos, 1.0)) {
                    wrench.selectProperty(serverPlayer, pos, serverPlayer.getMainHandItem(), InteractionHand.MAIN_HAND);
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });
    }

    private void cycleValue(ServerPlayer player, BlockPos pos, BlockState state, BlockState newState, ItemStack stack, InteractionHand hand) {
        ServerLevel level = player.level();
        level.setBlock(pos, newState, 18);
        spawnBreakParticles(level, pos, state);
        finishUse(player, pos, stack, hand, ModSoundGroups.WRENCH_USE);
    }

    private static @Nullable BlockState nextState(Level level, BlockPos pos, BlockState state, ItemStack stack, boolean backward) {
        List<Property<?>> properties = applicableProperties(state);
        DebugStickState wrenchState = stack.get(DataComponents.DEBUG_STICK_STATE);
        if (properties.isEmpty() || wrenchState == null) {
            return null;
        }

        Property<?> property = Objects.requireNonNullElse(selectedProperty(wrenchState, state, properties), properties.getFirst());
        return cycleState(level, pos, state, property, backward);
    }

    private void selectProperty(ServerPlayer player, BlockPos pos, ItemStack stack, InteractionHand hand) {
        BlockState state = player.level().getBlockState(pos);
        List<Property<?>> properties = applicableProperties(state);
        DebugStickState wrenchState = stack.get(DataComponents.DEBUG_STICK_STATE);
        if (properties.size() < 2 || wrenchState == null) {
            return;
        }

        Property<?> next = getRelative(properties, selectedProperty(wrenchState, state, properties), player.isSecondaryUseActive());
        stack.set(DataComponents.DEBUG_STICK_STATE, wrenchState.withProperty(state.typeHolder(), next));
        finishUse(player, pos, stack, hand, ModSoundGroups.WRENCH_SELECT);
    }

    private static @Nullable Property<?> selectedProperty(DebugStickState wrenchState, BlockState state, List<Property<?>> properties) {
        Property<?> property = wrenchState.properties().get(state.typeHolder());
        return property != null && properties.contains(property) ? property : null;
    }

    private static void finishUse(ServerPlayer player, BlockPos pos, ItemStack stack, InteractionHand hand, SoundEvent sound) {
        player.level().playSound(null, pos, sound, SoundSource.PLAYERS, 1.0F, 1.0F);
        stack.hurtAndBreak(1, player, hand);
    }

    private static List<Property<?>> applicableProperties(BlockState state) {
        if (state.getBlock() instanceof WallBlock && !state.getValue(WallBlock.UP)) {
            return List.of();
        }
        return state.getBlock().getStateDefinition().getProperties().stream().filter(candidate -> ALLOWED_PROPERTIES.contains(candidate.getName())).toList();
    }

    private static void spawnBreakParticles(ServerLevel level, BlockPos pos, BlockState state) {
        BlockParticleOption particle = new BlockParticleOption(ParticleTypes.BLOCK, state);
        state.getShape(level, pos).forAllBoxes((x1, y1, z1, x2, y2, z2) -> {
            double widthX = Math.min(1.0, x2 - x1);
            double widthY = Math.min(1.0, y2 - y1);
            double widthZ = Math.min(1.0, z2 - z1);
            int count = Math.max(2, Mth.ceil(widthX / 0.25)) * Math.max(2, Mth.ceil(widthY / 0.25)) * Math.max(2, Mth.ceil(widthZ / 0.25));
            level.sendParticles(particle, pos.getX() + (x1 + x2) / 2, pos.getY() + (y1 + y2) / 2, pos.getZ() + (z1 + z2) / 2, Math.max(1, Math.round(count * 0.4F)), widthX / 4, widthY / 4, widthZ / 4, 0.1);
        });
    }

    private static <T extends Comparable<T>> @Nullable BlockState cycleState(Level level, BlockPos pos, BlockState state, Property<T> property, boolean backward) {
        T value = state.getValue(property);
        for (int remaining = property.getPossibleValues().size() - 1; remaining > 0; remaining--) {
            value = getRelative(property.getPossibleValues(), value, backward);
            BlockState candidate = state.setValue(property, value);
            if (candidate.canSurvive(level, pos)) {
                return candidate;
            }
        }
        return null;
    }

    private static <T> T getRelative(Iterable<T> collection, @Nullable T current, boolean backward) {
        return backward ? Util.findPreviousInIterable(collection, current) : Util.findNextInIterable(collection, current);
    }

}