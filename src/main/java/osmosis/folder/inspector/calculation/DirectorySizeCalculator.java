package osmosis.folder.inspector.calculation;

import osmosis.folder.inspector.container.Container;
import osmosis.folder.inspector.container.DirectoryContainer;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class DirectorySizeCalculator extends RecursiveTask<Long> {

    private final DirectoryContainer directoryContainer;

    public DirectorySizeCalculator(DirectoryContainer directoryContainer) {
        this.directoryContainer = directoryContainer;
    }

    @Override
    protected Long compute() {
        List<Container> childrenContainers = directoryContainer.getChildrenContainers();
        long size = childrenContainers.stream()
                .filter(container -> container instanceof DirectoryContainer)
                .map(container -> new DirectorySizeCalculator((DirectoryContainer) container))
                .map(ForkJoinTask::fork)
                .mapToLong(ForkJoinTask::join)
                .sum();
        size += childrenContainers.stream()
                .filter(container -> !(container instanceof DirectoryContainer))
                .mapToLong(Container::getSize)
                .sum();
        directoryContainer.setSize(size);
        directoryContainer.invokeListener();
        return size;
    }
}
