package net.nicolas.calcium.mixin.gameplay;

import com.google.common.collect.ImmutableMap;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.advancements.AdvancementTab;
import net.minecraft.client.gui.screens.advancements.AdvancementTabType;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(AdvancementTab.class)
public abstract class AdvancementTabIconMixin {

    @Unique private static final Map<Identifier, ItemLike> TAB_ICONS = new ImmutableMap.Builder<Identifier, ItemLike>()

        .put(Identifier.withDefaultNamespace("story/root"), Items.GRASS_BLOCK)
        .put(Identifier.withDefaultNamespace("husbandry/root"), Items.HAY_BLOCK)
        .put(Identifier.withDefaultNamespace("adventure/root"), Items.MAGMA_BLOCK)
        .put(Identifier.withDefaultNamespace("nether/root"), Items.NETHER_BRICKS)
        .put(Identifier.withDefaultNamespace("end/root"), Items.END_STONE)

        .build();

    @Mutable @Shadow @Final private ItemStack icon;

    @Shadow @Final private AdvancementNode rootNode;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void calcium$overrideTabIcon(Minecraft minecraft, AdvancementsScreen screen, AdvancementTabType type, int index, AdvancementNode rootNode, DisplayInfo display, CallbackInfo ci) {
        ItemLike icon = TAB_ICONS.get(this.rootNode.holder().id());
        if (icon != null) {
            this.icon = new ItemStack(icon);
        }
    }

}
