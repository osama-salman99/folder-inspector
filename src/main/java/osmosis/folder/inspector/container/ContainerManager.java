package osmosis.folder.inspector.container;

public class ContainerManager {
    private static final ContainerManager INSTANCE = new ContainerManager();
    private Container currentContainer;

    public Container getCurrentContainer() {
        return currentContainer;
    }

    public void setCurrentContainer(Container currentContainer) {
        this.currentContainer = currentContainer;
    }

    public void clearContainer() {
        this.currentContainer = null;
    }

    public static ContainerManager getInstance() {
        return INSTANCE;
    }
}
