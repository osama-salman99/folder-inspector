package osmosis.folder.inspector.file;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContainerTest {

    @Test
    public void calculatesSizeOfFile() {
        File file = getFile("folder1/f1-file1.txt");
        Container container = new Container(file);
        container.calculateSize();
        long size = container.getSize();

        assertEquals(24, size);
    }

    @Test
    public void calculatesSizeOfDirectory() {
        File file = getFile("folder1");
        Container container = new Container(file);
        container.calculateSize();
        long size = container.getSize();

        assertEquals(3519, size);
    }

    private File getFile(String path) {
        return new File(Objects.requireNonNull(getClass().getClassLoader().getResource(path)).getFile());
    }
}