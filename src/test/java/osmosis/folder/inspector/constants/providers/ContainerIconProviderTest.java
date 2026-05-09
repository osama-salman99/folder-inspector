package osmosis.folder.inspector.constants.providers;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import osmosis.folder.inspector.container.Container;
import osmosis.folder.inspector.container.ContainerFactory;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
class ContainerIconProviderTest {
    @Start
    private void start(Stage stage) {
    }

    @Test
    public void returnsFolderIconForDirectoryContainer() {
        Container container = ContainerFactory.createDirectoryContainer(
                TestUtils.getInstance().getFile("folder1"));

        Image icon = ContainerIconProvider.getIcon(container);

        assertNotNull(icon);
    }

    @Test
    public void returnsFileIconForFileContainer() {
        File file = TestUtils.getInstance().getFile("folder1/folder2/f2-file1.txt");
        Container container = ContainerFactory.createContainer(file, null);

        Image icon = ContainerIconProvider.getIcon(container);

        assertNotNull(icon);
    }

    @Test
    public void returnsLinkIconForSymbolicLinkContainer(@TempDir Path tempDir) throws Exception {
        Path target = Files.createFile(tempDir.resolve("target.txt"));
        Path link = Files.createSymbolicLink(tempDir.resolve("link.txt"), target);
        Container container = ContainerFactory.createContainer(link.toFile(), null);

        Image icon = ContainerIconProvider.getIcon(container);

        assertNotNull(icon);
    }
}
