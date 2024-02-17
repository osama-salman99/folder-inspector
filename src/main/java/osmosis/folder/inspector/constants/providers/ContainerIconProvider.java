package osmosis.folder.inspector.constants.providers;

import javafx.scene.image.Image;
import osmosis.folder.inspector.constants.ResourcePaths;
import osmosis.folder.inspector.container.Container;
import osmosis.folder.inspector.container.DirectoryContainer;
import osmosis.folder.inspector.container.FileContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ContainerIconProvider {
    private static final Map<Class<? extends Container>, Image> ICONS_MAP = new HashMap<>() {
        {
            put(DirectoryContainer.class, new Image(Objects.requireNonNull(FileContainer.class.getResource(ResourcePaths.FOLDER_ICON)).toExternalForm()));
            put(FileContainer.class, new Image(Objects.requireNonNull(FileContainer.class.getResource(ResourcePaths.FILE_ICON)).toExternalForm()));
        }
    };

    private ContainerIconProvider() {
    }

    public static Image getIcon(Container container) {
        return ICONS_MAP.get(container.getClass());
    }
}
