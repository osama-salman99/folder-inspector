package osmosis.folder.inspector.container;

import java.io.File;
import java.util.List;

public abstract class Container {
    protected final Container parent;
    protected final File file;
    protected ContainerReadyListener containerReadyListener;
    protected boolean started;
    protected long size;
    protected boolean ready;

    public Container(File file, Container parent) {
        this.parent = parent;
        this.file = file;
        this.containerReadyListener = null;
        this.started = false;
        this.ready = false;
        this.size = -1;
    }

    public abstract List<Container> getChildren();

    public abstract int getNumberOfChildren();

    public abstract void calculateSize();

    public String getPath() {
        return file.getAbsolutePath();
    }

    public Container getParent() {
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
        if (containerReadyListener != null) {
            containerReadyListener.onContainerReady();
        }
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isReady() {
        return ready;
    }
}
