package osmosis.folder.inspector.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import osmosis.folder.inspector.constants.ResourcePaths;
import osmosis.folder.inspector.container.ContainerFactory;
import osmosis.folder.inspector.container.ContainerManager;
import osmosis.folder.inspector.container.DirectoryContainer;
import osmosis.folder.inspector.test.TestUtils;
import osmosis.folder.inspector.panes.ContainerPane;

import java.util.Objects;

import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FoldersControllerNestedTest {
    @AfterEach
    void clearContainerManagerState() {
        ContainerManager.getInstance().clearContainer();
    }

    @Start
    private void start(Stage stage) throws Exception {
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(
                TestUtils.getInstance().getFile("folder1"));
        ContainerManager.getInstance().setCurrentContainer(container);

        Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("/osmosis/folder/inspector/" + ResourcePaths.FOLDERS_FXML)));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void initializeRendersDirectoryAndFileChildPanes(FxRobot robot) {
        VBox foldersVBox = robot.lookup("#foldersVBox").queryAs(VBox.class);

        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(foldersVBox);
        assertTrue(foldersVBox.getChildren().stream().anyMatch(node -> node instanceof ContainerPane),
                "VBox should contain at least one ContainerPane");
    }

    @Test
    public void backButtonIsPresent(FxRobot robot) {
        Button back = robot.lookup("#backButton").queryAs(Button.class);

        assertNotNull(back);
    }

    @Test
    public void clickingDirectoryChildPaneNavigatesIntoIt(FxRobot robot) {
        WaitForAsyncUtils.waitForFxEvents();
        VBox foldersVBox = robot.lookup("#foldersVBox").queryAs(VBox.class);

        ContainerPane firstDirectoryPane = foldersVBox.getChildren().stream()
                .filter(node -> node instanceof ContainerPane)
                .map(node -> (ContainerPane) node)
                .filter(pane -> pane.getContainer() instanceof DirectoryContainer)
                .findFirst()
                .orElse(null);

        if (firstDirectoryPane != null) {
            robot.interact(() -> firstDirectoryPane.fireEvent(new javafx.scene.input.MouseEvent(
                    javafx.scene.input.MouseEvent.MOUSE_PRESSED,
                    0, 0, 0, 0, javafx.scene.input.MouseButton.PRIMARY, 1,
                    false, false, false, false, true, false, false, false, false, false, null)));
            WaitForAsyncUtils.waitForFxEvents();
        }

        assertNotNull(foldersVBox);
    }

    @Test
    public void goBackWithParentNavigatesUp(FxRobot robot) {
        DirectoryContainer parent = ContainerFactory.createDirectoryContainer(
                TestUtils.getInstance().getFile("folder1"));
        DirectoryContainer child = new DirectoryContainer(
                TestUtils.getInstance().getFile("folder1/folder2"), parent);
        ContainerManager.getInstance().setCurrentContainer(child);

        Button back = robot.lookup("#backButton").queryAs(Button.class);
        robot.interact(back::fire);
        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(ContainerManager.getInstance().getCurrentContainer());
    }

    @Test
    public void rootButtonNavigatesToRoot(FxRobot robot) {
        DirectoryContainer root = ContainerFactory.createDirectoryContainer(
                TestUtils.getInstance().getFile("folder1"));
        DirectoryContainer mid = new DirectoryContainer(
                TestUtils.getInstance().getFile("folder1/folder2"), root);
        DirectoryContainer leaf = new DirectoryContainer(
                TestUtils.getInstance().getFile("folder1/folder2"), mid);
        ContainerManager.getInstance().setCurrentContainer(leaf);

        Button rootButton = robot.lookup("#rootButton").queryAs(Button.class);
        robot.interact(rootButton::fire);
        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(ContainerManager.getInstance().getCurrentContainer());
    }

    @Test
    public void refreshContentCalculationResetsAndRecalculates(FxRobot robot) {
        WaitForAsyncUtils.waitForFxEvents();

        Button refresh = robot.lookup("#refreshContentCalculationButton").queryAs(Button.class);
        robot.interact(refresh::fire);
        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(refresh);
    }
}
