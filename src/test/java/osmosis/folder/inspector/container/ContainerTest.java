package osmosis.folder.inspector.container;

import org.junit.jupiter.api.Test;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContainerTest {
    @Test
    public void calculatesSizeOfFile() {
        File file = TestUtils.getInstance().getFile("folder1/folder4/f1-file1.txt");
        Container container = ContainerFactory.createContainer(file, null);
        long size = container.getSize();

        assertEquals(24, size);
    }
}