package osmosis.folder.inspector.container;

import org.junit.jupiter.api.Test;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileContainerTest {
    @Test
    public void fileContainerSizeMatchesFileLength() {
        File file = TestUtils.getInstance().getFile("folder1/folder2/f2-file1.txt");
        FileContainer container = new FileContainer(file, null);

        assertEquals(552L, container.getSize());
    }

    @Test
    public void emptyFileContainerHasZeroSize() {
        File file = TestUtils.getInstance().getFile("folder1/folder2/f2-file2.txt");
        FileContainer container = new FileContainer(file, null);

        assertEquals(0L, container.getSize());
        assertTrue(container.isReady());
    }
}
