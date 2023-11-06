package osmosis.folder.inspector.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Container {
    private static Container currentContainer;
    private final Container parent;
    private final java.io.File file;
    private final List<Container> children;
    private final java.io.File[] childrenFileObjects;
    private FileReadyListener fileReadyListener;
    private boolean started;
    private long size;
    private boolean ready;

    public Container(File file) {
        this(file, null);
    }

    public Container(File file, Container parent) {
        this.parent = parent;
        this.file = file;
        this.childrenFileObjects = file.listFiles();
        this.children = new ArrayList<>();
        this.fileReadyListener = null;
        this.started = false;
        this.ready = false;
        this.size = -1;
    }

    public void calculateSize() {
        started = true;
        size = 0;
        if (file.isDirectory()) {
            if (childrenFileObjects != null) {
                for (java.io.File child : childrenFileObjects) {
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

    public void setFileReadyListener(FileReadyListener fileReadyListener) {
        this.fileReadyListener = fileReadyListener;
    }

    public void invokeListener() {
        if (fileReadyListener != null) {
            fileReadyListener.onFileReady();
        }
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isReady() {
        return ready;
    }

    public int getNumberOfChildren() {
        return childrenFileObjects != null ? childrenFileObjects.length : 0;
    }

    public static Container getCurrentFile() {
        return currentContainer;
    }

    public static void setCurrentFile(Container currentContainer) {
        Container.currentContainer = currentContainer;
    }
}
