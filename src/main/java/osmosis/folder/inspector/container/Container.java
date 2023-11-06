package osmosis.folder.inspector.container;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Container {
    private final Container parent;
    private final File file;
    private final List<Container> children;
    private final File[] childrenFiles;
    private ContainerReadyListener containerReadyListener;
    private boolean started;
    private long size;
    private boolean ready;

    public Container(File file) {
        this(file, null);
    }

    public Container(File file, Container parent) {
        this.parent = parent;
        this.file = file;
        this.childrenFiles = file.listFiles();
        this.children = new ArrayList<>();
        this.containerReadyListener = null;
        this.started = false;
        this.ready = false;
        this.size = -1;
    }

    public void calculateSize() {
        started = true;
        size = 0;
        if (file.isDirectory()) {
            if (childrenFiles != null) {
                for (File child : childrenFiles) {
                    children.add(new Container(child, this));
                }
            }
            for (Container directory : new ArrayList<>(children)) {
                directory.calculateSize();
                size += directory.getSize();
                children.sort(Comparator.comparingLong(Container::getSize));
                Collections.reverse(children);
                invokeListener();
            }
        } else {
            size = file.length();
        }
        ready = true;
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

    public List<Container> getChildren() {
        return children;
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

    public int getNumberOfChildren() {
        return childrenFiles != null ? childrenFiles.length : 0;
    }
}
