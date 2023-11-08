package osmosis.folder.inspector.container;

import javafx.scene.image.Image;

import java.io.File;
import java.util.List;
import java.util.Objects;

public abstract class Container {
    protected final File file;
    protected final DirectoryContainer parent;
    protected long size;
    protected boolean ready;
    protected boolean started;
    protected ContainerReadyListener containerReadyListener;

    public Container(File file, DirectoryContainer parent) {
        this.parent = parent;
        this.file = file;
        this.containerReadyListener = null;
        this.started = false;
        this.ready = false;
        this.size = Long.MIN_VALUE;
    }

    public int getNumberOfChildren() {
        return getChildren().size();
    }

    public boolean hasNoChildren() {
        return getNumberOfChildren() == 0;
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

    public boolean isStarted() {
        return started;
    }

    public boolean isReady() {
        return ready;
    }

    public boolean hasParentContainer() {
        return Objects.nonNull(parent);
    }

    public abstract List<Container> getChildren();

    public abstract void calculateSize();

    public abstract Image getIcon();
}
