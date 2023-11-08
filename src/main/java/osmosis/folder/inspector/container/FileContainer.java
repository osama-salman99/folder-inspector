package osmosis.folder.inspector.container;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class FileContainer extends Container {
    public FileContainer(File file, DirectoryContainer parent) {
        super(file, parent);
    }

    @Override
    public void calculateSize() {
        started = true;
        size = 0;
        size = file.length();
        ready = true;
    }

    @Override
    public List<Container> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public int getNumberOfChildren() {
        return 0;
    }
}
