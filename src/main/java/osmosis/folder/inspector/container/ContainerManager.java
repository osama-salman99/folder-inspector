package osmosis.folder.inspector.container;

import java.io.File;
import java.util.Objects;

public class ContainerManager {
    private static final ContainerManager INSTANCE = new ContainerManager();
    private DirectoryContainer currentContainer;

    private ContainerManager() {
    }

    public DirectoryContainer getCurrentContainer() {
        return currentContainer;
    }

    public void setRootContainer(File file) {
        this.currentContainer = ContainerFactory.createDirectoryContainer(file);
    }

    public void navigateTo(DirectoryContainer container, ChildContainerReadyListener listener) {
        if (Objects.nonNull(currentContainer)) {
            currentContainer.clearChildContainerReadyListener();
        }
        this.currentContainer = container;
        container.setChildContainerReadyListener(listener);
    }

    public void clearContainer() {
        this.currentContainer = null;
    }

    public static ContainerManager getInstance() {
        return INSTANCE;
    }
}
