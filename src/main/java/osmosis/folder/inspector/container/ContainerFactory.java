package osmosis.folder.inspector.container;

import java.io.File;
import java.nio.file.Files;

public class ContainerFactory {

    private ContainerFactory() {
    }

    public static DirectoryContainer createDirectoryContainer(File file) {
        return new DirectoryContainer(file, null);
    }

    public static Container createContainer(File file, DirectoryContainer parent) {
        if (Files.isSymbolicLink(file.toPath())) {
            return new SymbolicLinkContainer(file, parent);
        }
        if (file.isDirectory()) {
            return new DirectoryContainer(file, parent);
        }
        return new FileContainer(file, parent);
    }
}
