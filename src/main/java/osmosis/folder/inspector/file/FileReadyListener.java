package osmosis.folder.inspector.file;

public abstract class FileReadyListener {
    private Container container;

    public FileReadyListener(Container container) {
        this.container = container;
    }

    public Container getFile() {
        return container;
    }

    public void setFile(Container container) {
        this.container = container;
    }

    public abstract void onFileReady();
}
