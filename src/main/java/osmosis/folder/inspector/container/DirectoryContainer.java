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

    public void setChildContainerReadyListener(ChildContainerReadyListener childContainerReadyListener) {
        this.childContainerReadyListener = childContainerReadyListener;
    }

    public void clearChildContainerReadyListener() {
        this.childContainerReadyListener = null;
    }

    public void invokeListener() {
        if (hasListener()) {
            childContainerReadyListener.onContainerReady();
        } else if (hasParentContainer() && parent.hasListener()) {
            parent.invokeListener();
        }
    }

    private boolean hasListener() {
        return Objects.nonNull(childContainerReadyListener);
    }

    public List<Container> getChildrenContainers() {
        return childrenContainersWrapper.getChildrenContainers();
    }

    public boolean isEmpty() {
        return childrenContainersWrapper.isEmpty();
    }

    public void setSize(long size) {
        this.size = size;
    }
}
