package net.nicolas.calcium.entity.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.item.DyeColor;

import java.util.function.IntFunction;

public class GiantClamVariant {

    public enum BaseColor {

        CYAN(0, "cyan", 55807),
        GREEN(1, "green", 7588118),
        ORANGE(2, "orange", 16684863),
        PURPLE(3, "purple", 9580031);

        private static final IntFunction<BaseColor> BY_ID = ByIdMap.sparse(BaseColor::getId, values(), CYAN);
        private final int id;
        private final String name;
        private final int textColor;

        BaseColor(int id, String name, int textColor) {
            this.id = id;
            this.name = name;
            this.textColor = textColor;
        }

        public int getId() {
            return this.id;
        }
        public int getTextColor() {
            return this.textColor;
        }
        public String colorName() {
            return this.name;
        }
        public static BaseColor byId(int id) {
            return BY_ID.apply(id);
        }

        public Component colorDisplayName() {
            return Component.translatable("entity.calcium.giant_clam.color." + this.name);
        }

    }

    public enum Pattern {

        NO_PATTERN(0, "no_pattern"),
        STRIPED(1, "striped"),
        DOTTED(2, "dotted"),
        TILED(3, "tiled"),
        RIDGED(4, "ridged");

        private static final IntFunction<Pattern> BY_ID = ByIdMap.sparse(Pattern::getId, values(), NO_PATTERN);
        private final int id;
        private final String name;

        Pattern(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }
        public String patternName() {
            return this.name;
        }
        public static Pattern byId(int id) {
            return BY_ID.apply(id);
        }

        public Component patternDisplayName() {
            return Component.translatable("entity.calcium.giant_clam.pattern." + this.name);
        }

    }

    public record Variant(BaseColor baseColor, Pattern pattern, DyeColor dyeColor) {

        public int getPackedId() {
            return GiantClam.packVariant(this.baseColor, this.pattern, this.dyeColor);
        }

    }

}