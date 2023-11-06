package osmosis.folder.inspector.container;

public abstract class ContainerReadyListener {
    private Container container;

    public ContainerReadyListener(Container container) {
        this.container = container;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public abstract void onContainerReady();
}
