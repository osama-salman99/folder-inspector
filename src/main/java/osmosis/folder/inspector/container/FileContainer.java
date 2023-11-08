package osmosis.folder.inspector.container;

import javafx.scene.image.Image;

import java.io.File;
import java.util.Collections;
import java.util.List;
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
    public List<Container> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public int getNumberOfChildren() {
        return 0;
    }

    @Override
    public Image getIcon() {
        return FILE_ICON;
    }
}
