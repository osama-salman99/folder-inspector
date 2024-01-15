package osmosis.folder.inspector.container;

import java.io.File;

public class FileContainer extends Container {
    public FileContainer(File file, DirectoryContainer parent) {
        super(file, parent);
    }

    @Override
    public long calculateSize() {
        size = file.length();
        ready = true;
        return size;
    }
}
