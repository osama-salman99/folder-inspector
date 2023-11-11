package osmosis.folder.inspector.container;

import java.io.File;

public class ContainerFactory {
    public static DirectoryContainer createContainer(File file) {
        return new DirectoryContainer(file, null);
    }

    public static Container createContainer(File file, DirectoryContainer parent) {
        if (file.isDirectory()) {
            return new DirectoryContainer(file, parent);
        }
        return new FileContainer(file, parent);
    }
}
