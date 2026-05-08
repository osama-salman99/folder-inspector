package osmosis.folder.inspector.container;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import osmosis.folder.inspector.calculation.DirectorySizeCalculator;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContainerTest {
    @Test
    public void calculatesSizeOfFile() {
        File file = TestUtils.getInstance().getFile("folder1/folder4/f1-file1.txt");
        Container container = ContainerFactory.createContainer(file, null);
        long size = container.getSize();

        assertEquals(24, size);
    }

    @Test
    public void exposesAbsolutePathOfBackingFile() {
        File file = TestUtils.getInstance().getFile("folder1/folder4/f1-file1.txt");
        Container container = ContainerFactory.createContainer(file, null);

        assertEquals(file.getAbsolutePath(), container.getPath());
    }

    @Test
    public void exposesNameOfBackingFile() {
        File file = TestUtils.getInstance().getFile("folder1/folder4/f1-file1.txt");
        Container container = ContainerFactory.createContainer(file, null);

        assertEquals("f1-file1.txt", container.getName());
    }

    @Test
    public void hasParentContainerReturnsTrueWhenParentSet() {
        File parentFile = TestUtils.getInstance().getFile("folder1");
        File childFile = TestUtils.getInstance().getFile("folder1/folder4/f1-file1.txt");
        DirectoryContainer parent = ContainerFactory.createDirectoryContainer(parentFile);
        Container child = ContainerFactory.createContainer(childFile, parent);

        assertTrue(child.hasParentContainer());
        assertSame(parent, child.getParent());
    }

    @Test
    public void hasParentContainerReturnsFalseWhenParentNull() {
        File file = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);

        assertFalse(container.hasParentContainer());
        assertNull(container.getParent());
    }

    @Test
    public void fileContainerIsReadyOnConstruction() {
        File file = TestUtils.getInstance().getFile("folder1/folder4/f1-file1.txt");
        Container container = ContainerFactory.createContainer(file, null);

        assertTrue(container.isReady());
    }

    @Test
    public void directoryContainerIsNotReadyBeforeSizeCalculation() {
        File file = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);

        assertFalse(container.isReady());
    }

    @Test
    public void directoryContainerIsReadyAfterSizeIsSet() {
        File file = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);
        container.setSize(123L);

        assertTrue(container.isReady());
        assertEquals(123L, container.getSize());
    }

    @Test
    public void compareToOrdersContainersBySize() {
        File smallFile = TestUtils.getInstance().getFile("folder1/folder4/f1-file1.txt");
        File largeFile = TestUtils.getInstance().getFile("folder1/folder5/mario.jpg");
        Container small = ContainerFactory.createContainer(smallFile, null);
        Container large = ContainerFactory.createContainer(largeFile, null);

        assertTrue(small.compareTo(large) < 0);
        assertTrue(large.compareTo(small) > 0);
        assertEquals(0, small.compareTo(small));
    }

    @Test
    public void clearReadyMakesDirectoryContainerNotReady() throws InterruptedException {
        File file = TestUtils.getInstance().getFile("folder1/folder4");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);
        DirectorySizeCalculator.getInstance().calculate(container);
        Thread.sleep(200);
        assertTrue(container.isReady());

        container.clearReady();

        assertFalse(container.isReady());
    }

    @Test
    public void clearReadyOnDirectoryContainerResetsChildrenToFreshInstances() throws InterruptedException {
        File file = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);
        DirectorySizeCalculator.getInstance().calculate(container);
        Thread.sleep(200);
        List<Container> originalChildren = container.getChildrenContainers();
        assertFalse(originalChildren.isEmpty());

        container.clearReady();
        List<Container> refreshedChildren = container.getChildrenContainers();

        assertFalse(container.isReady());
        refreshedChildren.stream()
                .filter(child -> child instanceof DirectoryContainer)
                .forEach(subDirectory -> assertFalse(subDirectory.isReady()));
        assertFalse(refreshedChildren.stream().anyMatch(originalChildren::contains));
    }

    @Test
    public void clearReadyPicksUpNewlyCreatedChildren(@TempDir Path tempDir) throws IOException {
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(tempDir.toFile());
        assertTrue(container.getChildrenContainers().isEmpty());

        Files.createDirectory(tempDir.resolve("new-folder"));
        Files.writeString(tempDir.resolve("new-file.txt"), "hello");
        container.clearReady();

        List<Container> children = container.getChildrenContainers();
        assertEquals(2, children.size());
    }
}
