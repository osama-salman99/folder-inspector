package osmosis.folder.inspector.constants;

import javafx.scene.paint.Color;

import java.io.File;
import java.nio.file.FileSystems;

public class Constant {
    public static final String COLON = ":";
    public static final int ICON_SIZE = 20;
    public static final int PROGRESS_INDICATOR_SIZE = 20;
    public static final File[] EMPTY_FILES_ARRAY = new File[0];
    public static final String FOLDER_INSPECTOR = "Folder Inspector";
    public static final String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();
    public static final Color ACCENT_COLOR = Color.rgb(190, 211, 231);

}
