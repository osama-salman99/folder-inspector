package osmosis.folder.inspector.container;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class DirectoryContainer extends Container {
    private static final Image FOLDER_ICON = new Image(Objects.requireNonNull(FileContainer.class.getResource("/osmosis/icons/folder_icon.png")).toExternalForm());
    private final List<Container> children;
    private final File[] childrenFiles;

    public DirectoryContainer(File file, DirectoryContainer parent) {
        super(file, parent);
        this.childrenFiles = file.listFiles();
        this.children = new ArrayList<>();
    }

    @Override
    public void calculateSize() {
        started = true;
        size = 0;
        if (childrenFiles != null) {
            for (File child : childrenFiles) {
                children.add(ContainerFactory.createContainer(child, this));
            }
        }

        for (Container directory : List.copyOf(children)) {
            directory.calculateSize();
            size += directory.getSize();
            children.sort(Comparator.comparingLong(Container::getSize));
            Collections.reverse(children);
            invokeListener();
        }
        ready = true;
    }

    @Override
    public Image getIcon() {
        return FOLDER_ICON;
    }

    public List<Container> getChildren() {
        return children;
    }

    public int getNumberOfChildren() {
        return childrenFiles.length;
    }
}
