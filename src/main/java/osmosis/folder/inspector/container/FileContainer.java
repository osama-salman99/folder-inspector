package osmosis.folder.inspector.container;

import java.io.File;

public class FileContainer extends Container {
    public FileContainer(File file, DirectoryContainer parent) {
        super(file, parent);
        setSize(file.length());
    }
}
