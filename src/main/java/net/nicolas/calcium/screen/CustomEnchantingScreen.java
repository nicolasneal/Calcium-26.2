package net.nicolas.calcium.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CustomEnchantingScreen extends AbstractRecipeBookScreen<CustomEnchantingScreenHandler> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("calcium", "textures/gui/container/enchanting_table.png");
    private static final Identifier ERROR = Identifier.fromNamespaceAndPath("calcium", "textures/gui/sprites/container/enchanting_table/error.png");
    private static final Identifier[] LEVEL_SPRITES = new Identifier[]{
        Identifier.fromNamespaceAndPath("calcium", "textures/gui/sprites/container/enchanting_table/level_1.png"),
        Identifier.fromNamespaceAndPath("calcium", "textures/gui/sprites/container/enchanting_table/level_2.png"),
        Identifier.fromNamespaceAndPath("calcium", "textures/gui/sprites/container/enchanting_table/level_3.png")
    };

    private final EnchantingRecipeBookComponent recipeBook;

    public CustomEnchantingScreen(CustomEnchantingScreenHandler handler, Inventory inventory, Component title) {
        this(handler, new EnchantingRecipeBookComponent(handler), inventory, title);
    }

    private CustomEnchantingScreen(CustomEnchantingScreenHandler handler, EnchantingRecipeBookComponent recipeBook, Inventory inventory, Component title) {
        super(handler, recipeBook, inventory, title);
        this.recipeBook = recipeBook;
    }

    @Override protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + 5, this.height / 2 - 49);
    }

    @Override protected void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractTooltip(graphics, mouseX, mouseY);

        Slot slot = this.getSlotUnderMouse();
        if (slot instanceof CustomSlot customSlot && !this.recipeBook.hasGhostItem(slot)) {
            Component tooltip = customSlot.getTooltip();
            if (tooltip != null) {
                graphics.setTooltipForNextFrame(this.font, tooltip, mouseX, mouseY);
            }
        }

    }

    private Slot getSlotUnderMouse() {

        double mouseX = this.minecraft.mouseHandler.xpos() * this.minecraft.getWindow().getGuiScaledWidth() / this.minecraft.getWindow().getScreenWidth();
        double mouseY = this.minecraft.mouseHandler.ypos() * this.minecraft.getWindow().getGuiScaledHeight() / this.minecraft.getWindow().getScreenHeight();
        int guiLeft = this.leftPos;
        int guiTop = this.topPos;
        int relativeX = (int) mouseX - guiLeft;
        int relativeY = (int) mouseY - guiTop;

        for (Slot slot : this.menu.slots) {
            int centerX = slot.x + 8;
            int centerY = slot.y + 8;
            if (relativeX >= centerX - 18 / 2 && relativeX < centerX + 18 / 2 &&
                    relativeY >= centerY - 18 / 2 && relativeY < centerY + 18 / 2) {
                return slot;
            }
        }

        return null;

    }

    @Override public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);

        int x = this.leftPos;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        boolean mainInputOccupied = this.menu.slots.get(37).hasItem();

        int ingredientCount = 0;
        for (int i = 38; i <= 46; i++) {
            if (this.menu.slots.get(i).hasItem()) {
                ingredientCount++;
            }
        }

        if (mainInputOccupied && ingredientCount > 0) {
            int spriteIndex = (ingredientCount - 1) / 3;
            Identifier sprite = LEVEL_SPRITES[spriteIndex];
            graphics.blit(RenderPipelines.GUI_TEXTURED, sprite, x + 73, y + 65, 0, 0, 12, 10, 12, 10);
        }

        int cost = this.menu.getLevelCost();
        if (cost > 0) {

            assert this.minecraft.player != null;
            boolean enoughLevels = this.minecraft.player.getAbilities().instabuild || this.minecraft.player.experienceLevel >= cost;
            ItemStack lapisStack = this.menu.getSlot(36).getItem();
            boolean enoughLapis = !lapisStack.isEmpty() && lapisStack.is(Items.LAPIS_LAZULI) && lapisStack.getCount() >= ingredientCount;

            if (!enoughLevels || !enoughLapis) {
                graphics.blit(RenderPipelines.GUI_TEXTURED, ERROR, x + 126, y + 37, 0, 0, 11, 12, 11, 12);
            }

        }

    }

    @Override protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - font.width(title)) / 2;
    }

}