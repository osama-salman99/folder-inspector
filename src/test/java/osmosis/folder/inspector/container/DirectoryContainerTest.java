package osmosis.folder.inspector.container;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DirectoryContainerTest {
    @Test
    public void getChildrenContainersListsEntries() {
        File file = TestUtils.getInstance().getFile("folder1/folder2");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);

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
        AtomicInteger callCount = new AtomicInteger();
        container.setChildContainerReadyListener(callCount::incrementAndGet);

        container.invokeListener();

        assertEquals(1, callCount.get());
    }

    @Test
    public void invokeListenerBubblesUpToParentWhenChildHasNoListener() {
        File parentFile = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer parent = ContainerFactory.createDirectoryContainer(parentFile);
        DirectoryContainer child = new DirectoryContainer(TestUtils.getInstance().getFile("folder1/folder2"), parent);
        AtomicInteger parentCallCount = new AtomicInteger();
        parent.setChildContainerReadyListener(parentCallCount::incrementAndGet);

        child.invokeListener();

        assertEquals(1, parentCallCount.get());
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
        AtomicInteger callCount = new AtomicInteger();
        container.setChildContainerReadyListener(callCount::incrementAndGet);

        container.clearChildContainerReadyListener();
        container.invokeListener();

        assertEquals(0, callCount.get());
    }

    @Test
    public void setSizeUpdatesContainerSize() {
        File file = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);

        container.setSize(987L);

        assertEquals(987L, container.getSize());
    }
}
