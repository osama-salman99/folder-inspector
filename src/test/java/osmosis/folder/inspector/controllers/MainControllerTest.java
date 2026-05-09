package osmosis.folder.inspector.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
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

    @org.junit.jupiter.api.AfterEach
    void clearContainerManagerState() {
        osmosis.folder.inspector.container.ContainerManager.getInstance().clearContainer();
    }

    @Test
    public void inspectOnValidPathNavigatesToFoldersScene(FxRobot robot) throws Exception {
        Button inspectButton = robot.lookup("#inspectButton").queryAs(Button.class);
        TextField pathInput = robot.lookup("#pathInputField").queryAs(TextField.class);
        java.io.File validFolder = osmosis.folder.inspector.test.TestUtils.getInstance().getFile("folder1/folder2");
        robot.interact(() -> {
            pathInput.setText(validFolder.getAbsolutePath());
            inspectButton.setDisable(false);
        });

        robot.interact(inspectButton::fire);
        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(osmosis.folder.inspector.container.ContainerManager.getInstance().getCurrentContainer());
    }

    @Test
    public void inspectOnBlankPathShowsErrorAlert(FxRobot robot) throws Exception {
        Button inspectButton = robot.lookup("#inspectButton").queryAs(Button.class);
        TextField pathInput = robot.lookup("#pathInputField").queryAs(TextField.class);
        robot.interact(() -> {
            pathInput.setText("");
            inspectButton.setDisable(false);
        });

        Thread fire = new Thread(() -> javafx.application.Platform.runLater(() -> inspectButton.fire()));
        fire.start();

        WaitForAsyncUtils.waitFor(3, java.util.concurrent.TimeUnit.SECONDS, () ->
                robot.listTargetWindows().stream().anyMatch(w -> w instanceof Stage stage
                        && stage.getScene() != null
                        && stage.getScene().getRoot() instanceof DialogPane));

        robot.interact(() -> {
            DialogPane dialogPane = (DialogPane) robot.listTargetWindows().stream()
                    .map(w -> ((Stage) w).getScene().getRoot())
                    .filter(r -> r instanceof DialogPane)
                    .findFirst().orElseThrow();
            Button ok = (Button) dialogPane.lookupButton(
                    dialogPane.getButtonTypes().stream().findFirst().orElse(ButtonType.OK));
            ok.fire();
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(pathInput);
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
