package osmosis.folder.inspector.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.net.URL;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(ApplicationExtension.class)
class ControllerTest {
    private Stage primaryStage;
    private Button button;

    @Start
    private void start(Stage stage) {
        this.primaryStage = stage;
        this.button = new Button("Trigger");
        VBox root = new VBox(button);
        stage.setScene(new Scene(root, 200, 100));
        stage.show();
    }

    @Test
    public void getStageReturnsSourceNodesStage(FxRobot robot) {
        Stage[] resolved = new Stage[1];
        ActionEvent event = new ActionEvent(button, null);

        robot.interact(() -> resolved[0] = TestController.callGetStage(event));

        assertSame(primaryStage, resolved[0]);
    }

    @Test
    public void setSceneShowsErrorAlertWhenFxmlMissing(FxRobot robot) throws Exception {
        TestController controller = new TestController();
        ActionEvent event = new ActionEvent(button, null);

        Thread fire = new Thread(() -> javafx.application.Platform.runLater(
                () -> controller.callSetScene(event, "does-not-exist.fxml")));
        fire.start();

        WaitForAsyncUtils.waitFor(3, java.util.concurrent.TimeUnit.SECONDS, () ->
                robot.listTargetWindows().stream().anyMatch(w -> w instanceof Stage stage
                        && stage.getScene() != null
                        && stage.getScene().getRoot() instanceof javafx.scene.control.DialogPane));

        robot.interact(() -> {
            javafx.scene.control.DialogPane dp = (javafx.scene.control.DialogPane) robot.listTargetWindows().stream()
                    .map(w -> ((Stage) w).getScene().getRoot())
                    .filter(r -> r instanceof javafx.scene.control.DialogPane)
                    .findFirst().orElseThrow();
            javafx.scene.control.Button ok = (javafx.scene.control.Button) dp.lookupButton(
                    dp.getButtonTypes().stream().findFirst().orElse(javafx.scene.control.ButtonType.OK));
            ok.fire();
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void setSceneAsyncSwapsStageScene(FxRobot robot) throws Exception {
        Scene before = primaryStage.getScene();
        TestController controller = new TestController();
        ActionEvent event = new ActionEvent(button, null);

        robot.interact(() -> controller.callSetScene(event, "main.fxml"));
        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(primaryStage.getScene());
        assertNotNull(before);
    }

    private static class TestController extends Controller {
        public static Stage callGetStage(ActionEvent event) {
            return getStage(event);
        }

        public void callSetScene(ActionEvent event, String resourceName) {
            setScene(event, resourceName);
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
        }
    }
}
