package net.nicolas.calcium.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class CustomBeaconScreen extends AbstractContainerScreen<CustomBeaconScreenHandler> {

    private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/gui/container/beacon.png");

    static final Identifier BUTTON_TEXTURE = Identifier.withDefaultNamespace("container/beacon/button");
    static final Identifier BUTTON_HIGHLIGHTED_TEXTURE = Identifier.withDefaultNamespace("container/beacon/button_highlighted");
    static final Identifier BUTTON_SELECTED_TEXTURE = Identifier.withDefaultNamespace("container/beacon/button_selected");
    static final Identifier BUTTON_DISABLED_TEXTURE = Identifier.withDefaultNamespace("container/beacon/button_disabled");

    private final List<BeaconButtonWidget> buttons = new ArrayList<>();

    public CustomBeaconScreen(CustomBeaconScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title, 176, 188);
    }

    static Identifier getMobEffectSprite(Holder<MobEffect> effect) {
        return effect.unwrapKey().map(ResourceKey::identifier).map(id -> id.withPrefix("mob_effect/")).orElseGet(MissingTextureAtlasSprite::getLocation);
    }

    @Override
    protected void init() {
        super.init();
        this.buttons.clear();

        int index = 0;

        for (int i = 0; i <= 2; ++i) {
            int count = BeaconBlockEntity.BEACON_EFFECTS.get(i).size();
            int width = count * 22 + (count - 1) * 2;
            for (int j = 0; j < count; ++j) {
                Holder<MobEffect> effect = BeaconBlockEntity.BEACON_EFFECTS.get(i).get(j);
                EffectButtonWidget btn = new EffectButtonWidget(0, 0, effect, true, i);
                setWidgetPosition(btn, index);
                this.addRenderableWidget(btn);
                this.buttons.add(btn);
                index++;
            }
        }

        int count = BeaconBlockEntity.BEACON_EFFECTS.get(3).size() + 1;

        for (int j = 0; j < count - 1; ++j) {
            Holder<MobEffect> effect = BeaconBlockEntity.BEACON_EFFECTS.get(3).get(j);
            EffectButtonWidget btn = new EffectButtonWidget(0, 0, effect, false, 3);
            setWidgetPosition(btn, index);
            this.addRenderableWidget(btn);
            this.buttons.add(btn);
            index++;
        }

        Holder<MobEffect> dummy = BeaconBlockEntity.BEACON_EFFECTS.get(0).getFirst();
        UpgradeButtonWidget upgradeBtn = new UpgradeButtonWidget(0, 0, dummy);
        upgradeBtn.visible = false;
        setWidgetPosition(upgradeBtn, index);
        this.addRenderableWidget(upgradeBtn);
        this.buttons.add(upgradeBtn);
    }

    private void setWidgetPosition(AbstractButton widget, int i) {
        if (i == 0) {
            widget.setX(this.leftPos + 41);
            widget.setY(this.topPos + 16);
        } else if (i == 1) {
            widget.setX(this.leftPos + 67);
            widget.setY(this.topPos + 16);
        } else if (i == 2) {
            widget.setX(this.leftPos + 41);
            widget.setY(this.topPos + 42);
        } else if (i == 3) {
            widget.setX(this.leftPos + 67);
            widget.setY(this.topPos + 42);
        } else if (i == 4) {
            widget.setX(this.leftPos + 54);
            widget.setY(this.topPos + 68);
        } else if (i == 5) {
            widget.setX(this.leftPos + 107);
            widget.setY(this.topPos + 42);
        } else if (i == 6) {
            widget.setX(this.leftPos + 133);
            widget.setY(this.topPos + 42);
        }
    }

    @Override
    public void containerTick() {
        super.containerTick();
        int properties = this.menu.getProperties();
        this.buttons.forEach(button -> button.tick(properties));
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, i, j, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
    }

    @Environment(EnvType.CLIENT)
    interface BeaconButtonWidget {
        void tick(int level);
    }

    @Environment(EnvType.CLIENT)
    abstract static class BaseButtonWidget extends AbstractButton implements BeaconButtonWidget {
        protected boolean disabled;

        protected BaseButtonWidget(int x, int y) {
            super(x, y, 22, 22, CommonComponents.EMPTY);
        }

        protected BaseButtonWidget(int x, int y, Component message) {
            super(x, y, 22, 22, message);
        }

        @Override protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
            Identifier identifier;
            if (!this.active) {
                identifier = BUTTON_DISABLED_TEXTURE;
            }
            else if (this.disabled) {
                identifier = BUTTON_SELECTED_TEXTURE;
            }
            else if (this.isHovered) {
                identifier = BUTTON_HIGHLIGHTED_TEXTURE;
            }
            else {
                identifier = BUTTON_TEXTURE;
            }
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, identifier, this.getX(), this.getY(), this.width, this.height);
            this.extractExtra(graphics);
        }

        public abstract void onPress();
        protected abstract void extractExtra(GuiGraphicsExtractor graphics);

        @Override public void updateWidgetNarration(NarrationElementOutput builder) {
            this.defaultButtonNarrationText(builder);
        }

    }

    @Environment(EnvType.CLIENT) class EffectButtonWidget extends BaseButtonWidget {
        private final boolean primary;
        protected final int level;
        private Holder<MobEffect> effect;
        private Identifier sprite;

        public EffectButtonWidget(int x, int y, Holder<MobEffect> effect, boolean primary, int level) {
            super(x, y);
            this.primary = primary;
            this.level = level;
            this.init(effect);
        }

        protected void init(Holder<MobEffect> effect) {
            this.effect = effect;
            this.sprite = getMobEffectSprite(effect);
            this.setTooltip(Tooltip.create(Component.translatable(effect.value().getDescriptionId())));
        }

        @Override public void onPress() {
            if (!this.disabled) {
                if (this.primary) {
                    Objects.requireNonNull(CustomBeaconScreen.this.minecraft.getConnection()).send(
                            new ServerboundSetBeaconPacket(Optional.of(this.effect), Optional.empty())
                    );
                } else {
                    Objects.requireNonNull(CustomBeaconScreen.this.minecraft.getConnection()).send(
                            new ServerboundSetBeaconPacket(Optional.ofNullable(CustomBeaconScreen.this.menu.getPrimaryEffect()), Optional.of(this.effect))
                    );
                }
            }
        }

        @Override public void onPress(InputWithModifiers input) {
            this.onPress();
        }

        @Override protected void extractExtra(GuiGraphicsExtractor graphics) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.sprite, this.getX() + 2, this.getY() + 2, 18, 18);
        }

        @Override public void tick(int level) {
            this.active = this.level < level;
            this.disabled = this.effect.equals(this.primary ? CustomBeaconScreen.this.menu.getPrimaryEffect() : CustomBeaconScreen.this.menu.getSecondaryEffect());
        }

    }

    @Environment(EnvType.CLIENT) class UpgradeButtonWidget extends EffectButtonWidget {
        public UpgradeButtonWidget(int x, int y, Holder<MobEffect> effect) {
            super(x, y, effect, false, 3);
        }

        @Override public void tick(int level) {
            Holder<MobEffect> primary = CustomBeaconScreen.this.menu.getPrimaryEffect();
            if (primary != null) {
                this.visible = true;
                this.init(primary);
                super.tick(level);
            } else {
                this.visible = false;
            }
        }

        @Override protected void init(Holder<MobEffect> effect) {
            super.init(effect);
            this.setTooltip(Tooltip.create(Component.translatable(effect.value().getDescriptionId()).append(" II")));
        }

    }

}
