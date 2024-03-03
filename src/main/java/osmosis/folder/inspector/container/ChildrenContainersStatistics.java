package osmosis.folder.inspector.container;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ChildrenContainersStatistics {
    private final List<Container> childrenContainers;
    private final long numberOfReadyContainers;
    private final long numberOfChildren;

    private ChildrenContainersStatistics(List<Container> childrenContainers, long numberOfReadyContainers, long numberOfChildren) {
        this.childrenContainers = childrenContainers;
        this.numberOfReadyContainers = numberOfReadyContainers;
        this.numberOfChildren = numberOfChildren;
    }

    public List<Container> getChildrenContainers() {
        return childrenContainers;
    }

    public long getNumberOfReadyContainers() {
        return numberOfReadyContainers;
    }

    public long getNumberOfChildren() {
        return numberOfChildren;
    }

    public static ChildrenContainersStatistics calculate(List<Container> containers) {
        return new ChildrenContainersStatistics(
                containers.stream().sorted(((Comparator<Container>) Container::compareTo).reversed()).collect(Collectors.toList()),
                containers.stream().filter(Container::isReady).count(),
                containers.size()
        );
    }
}
