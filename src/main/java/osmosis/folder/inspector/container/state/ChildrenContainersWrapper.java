package osmosis.folder.inspector.container.state;

import osmosis.folder.inspector.constants.Constant;
import osmosis.folder.inspector.container.Container;
import osmosis.folder.inspector.container.ContainerFactory;
import osmosis.folder.inspector.container.DirectoryContainer;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChildrenContainersWrapper {
    private final File file;
    private final DirectoryContainer directoryContainer;
    private List<Container> containers;

    public ChildrenContainersWrapper(File file, DirectoryContainer directoryContainer) {
        this.file = file;
        this.directoryContainer = directoryContainer;
    }

    public List<Container> getChildrenContainers() {
        if (containers == null) {
            containers = Arrays.stream(Optional.ofNullable(file.listFiles()).orElse(Constant.EMPTY_FILES_ARRAY))
                    .map(child -> ContainerFactory.createContainer(child, directoryContainer))
                    .collect(Collectors.toList());
        }
        return containers;
    }

    public boolean isEmpty() {
        return getChildrenContainers().isEmpty();
    }

    public int getNumberOfChildren() {
        return getChildrenContainers().size();
    }

    public void sortContainers() {
        List<Container> childrenContainers = getChildrenContainers();
        childrenContainers.sort(Comparator.comparingLong(Container::getSize));
        Collections.reverse(childrenContainers);
    }
}
