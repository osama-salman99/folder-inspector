package osmosis.folder.inspector.calculation;

import org.junit.jupiter.api.Test;
import osmosis.folder.inspector.container.ContainerFactory;
import osmosis.folder.inspector.container.DirectoryContainer;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectorySizeCalculatorTest {
    @Test
    public void calculatesSizeOfDirectory() throws InterruptedException {
        File file = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);
        DirectorySizeCalculator.getInstance().calculate(container);
        Thread.sleep(200);
        long size = container.getSize();

        assertEquals(3543, size);
    }
}