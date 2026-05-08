package osmosis.folder.inspector.container;

import org.junit.jupiter.api.BeforeAll;
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
import static org.junit.jupiter.params.provider.Arguments.argumentSet;

class ContainerFactoryTest {
    @TempDir
    static Path symlinkTempDir;

    private static File symlinkFile;

    @BeforeAll
    static void createSymlink() throws Exception {
        Path target = Files.createFile(symlinkTempDir.resolve("target.txt"));
        symlinkFile = Files.createSymbolicLink(symlinkTempDir.resolve("link.txt"), target).toFile();
    }

    @Test
    public void createsDirectoryContainerWithoutParent() {
        File file = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);

        assertEquals(file.getAbsolutePath(), container.getPath());
        assertNull(container.getParent());
    }

    public static Stream<Arguments> containerInputProvider() {
        return Stream.of(
                argumentSet("directory input -> DirectoryContainer",
                        TestUtils.getInstance().getFile("folder1/folder2"), DirectoryContainer.class),
                argumentSet("regular file input -> FileContainer",
                        TestUtils.getInstance().getFile("folder1/folder4/f1-file1.txt"), FileContainer.class),
                argumentSet("symlink input -> SymbolicLinkContainer",
                        symlinkFile, SymbolicLinkContainer.class)
        );
    }

    @ParameterizedTest
    @MethodSource("containerInputProvider")
    public void createsAppropriateContainerForInput(File file, Class<? extends Container> expectedType) {
        Container container = ContainerFactory.createContainer(file, null);

        assertInstanceOf(expectedType, container);
    }
}
