package osmosis.folder.inspector.dimensions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static osmosis.folder.inspector.constants.DimensionRatios.MIN_HEIGHT_RATIO;
import static osmosis.folder.inspector.constants.DimensionRatios.MIN_WIDTH_RATIO;
import static osmosis.folder.inspector.constants.DimensionRatios.PREF_HEIGHT_RATIO;
import static osmosis.folder.inspector.constants.DimensionRatios.PREF_WIDTH_RATIO;

class DimensionsTest {
    private static final double DELTA = 1e-9;

    @Test
    public void appliesRatiosToScreenWidthAndHeight() {
        Dimensions dimensions = Dimensions.calculate(1920.0, 1080.0);

        assertEquals(1920.0 * PREF_WIDTH_RATIO, dimensions.getPrefWidth(), DELTA);
        assertEquals(1080.0 * PREF_HEIGHT_RATIO, dimensions.getPrefHeight(), DELTA);
        assertEquals(1920.0 * MIN_WIDTH_RATIO, dimensions.getMinWidth(), DELTA);
        assertEquals(1080.0 * MIN_HEIGHT_RATIO, dimensions.getMinHeight(), DELTA);
    }

    @Test
    public void preservesConstructorValues() {
        Dimensions dimensions = new Dimensions(1.0, 2.0, 3.0, 4.0);

        assertEquals(1.0, dimensions.getPrefWidth(), DELTA);
        assertEquals(2.0, dimensions.getPrefHeight(), DELTA);
        assertEquals(3.0, dimensions.getMinWidth(), DELTA);
        assertEquals(4.0, dimensions.getMinHeight(), DELTA);
    }

    @Test
    public void zeroScreenSizeProducesZeroDimensions() {
        Dimensions dimensions = Dimensions.calculate(0.0, 0.0);

        assertEquals(0.0, dimensions.getPrefWidth(), DELTA);
        assertEquals(0.0, dimensions.getPrefHeight(), DELTA);
        assertEquals(0.0, dimensions.getMinWidth(), DELTA);
        assertEquals(0.0, dimensions.getMinHeight(), DELTA);
    }
}
