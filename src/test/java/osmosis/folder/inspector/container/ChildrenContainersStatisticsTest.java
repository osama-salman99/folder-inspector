package osmosis.folder.inspector.container;

import org.junit.jupiter.api.Test;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChildrenContainersStatisticsTest {
    @Test
    public void calculateOnEmptyListProducesZeroCounts() {
        ChildrenContainersStatistics statistics = ChildrenContainersStatistics.calculate(List.of());

        assertEquals(0L, statistics.getNumberOfChildren());
        assertEquals(0L, statistics.getNumberOfReadyContainers());
        assertEquals(List.of(), statistics.getChildrenContainers());
    }

    @Test
    public void calculateCountsTotalChildren() {
        File folder2 = TestUtils.getInstance().getFile("folder1/folder2");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(folder2);

        ChildrenContainersStatistics statistics = ChildrenContainersStatistics.calculate(container.getChildrenContainers());

        assertEquals(3L, statistics.getNumberOfChildren());
    }

    @Test
    public void calculateCountsOnlyReadyChildren() {
        File folder1 = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(folder1);

        ChildrenContainersStatistics statistics = ChildrenContainersStatistics.calculate(container.getChildrenContainers());

        long fileChildren = container.getChildrenContainers().stream().filter(Container::isReady).count();
        assertEquals(fileChildren, statistics.getNumberOfReadyContainers());
    }

    @Test
    public void calculateOrdersChildrenBySizeDescending() {
        File folder2 = TestUtils.getInstance().getFile("folder1/folder2");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(folder2);

        ChildrenContainersStatistics statistics = ChildrenContainersStatistics.calculate(container.getChildrenContainers());
        List<Container> ordered = statistics.getChildrenContainers();

        assertEquals(3, ordered.size());
        for (int index = 0; index < ordered.size() - 1; index++) {
            assertEquals(true, ordered.get(index).getSize() >= ordered.get(index + 1).getSize());
        }
    }
}
