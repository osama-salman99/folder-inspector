package osmosis.folder.inspector.dimensions;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import static osmosis.folder.inspector.constants.DimensionRatios.MIN_HEIGHT_RATIO;
import static osmosis.folder.inspector.constants.DimensionRatios.MIN_WIDTH_RATIO;
import static osmosis.folder.inspector.constants.DimensionRatios.PREF_HEIGHT_RATIO;
import static osmosis.folder.inspector.constants.DimensionRatios.PREF_WIDTH_RATIO;

public class Dimensions {
    private final double prefWidth;
    private final double prefHeight;
    private final double minWidth;
    private final double minHeight;

    public Dimensions(double prefWidth, double prefHeight, double minWidth, double minHeight) {
        this.prefWidth = prefWidth;
        this.prefHeight = prefHeight;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
    }

    public double getPrefWidth() {
        return prefWidth;
    }

    public double getPrefHeight() {
        return prefHeight;
    }

    public double getMinWidth() {
        return minWidth;
    }

    public double getMinHeight() {
        return minHeight;
    }

    public static Dimensions calculate() {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        return new Builder()
                .prefWidth(bounds.getWidth() * PREF_WIDTH_RATIO)
                .prefHeight(bounds.getHeight() * PREF_HEIGHT_RATIO)
                .minWidth(bounds.getWidth() * MIN_WIDTH_RATIO)
                .minHeight(bounds.getHeight() * MIN_HEIGHT_RATIO)
                .build();
    }

    private static class Builder {
        private double prefWidth;
        private double prefHeight;
        private double minWidth;
        private double minHeight;

        public Builder prefWidth(double prefWidth) {
            this.prefWidth = prefWidth;
            return this;
        }

        public Builder prefHeight(double prefHeight) {
            this.prefHeight = prefHeight;
            return this;
        }

        public Builder minWidth(double minWidth) {
            this.minWidth = minWidth;
            return this;
        }

        public Builder minHeight(double minHeight) {
            this.minHeight = minHeight;
            return this;
        }

        public Dimensions build() {
            return new Dimensions(prefWidth, prefHeight, minWidth, minHeight);
        }
    }
}
