package osmosis.folder.inspector.container.state;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import osmosis.folder.inspector.container.Container;
import osmosis.folder.inspector.container.ContainerFactory;
import osmosis.folder.inspector.container.DirectoryContainer;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChildrenContainersWrapperTest {
    @Test
    public void getChildrenContainersInitializesAndReturnsList() {
        File file = TestUtils.getInstance().getFile("folder1/folder2");
        DirectoryContainer parent = ContainerFactory.createDirectoryContainer(file);
        ChildrenContainersWrapper wrapper = new ChildrenContainersWrapper(file, parent);

        List<Container> children = wrapper.getChildrenContainers();

        assertEquals(3, children.size());
    }

    @Test
    public void getChildrenContainersIsCachedAfterFirstCall() {
        File file = TestUtils.getInstance().getFile("folder1/folder2");
        DirectoryContainer parent = ContainerFactory.createDirectoryContainer(file);
        ChildrenContainersWrapper wrapper = new ChildrenContainersWrapper(file, parent);

        List<Container> first = wrapper.getChildrenContainers();
        List<Container> second = wrapper.getChildrenContainers();

        assertEquals(first.size(), second.size());
        for (int index = 0; index < first.size(); index++) {
            assertSame(first.get(index), second.get(index));
        }
    }

    @Test
    public void isEmptyReturnsTrueForEmptyDirectory(@TempDir Path tempDir) {
        File file = tempDir.toFile();
        DirectoryContainer parent = ContainerFactory.createDirectoryContainer(file);
        ChildrenContainersWrapper wrapper = new ChildrenContainersWrapper(file, parent);

        assertTrue(wrapper.isEmpty());
    }

    @Test
    public void isEmptyReturnsFalseForNonEmptyDirectory() {
        File file = TestUtils.getInstance().getFile("folder1/folder2");
        DirectoryContainer parent = ContainerFactory.createDirectoryContainer(file);
        ChildrenContainersWrapper wrapper = new ChildrenContainersWrapper(file, parent);

        assertFalse(wrapper.isEmpty());
    }

    @Test
    public void getChildrenContainersHandlesUnreadableFileGracefully(@TempDir Path tempDir) {
        File file = new File(tempDir.toFile(), "does-not-exist");
        DirectoryContainer parent = ContainerFactory.createDirectoryContainer(file);
        ChildrenContainersWrapper wrapper = new ChildrenContainersWrapper(file, parent);

        assertTrue(wrapper.getChildrenContainers().isEmpty());
    }
}
