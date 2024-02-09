package osmosis.folder.inspector.container;

public class ContainerManager {
    private static final ContainerManager INSTANCE = new ContainerManager();
    private DirectoryContainer currentContainer;

    private ContainerManager() {
    }

    public DirectoryContainer getCurrentContainer() {
        return currentContainer;
    }

    public void setCurrentContainer(DirectoryContainer currentContainer) {
        this.currentContainer = currentContainer;
    }

    public void clearContainer() {
        this.currentContainer = null;
    }

    public static ContainerManager getInstance() {
        return INSTANCE;
    }
}
