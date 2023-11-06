package osmosis.folder.inspector.container;

import java.io.File;

public class ContainerFactory {
    public static Container createContainer(File file) {
        return createContainer(file, null);
    }

    public static Container createContainer(File file, DirectoryContainer parent) {
        if (file.isDirectory()) {
            return new DirectoryContainer(file, parent);
        }
        return new FileContainer(file, parent);
    }
}
