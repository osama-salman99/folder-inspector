package osmosis.folder.inspector.panes;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import osmosis.folder.inspector.container.Container;
import osmosis.folder.inspector.container.ContainerFactory;
import osmosis.folder.inspector.container.DirectoryContainer;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(ApplicationExtension.class)
class ContainerPaneTest {
    @Start
    private void start(Stage stage) {
    }

    @Test
    public void buildsForFileContainer() {
        File file = TestUtils.getInstance().getFile("folder1/folder2/f2-file1.txt");
        Container container = ContainerFactory.createContainer(file, null);

        ContainerPane pane = new ContainerPane(container);

        assertSame(container, pane.getContainer());
        assertNotNull(pane.getCenter());
        assertNotNull(pane.getRight());
    }

    @Test
    public void buildsForDirectoryContainer() {
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(
                TestUtils.getInstance().getFile("folder1/folder2"));

        ContainerPane pane = new ContainerPane(container);

        assertSame(container, pane.getContainer());
        assertNotNull(pane.getCenter());
        assertNotNull(pane.getRight());
    }

    @Test
    public void backgroundsAlternateAcrossPaneSequence() {
        File file1 = TestUtils.getInstance().getFile("folder1/folder2/f2-file1.txt");
        File file2 = TestUtils.getInstance().getFile("folder1/folder2/f2-file2.txt");
        Container c1 = ContainerFactory.createContainer(file1, null);
        Container c2 = ContainerFactory.createContainer(file2, null);

        ContainerPane pane1 = new ContainerPane(c1);
        ContainerPane pane2 = new ContainerPane(c2);

        assertNotEquals(pane1.getBackground(), pane2.getBackground(),
                "Consecutive panes should not share the same background");
    }
}
