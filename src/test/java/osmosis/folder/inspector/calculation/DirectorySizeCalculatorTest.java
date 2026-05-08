package osmosis.folder.inspector.calculation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import osmosis.folder.inspector.container.ChildContainerReadyListener;
import osmosis.folder.inspector.container.ContainerFactory;
import osmosis.folder.inspector.container.DirectoryContainer;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DirectorySizeCalculatorTest {
    @ParameterizedTest
    @MethodSource("folderPathSizeProvider")
    public void calculatesSizeOfDirectory(String path, Long size) throws InterruptedException {
        File file = TestUtils.getInstance().getFile(path);
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);
        DirectorySizeCalculator.getInstance().calculate(container);
        Thread.sleep(200);

        assertEquals(size, container.getSize());
    }

    public static Stream<Arguments> folderPathSizeProvider() {
        return Stream.of(
                Arguments.of("folder1/folder2", 2060L),
                Arguments.of("folder1/folder4", 24L),
                Arguments.of("folder1/folder5", 47661L),
                Arguments.of("folder1", 49769L)
        );
    }

    @Test
    public void calculatesSizeOfEmptyDirectoryAsZero(@TempDir Path tempDir) throws InterruptedException {
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(tempDir.toFile());

        DirectorySizeCalculator.getInstance().calculate(container);
        Thread.sleep(200);

        assertEquals(0L, container.getSize());
        assertTrue(container.isReady());
    }

    @Test
    public void calculationInvokesContainerReadyListener(@TempDir Path tempDir) throws Exception {
        Files.createFile(tempDir.resolve("a.txt"));
        Files.createFile(tempDir.resolve("b.txt"));
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(tempDir.toFile());
        ChildContainerReadyListener listener = mock(ChildContainerReadyListener.class);
        container.setChildContainerReadyListener(listener);

        DirectorySizeCalculator.getInstance().calculate(container);
        Thread.sleep(300);

        verify(listener, times(1)).onContainerReady();
    }

    @Test
    public void getInstanceReturnsSingleton() {
        assertSame(DirectorySizeCalculator.getInstance(), DirectorySizeCalculator.getInstance());
    }
}
