package osmosis.folder.inspector.panes;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import osmosis.folder.inspector.constants.Constant;
import osmosis.folder.inspector.container.Container;

import java.util.concurrent.atomic.AtomicBoolean;

import static osmosis.folder.inspector.constants.Constant.ICON_SIZE;
import static osmosis.folder.inspector.constants.Constant.PROGRESS_INDICATOR_SIZE;

public class ContainerPane extends BorderPane {
    public static final Background ACCENT_COLORED_BACKGROUND = new Background(new BackgroundFill(Constant.ACCENT_COLOR, CornerRadii.EMPTY, Insets.EMPTY));
    private static final AtomicBoolean previousIsDefault = new AtomicBoolean(true);
    private final Container container;

    public ContainerPane(Container container) {
        this.container = container;
        setPadding(new Insets(5, 20, 5, 20));
        if (previousIsDefault.get()) {
            setBackground(ACCENT_COLORED_BACKGROUND);
        }
        previousIsDefault.set(!previousIsDefault.get());

        setLeft(createNamePane(container));
        setRight(createSizeLabel(container));
    }

    private Node createSizeLabel(Container container) {
        if (container.isReady()) {
            return new Label(Constant.DECIMAL_FORMAT.format(container.getSize()));
        }
        return createProgressIndicator();
    }

    public Container getContainer() {
        return container;
    }

    private static Pane createNamePane(Container container) {
        BorderPane pane = new BorderPane();
        pane.setRight(createNameLabel(container));
        pane.setLeft(createIcon(container));
        return pane;
    }

    private static Label createNameLabel(Container container) {
        Label value = new Label(container.getName());
        value.setPadding(new Insets(0, 10, 0, 10));
        return value;
    }

    private static ImageView createIcon(Container container) {
        ImageView imageView = new ImageView(container.getIcon());
        imageView.setFitHeight(ICON_SIZE);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private static ProgressIndicator createProgressIndicator() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxSize(PROGRESS_INDICATOR_SIZE, PROGRESS_INDICATOR_SIZE);
        return progressIndicator;
    }
}
