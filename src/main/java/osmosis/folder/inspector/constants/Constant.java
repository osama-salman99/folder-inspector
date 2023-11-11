package osmosis.folder.inspector.constants;

import javafx.scene.paint.Color;

import java.nio.file.FileSystems;
import java.text.DecimalFormat;

public class Constant {
    public static final String COLON = ":";
    public static final int ICON_SIZE = 20;
    public static final int PROGRESS_INDICATOR_SIZE = 20;
    public static final String DECIMAL_FORMAT_PATTERN = "#,###";
    public static final String FOLDER_INSPECTOR = "Folder Inspector";
    public static final String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();
    public static final Color ACCENT_COLOR = Color.rgb(190, 211, 231);
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(DECIMAL_FORMAT_PATTERN);
}
