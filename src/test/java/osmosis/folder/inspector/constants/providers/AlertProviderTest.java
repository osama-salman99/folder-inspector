package osmosis.folder.inspector.constants.providers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import osmosis.folder.inspector.constants.UserMessages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class AlertProviderTest {
    @Start
    private void start(Stage stage) {
    }

    @Test
    public void createsBackToMainMenuConfirmationAlert() throws Exception {
        Alert alert = WaitForAsyncUtils.asyncFx(AlertProvider::createBackToMainMenuAlert).get();

        assertEquals(Alert.AlertType.CONFIRMATION, alert.getAlertType());
        assertEquals(UserMessages.GO_BACK_MAIN_MENU, alert.getContentText());
        assertTrue(alert.getButtonTypes().contains(ButtonType.YES));
        assertTrue(alert.getButtonTypes().contains(ButtonType.CANCEL));
    }
}
