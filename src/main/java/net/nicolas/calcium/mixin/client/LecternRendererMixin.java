package net.nicolas.calcium.mixin.client;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.LecternRenderer;
import net.minecraft.client.resources.model.sprite.SpriteId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LecternRenderer.class)
public class LecternRendererMixin {

    @Redirect(method = "submit", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/blockentity/EnchantTableRenderer;BOOK_TEXTURE:Lnet/minecraft/client/resources/model/sprite/SpriteId;"))
    private SpriteId calcium$useLecternBookTexture() {
        return Sheets.BLOCK_ENTITIES_MAPPER.defaultNamespaceApply("lectern/lectern_book");
    }

}