package osmosis.folder.inspector.container;

import java.io.File;
import java.util.List;

public abstract class Container {
    protected long size;
    protected boolean ready;
    protected boolean started;
    protected final File file;
    protected final Container parent;
    protected ContainerReadyListener containerReadyListener;

    public Container(File file, Container parent) {
        this.parent = parent;
        this.file = file;
        this.containerReadyListener = null;
        this.started = false;
        this.ready = false;
        this.size = Long.MAX_VALUE;
    }

    public abstract List<Container> getChildren();

    public abstract void calculateSize();

    public int getNumberOfChildren() {
        return getChildren().size();
    }

    public boolean hasNoChildren() {
        return getNumberOfChildren() == 0;
    }

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
