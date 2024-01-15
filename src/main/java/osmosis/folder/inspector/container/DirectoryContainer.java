package osmosis.folder.inspector.container;

import osmosis.folder.inspector.container.state.ChildrenContainersWrapper;

import java.io.File;
import java.util.List;
import java.util.Objects;

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

        // TODO: Calculate how many file children there are and sum them up
        if (childrenContainers.stream()
                .filter(container -> container instanceof DirectoryContainer)
                .count() > 7) {
            // TODO: Start a thread for each folder
        }

        size = 0;
        for (Container directory : childrenContainers) {
            directory.calculateSize();
            size += directory.getSize();
            childrenContainersWrapper.sortContainers();
            invokeListener();
        }
        ready = true;
        return size;
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
}
