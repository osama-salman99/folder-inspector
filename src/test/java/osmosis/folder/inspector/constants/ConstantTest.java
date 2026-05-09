package osmosis.folder.inspector.constants;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class ConstantTest {
    @Start
    private void start(Stage stage) {
    }

    @Test
    public void exposesScalarConstants() {
        assertEquals(":", Constant.COLON);
        assertEquals(20, Constant.ICON_SIZE);
        assertEquals(20, Constant.PROGRESS_INDICATOR_SIZE);
        assertEquals("Folder Inspector", Constant.FOLDER_INSPECTOR);
    }

    @Test
    public void emptyFilesArrayIsZeroLength() {
        assertEquals(0, Constant.EMPTY_FILES_ARRAY.length);
    }

    @Test
    public void fileSeparatorMatchesPlatformDefault() {
        assertNotNull(Constant.FILE_SEPARATOR);
        assertTrue(Constant.FILE_SEPARATOR.length() >= 1);
    }

    @Test
    public void accentColorIsConfigured() {
        assertNotNull(Constant.ACCENT_COLOR);
        assertEquals(190.0 / 255.0, Constant.ACCENT_COLOR.getRed(), 0.001);
    }
}
