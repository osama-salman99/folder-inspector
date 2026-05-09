package osmosis.folder.inspector.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import osmosis.folder.inspector.constants.ResourcePaths;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class MainControllerTest {
    @Start
    private void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/osmosis/folder/inspector/" + ResourcePaths.MAIN_FXML)));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void mainFxmlLoadsAndExposesControls(FxRobot robot) {
        TextField pathInput = robot.lookup("#pathInputField").queryAs(TextField.class);
        Button inspectButton = robot.lookup("#inspectButton").queryAs(Button.class);

        assertNotNull(pathInput);
        assertNotNull(inspectButton);
        assertTrue(inspectButton.isDisabled(), "Inspect button starts disabled until input is non-blank");
    }

    @Test
    public void typingPathEnablesInspectButton(FxRobot robot) {
        TextField pathInput = robot.lookup("#pathInputField").queryAs(TextField.class);
        Button inspectButton = robot.lookup("#inspectButton").queryAs(Button.class);

        robot.interact(() -> {
            pathInput.setText("/some/path");
            pathInput.fireEvent(new javafx.scene.input.KeyEvent(
                    javafx.scene.input.KeyEvent.KEY_TYPED, "x", "x",
                    javafx.scene.input.KeyCode.X, false, false, false, false));
        });
        org.testfx.util.WaitForAsyncUtils.waitForFxEvents();

        assertTrue(!inspectButton.isDisabled(), "Inspect button enables when text is non-blank");
    }
}
