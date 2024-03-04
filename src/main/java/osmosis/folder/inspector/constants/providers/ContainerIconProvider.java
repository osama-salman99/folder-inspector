package osmosis.folder.inspector.constants.providers;

import javafx.scene.image.Image;
import osmosis.folder.inspector.FolderInspector;
import osmosis.folder.inspector.constants.ResourcePaths;
import osmosis.folder.inspector.container.Container;
import osmosis.folder.inspector.container.DirectoryContainer;
import osmosis.folder.inspector.container.FileContainer;
import osmosis.folder.inspector.container.SymbolicLinkContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ContainerIconProvider {
    private static final Map<Class<? extends Container>, Image> iconsMap = new HashMap<>() {
        {
            put(DirectoryContainer.class, createImage(ResourcePaths.FOLDER_ICON));
            put(FileContainer.class, createImage(ResourcePaths.FILE_ICON));
            put(SymbolicLinkContainer.class, createImage(ResourcePaths.LINK_ICON));
        }
    };

    private ContainerIconProvider() {
    }

    public static Image getIcon(Container container) {
        return iconsMap.get(container.getClass());
    }

    private static Image createImage(String folderIcon) {
        return new Image(Objects.requireNonNull(FolderInspector.class.getResource(folderIcon)).toExternalForm());
    }
}
