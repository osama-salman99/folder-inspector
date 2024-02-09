package osmosis.folder.inspector.container;

import osmosis.folder.inspector.calculation.AsyncCalculation;
import osmosis.folder.inspector.calculation.CalculationMethod;
import osmosis.folder.inspector.calculation.FutureWrapper;
import osmosis.folder.inspector.calculation.SyncCalculation;
import osmosis.folder.inspector.container.state.ChildrenContainersWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static osmosis.folder.inspector.constants.Constant.MIN_NUMBER_OF_DIRECTORIES;

public class DirectoryContainer extends Container {
    private final ChildrenContainersWrapper childrenContainersWrapper;
    private ChildContainerReadyListener childContainerReadyListener;

    public DirectoryContainer(File file, DirectoryContainer parent) {
        super(file, parent);
        this.childrenContainersWrapper = new ChildrenContainersWrapper(file, this);
        this.childContainerReadyListener = null;
    }

    @Override
    public long calculateSize() {
        List<Container> childrenContainers = getChildrenContainers();
        CalculationMethod calculationMethod = getCalculationMethod(childrenContainers);

        size = calculateSize(calculationMethod);
        ready = true;
        return size;
    }

    private long calculateSize(CalculationMethod calculationMethod) {
        List<Container> childrenContainers = getChildrenContainers();
        List<FutureWrapper<Long>> futures = childrenContainers.stream()
                .map(calculationMethod::calculateSize)
                .collect(Collectors.toList());
        List<FutureWrapper<Long>> doneFutures = new ArrayList<>();
        /*
         TODO: Issue: parent tasks wait forever for child tasks that never get executed due to the thread pool being full
          Test if wait() and notify() can be used here
          Using while loop is very CPU intensive
          Try the Semaphore thingie
         */
        while (!futures.isEmpty()) {
            List<FutureWrapper<Long>> newDoneFutures = futures.stream()
                    .filter(FutureWrapper::isDone)
                    .collect(Collectors.toList());
            if (!newDoneFutures.isEmpty()) {
                childrenContainersWrapper.sortContainers();
                invokeListener();
            }
            doneFutures.addAll(newDoneFutures);
            futures.removeAll(doneFutures);
        }
        return doneFutures.stream()
                .map(FutureWrapper::get)
                .reduce(0L, Long::sum);
    }

    public void setChildContainerReadyListener(ChildContainerReadyListener childContainerReadyListener) {
        this.childContainerReadyListener = childContainerReadyListener;
    }

    public void clearChildContainerReadyListener() {
        this.childContainerReadyListener = null;
    }

    private void invokeListener() {
        if (Objects.nonNull(childContainerReadyListener)) {
            childContainerReadyListener.onContainerReady();
        }
    }

    public List<Container> getChildrenContainers() {
        return childrenContainersWrapper.getChildrenContainers();
    }

    public int getNumberOfChildren() {
        return childrenContainersWrapper.getNumberOfChildren();
    }

    public boolean isEmpty() {
        return childrenContainersWrapper.isEmpty();
    }

    private static CalculationMethod getCalculationMethod(List<Container> childrenContainers) {
        return calculateDirectoryChildrenCount(childrenContainers) >= MIN_NUMBER_OF_DIRECTORIES ?
                new AsyncCalculation() :
                new SyncCalculation();
    }

    private static long calculateDirectoryChildrenCount(List<Container> childrenContainers) {
        return childrenContainers.stream()
                .filter(container -> container instanceof DirectoryContainer)
                .count();
    }
}
