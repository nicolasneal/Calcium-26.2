package net.nicolas.calcium.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;

@Mixin(AdvancementsScreen.class)
public abstract class AdvancementsScreenMixin {

    @Unique private static final List<Identifier> TAB_ORDER = List.of(
        Identifier.withDefaultNamespace("story/root"),
        Identifier.withDefaultNamespace("husbandry/root"),
        Identifier.withDefaultNamespace("adventure/root"),
        Identifier.withDefaultNamespace("nether/root"),
        Identifier.withDefaultNamespace("end/root")
    );

    @ModifyArg(method = "onAddAdvancementRoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/advancements/AdvancementTab;create(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/screens/advancements/AdvancementsScreen;ILnet/minecraft/advancements/AdvancementNode;)Lnet/minecraft/client/gui/screens/advancements/AdvancementTab;"), index = 2)
    private int calcium$orderTabs(int index, @Local(argsOnly = true, name = "root") AdvancementNode root) {
        int position = TAB_ORDER.indexOf(root.holder().id());
        return position >= 0 ? position : TAB_ORDER.size() + index;
    }

}