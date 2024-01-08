package osmosis.folder.inspector.container;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContainerTest {

    @Test
    public void calculatesSizeOfFile() {
        File file = getFile("folder1/folder4/f1-file1.txt");
        Container container = ContainerFactory.createContainer(file, null);
        container.calculateSize();
        long size = container.getSize();

        assertEquals(24, size);
    }

    @Test
    public void calculatesSizeOfTextFileWithLineBreaks() {
        File file = getFile("folder1/folder2/f2-file1.txt");
        Container container = ContainerFactory.createContainer(file, null);
        container.calculateSize();
        long size = container.getSize();

        assertEquals(3495, size);
    }

    @Test
    public void calculatesSizeOfDirectory() {
        File file = getFile("folder1");
        Container container = ContainerFactory.createDirectoryContainer(file);
        container.calculateSize();
        long size = container.getSize();

        assertEquals(3543, size);
    }

    private File getFile(String path) {
        return new File(Objects.requireNonNull(getClass().getClassLoader().getResource(path)).getFile());
    }
}