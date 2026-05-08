package osmosis.folder.inspector.container;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DirectoryContainerTest {
    @Test
    public void getChildrenContainersListsEntries() {
        File file = TestUtils.getInstance().getFile("folder1/folder2");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);

        assertEquals(3, container.getChildrenContainers().size());

        List<String> names = container.getChildrenContainers().stream()
                .map(Container::getName)
                .sorted()
                .toList();

        assertEquals(List.of("f2-file1.txt", "f2-file2.txt", "f2-file3.zip"), names);
    }

    @Test
    public void isEmptyReturnsFalseForPopulatedDirectory() {
        File file = TestUtils.getInstance().getFile("folder1/folder2");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);

        assertFalse(container.isEmpty());
    }

    @Test
    public void isEmptyReturnsTrueForEmptyDirectory(@TempDir Path tempDir) {
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(tempDir.toFile());

        assertTrue(container.isEmpty());
    }

    @Test
    public void invokeListenerCallsRegisteredListener() {
        File file = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);
        ChildContainerReadyListener listener = mock(ChildContainerReadyListener.class);
        container.setChildContainerReadyListener(listener);

        container.invokeListener();

        verify(listener, times(1)).onContainerReady();
    }

    @Test
    public void invokeListenerBubblesUpToParentWhenChildHasNoListener() {
        File parentFile = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer parent = ContainerFactory.createDirectoryContainer(parentFile);
        DirectoryContainer child = new DirectoryContainer(TestUtils.getInstance().getFile("folder1/folder2"), parent);
        ChildContainerReadyListener parentListener = mock(ChildContainerReadyListener.class);
        parent.setChildContainerReadyListener(parentListener);

        child.invokeListener();

        verify(parentListener, times(1)).onContainerReady();
    }

    @Test
    public void invokeListenerIsNoOpWhenNeitherSelfNorParentHasListener() {
        File parentFile = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer parent = ContainerFactory.createDirectoryContainer(parentFile);
        DirectoryContainer child = new DirectoryContainer(TestUtils.getInstance().getFile("folder1/folder2"), parent);

        child.invokeListener();
    }

    @Test
    public void invokeListenerIsNoOpWhenRootHasNoListener() {
        File file = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);

        container.invokeListener();
    }

    @Test
    public void clearChildContainerReadyListenerRemovesListener() {
        File file = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);
        ChildContainerReadyListener listener = mock(ChildContainerReadyListener.class);
        container.setChildContainerReadyListener(listener);

        container.clearChildContainerReadyListener();
        container.invokeListener();

        verify(listener, never()).onContainerReady();
    }

    @Test
    public void setSizeUpdatesContainerSize() {
        File file = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);

        container.setSize(987L);

        assertEquals(987L, container.getSize());
    }
}
