package osmosis.folder.inspector.constants.providers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import osmosis.folder.inspector.constants.UserMessages;

public class AlertProvider {
    public static Alert createBackToMainMenuAlert() {
        return new Alert(
                Alert.AlertType.CONFIRMATION,
                UserMessages.GO_BACK_MAIN_MENU,
                ButtonType.YES,
                ButtonType.CANCEL
        );
    }
}
