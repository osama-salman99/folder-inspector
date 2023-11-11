package osmosis.folder.inspector.container;

import javafx.scene.image.Image;

import java.io.File;
import java.util.Objects;

public class FileContainer extends Container {
    private static final Image FILE_ICON = new Image(Objects.requireNonNull(FileContainer.class.getResource("/osmosis/icons/file_icon.png")).toExternalForm());

    public FileContainer(File file, DirectoryContainer parent) {
        super(file, parent);
    }

    @Override
    public void calculateSize() {
        started = true;
        size = file.length();
        ready = true;
    }

    @Override
    public Image getIcon() {
        return FILE_ICON;
    }
}
