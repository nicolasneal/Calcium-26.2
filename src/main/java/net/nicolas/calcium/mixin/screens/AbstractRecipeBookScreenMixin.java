package net.nicolas.calcium.mixin.screens;

import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.nicolas.calcium.core.CalciumClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractRecipeBookScreen.class)
public abstract class AbstractRecipeBookScreenMixin<T extends RecipeBookMenu> extends AbstractContainerScreen<T> {

    @Shadow @Final private RecipeBookComponent<?> recipeBookComponent;

    @Shadow protected abstract void onRecipeBookButtonClick();

    public AbstractRecipeBookScreenMixin(T menu, net.minecraft.world.entity.player.Inventory inventory, net.minecraft.network.chat.Component title) {
        super(menu, inventory, title);
    }

    @Redirect(method = "initButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractRecipeBookScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"))
    private GuiEventListener calcium$hideRecipeBookButton(AbstractRecipeBookScreen<T> instance, GuiEventListener widget) {
        return widget;
    }

    @Inject(method = "keyPressed", at = @At("TAIL"), cancellable = true)
    private void calcium$toggleRecipeBookWithR(KeyEvent event, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) return;
        if (!CalciumClient.TOGGLE_RECIPE_BOOK_KEY.matches(event)) return;

        this.recipeBookComponent.toggleVisibility();
        this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
        this.onRecipeBookButtonClick();
        cir.setReturnValue(true);
    }

}
