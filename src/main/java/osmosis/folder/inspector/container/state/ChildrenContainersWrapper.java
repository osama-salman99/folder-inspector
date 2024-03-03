package osmosis.folder.inspector.container.state;

import osmosis.folder.inspector.constants.Constant;
import osmosis.folder.inspector.container.Container;
import osmosis.folder.inspector.container.ContainerFactory;
import osmosis.folder.inspector.container.DirectoryContainer;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChildrenContainersWrapper {
    private final File file;
    private final DirectoryContainer directoryContainer;
    private State state = new EmptyState();
    private List<Container> containers;

    public ChildrenContainersWrapper(File file, DirectoryContainer directoryContainer) {
        this.file = file;
        this.directoryContainer = directoryContainer;
    }

    public List<Container> getChildrenContainers() {
        return state.getChildrenContainers();
    }

    public boolean isEmpty() {
        return getChildrenContainers().isEmpty();
    }

    private interface State {
        List<Container> getChildrenContainers();
    }

    private class EmptyState implements State {
        @Override
        public List<Container> getChildrenContainers() {
            containers = Arrays.stream(Optional.ofNullable(file.listFiles()).orElse(Constant.EMPTY_FILES_ARRAY))
                    .map(child -> ContainerFactory.createContainer(child, directoryContainer))
                    .collect(Collectors.toList());
            state = new InitializedState();
            return state.getChildrenContainers();
        }
    }

    private class InitializedState implements State {
        @Override
        public List<Container> getChildrenContainers() {
            return List.copyOf(containers);
        }
    }
}
