package osmosis.folder.inspector.container;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

class ContainerFactoryTest {
    @Test
    public void createsDirectoryContainerWithoutParent() {
        File file = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);

        assertEquals(file.getAbsolutePath(), container.getPath());
        assertNull(container.getParent());
    }

    @ParameterizedTest
    @MethodSource("containerInputProvider")
    public void createsAppropriateContainerForInput(File file, Class<? extends Container> expectedType) {
        Container container = ContainerFactory.createContainer(file, null);

        assertInstanceOf(expectedType, container);
    }

    public static Stream<Arguments> containerInputProvider() {
        return Stream.of(
                Arguments.of(TestUtils.getInstance().getFile("folder1/folder2"), DirectoryContainer.class),
                Arguments.of(TestUtils.getInstance().getFile("folder1/folder4/f1-file1.txt"), FileContainer.class)
        );
    }

    @Test
    public void createsSymbolicLinkContainerForSymlinkInput(@TempDir Path tempDir) throws Exception {
        Path target = Files.createFile(tempDir.resolve("target.txt"));
        Path link = Files.createSymbolicLink(tempDir.resolve("link.txt"), target);

        Container container = ContainerFactory.createContainer(link.toFile(), null);

        assertInstanceOf(SymbolicLinkContainer.class, container);
    }
}
