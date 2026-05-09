package osmosis.folder.inspector.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
class FoldersControllerTest {
    private File rootDir;

    @Start
    private void start(Stage stage) throws Exception {
        rootDir = TestUtils.getInstance().getFile("folder1/folder2");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(rootDir);
        ContainerManager.getInstance().setCurrentContainer(container);

        Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("/osmosis/folder/inspector/" + ResourcePaths.FOLDERS_FXML)));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void initializeShowsCurrentPathInAddressBar(FxRobot robot) {
        TextField addressBar = robot.lookup("#addressBar").queryAs(TextField.class);

        assertNotNull(addressBar);
        assertEquals(rootDir.getAbsolutePath(), addressBar.getText());
    }

    @Test
    public void initializePopulatesFoldersBoxWithChildren(FxRobot robot) throws Exception {
        VBox foldersVBox = robot.lookup("#foldersVBox").queryAs(VBox.class);

        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(foldersVBox);
    }

    @Test
    public void copyAddressToClipboardCopiesPath(FxRobot robot) {
        robot.clickOn("#copyAddressToClipboard");
        WaitForAsyncUtils.waitForFxEvents();

        TextField addressBar = robot.lookup("#addressBar").queryAs(TextField.class);
        assertEquals(rootDir.getAbsolutePath(), addressBar.getText());
    }

    @Test
    public void folderIsEmptyTextHidesForPopulatedDirectory(FxRobot robot) {
        Text emptyText = robot.lookup("#folderIsEmptyText").queryAs(Text.class);

        assertNotNull(emptyText);
        assertEquals(false, emptyText.isVisible());
    }
}
