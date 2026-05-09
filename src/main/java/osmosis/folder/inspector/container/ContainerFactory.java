package osmosis.folder.inspector.container;

import java.io.File;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContainerFactory {
    private static final Logger LOGGER = Logger.getLogger(ContainerFactory.class.getName());

    private ContainerFactory() {
    }

    public static DirectoryContainer createDirectoryContainer(File file) {
        LOGGER.log(Level.INFO, "Creating root directory container for {0}", file.getAbsolutePath());
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
