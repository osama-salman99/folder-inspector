package osmosis.folder.inspector.container;

import osmosis.folder.inspector.container.state.ChildrenContainersWrapper;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
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
    public void calculateSize() {
        size = 0;
        List<Container> childrenContainers = getChildrenContainers();

        for (Container directory : List.copyOf(childrenContainers)) {
            directory.calculateSize();
            size += directory.getSize();
            childrenContainersWrapper.sortContainers();
            invokeListener();
        }
        ready = true;
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
