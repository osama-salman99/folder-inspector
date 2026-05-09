package osmosis.folder.inspector.container;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ContainerManager {
    private static final Logger LOGGER = Logger.getLogger(ContainerManager.class.getName());
    private static final ContainerManager INSTANCE = new ContainerManager();
    private DirectoryContainer currentContainer;

    private ContainerManager() {
    }

    public DirectoryContainer getCurrentContainer() {
        return currentContainer;
    }

    public void setCurrentContainer(DirectoryContainer currentContainer) {
        LOGGER.log(Level.INFO, "Current container set to {0}", currentContainer.getPath());
        this.currentContainer = currentContainer;
    }

    public void clearContainer() {
        LOGGER.log(Level.INFO, "Cleared current container");
        this.currentContainer = null;
    }

    public static ContainerManager getInstance() {
        return INSTANCE;
    }
}
