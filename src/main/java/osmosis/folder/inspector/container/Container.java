package osmosis.folder.inspector.container;

import java.io.File;
import java.util.Objects;

public abstract class Container {
    protected final File file;
    protected final DirectoryContainer parent;
    protected long size;
    protected boolean ready;
    protected ContainerReadyListener containerReadyListener;

    public Container(File file, DirectoryContainer parent) {
        this.parent = parent;
        this.file = file;
        this.containerReadyListener = null;
        this.ready = false;
        this.size = Long.MIN_VALUE;
    }

    public String getPath() {
        return file.getAbsolutePath();
    }

    public DirectoryContainer getParent() {
        return parent;
    }

    public long getSize() {
        return size;
    }

    public String getName() {
        return file.getName();
    }

    public void setContainerReadyListener(ContainerReadyListener containerReadyListener) {
        this.containerReadyListener = containerReadyListener;
    }

    public void invokeListener() {
        if (Objects.nonNull(containerReadyListener)) {
            containerReadyListener.onContainerReady();
        }
    }

    public boolean isReady() {
        return ready;
    }

    public boolean hasParentContainer() {
        return Objects.nonNull(parent);
    }

    public abstract void calculateSize();
}
