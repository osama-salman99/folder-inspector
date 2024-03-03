package osmosis.folder.inspector.container;

import java.io.File;
import java.util.Objects;

public abstract class Container implements Comparable<Container> {
    protected final File file;
    protected final DirectoryContainer parent;
    protected long size;
    protected boolean ready;

    public Container(File file, DirectoryContainer parent) {
        this.parent = parent;
        this.file = file;
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

    public boolean isReady() {
        return size != Long.MIN_VALUE;
    }

    public boolean hasParentContainer() {
        return Objects.nonNull(parent);
    }

    @Override
    public int compareTo(Container that) {
        return Long.compare(this.size, that.size);
    }
}
