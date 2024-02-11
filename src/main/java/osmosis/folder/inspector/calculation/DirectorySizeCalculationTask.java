package osmosis.folder.inspector.calculation;

import osmosis.folder.inspector.container.Container;
import osmosis.folder.inspector.container.DirectoryContainer;
import osmosis.folder.inspector.container.FileContainer;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class DirectorySizeCalculationTask extends RecursiveTask<Long> {
    private final DirectoryContainer directoryContainer;

    public DirectorySizeCalculationTask(DirectoryContainer directoryContainer) {
        this.directoryContainer = directoryContainer;
    }

    @Override
    protected Long compute() {
        List<Container> childrenContainers = directoryContainer.getChildrenContainers();
        long directoriesSizes = calculateDirectoriesSizes(childrenContainers);
        long filesSizes = calculateFilesSizes(childrenContainers);
        long size = directoriesSizes + filesSizes;
        directoryContainer.setSize(size);
        directoryContainer.invokeListener();
        return size;
    }

    private long calculateDirectoriesSizes(List<Container> childrenContainers) {
        return childrenContainers.stream()
                .filter(container -> container instanceof DirectoryContainer)
                .map(container -> new DirectorySizeCalculationTask((DirectoryContainer) container))
                .map(ForkJoinTask::fork)
                .mapToLong(ForkJoinTask::join)
                .sum();
    }

    private static long calculateFilesSizes(List<Container> childrenContainers) {
        return childrenContainers.stream()
                .filter(container -> container instanceof FileContainer)
                .mapToLong(Container::getSize)
                .sum();
    }
}
