package osmosis.folder.inspector.formatter;

import java.text.DecimalFormat;
import java.util.List;

public class DigitalFormatter {
    private static final String DECIMAL_FORMAT_PATTERN = "#,###";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(DECIMAL_FORMAT_PATTERN);
    private static final List<String> DIGITAL_UNITS = List.of("B", "KB", "MB", "GB", "TB");

    public static String formatSize(long size) {
        return DECIMAL_FORMAT.format(size) + " (" + formatDigitalUnit(size) + ")";
    }

    private static String formatDigitalUnit(long size) {
        int unitIndex = 0;
        while (size / 1024 != 0 && unitIndex < DIGITAL_UNITS.size() - 1) {
            unitIndex++;
            size /= 1024;
        }
        return size + DIGITAL_UNITS.get(unitIndex);
    }
}
