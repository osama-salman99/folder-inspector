package osmosis.folder.inspector.container;

import osmosis.folder.inspector.constants.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DirectoryContainer extends Container {
    private final List<Container> childrenContainers;
    private final File[] childrenFiles;
    private ChildContainerReadyListener childContainerReadyListener;

    public DirectoryContainer(File file, DirectoryContainer parent) {
        super(file, parent);
        this.childrenFiles = Optional.ofNullable(file.listFiles()).orElse(Constant.EMPTY_FILES_ARRAY);
        this.childContainerReadyListener = null;
        this.childrenContainers = new ArrayList<>();
    }

    @Override
    public void calculateSize() {
        size = 0;
        if (childrenFiles != null) {
            for (File child : childrenFiles) {
                childrenContainers.add(ContainerFactory.createContainer(child, this));
            }
        }

        for (Container directory : List.copyOf(childrenContainers)) {
            directory.calculateSize();
            size += directory.getSize();
            childrenContainers.sort(Comparator.comparingLong(Container::getSize));
            Collections.reverse(childrenContainers);
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
        return childrenContainers;
    }

    public int getNumberOfChildren() {
        return childrenFiles.length;
    }

    public boolean isEmpty() {
        return childrenFiles.length == 0;
    }
}
