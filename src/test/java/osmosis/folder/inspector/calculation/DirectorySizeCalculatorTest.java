package osmosis.folder.inspector.calculation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import osmosis.folder.inspector.container.ContainerFactory;
import osmosis.folder.inspector.container.DirectoryContainer;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
