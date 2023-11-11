package osmosis.folder.inspector.container;

public abstract class ContainerReadyListener {
    private DirectoryContainer container;

    public ContainerReadyListener(DirectoryContainer container) {
        this.container = container;
    }

    public DirectoryContainer getContainer() {
        return container;
    }

    public void setContainer(DirectoryContainer container) {
        this.container = container;
    }

    public abstract void onContainerReady();
}
