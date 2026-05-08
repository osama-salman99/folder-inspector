package osmosis.folder.inspector.container;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SymbolicLinkContainerTest {
    @Test
    public void symbolicLinkContainerHasZeroSize(@TempDir Path tempDir) throws Exception {
        Path target = Files.createFile(tempDir.resolve("target.txt"));
        Path link = Files.createSymbolicLink(tempDir.resolve("link.txt"), target);

        Container container = new SymbolicLinkContainer(link.toFile(), null);

        assertEquals(0L, container.getSize());
        assertTrue(container.isReady());
    }

    @Test
    public void symbolicLinkContainerNameIsPrefixed(@TempDir Path tempDir) throws Exception {
        Path target = Files.createFile(tempDir.resolve("real.txt"));
        Path link = Files.createSymbolicLink(tempDir.resolve("alias.txt"), target);

        Container container = new SymbolicLinkContainer(link.toFile(), null);

        assertEquals("(Symbolic Link) alias.txt", container.getName());
    }

    @Test
    public void symbolicLinkContainerExposesPath(@TempDir Path tempDir) throws Exception {
        Path target = Files.createFile(tempDir.resolve("real.txt"));
        File link = Files.createSymbolicLink(tempDir.resolve("alias.txt"), target).toFile();

        Container container = new SymbolicLinkContainer(link, null);

        assertEquals(link.getAbsolutePath(), container.getPath());
    }
}
