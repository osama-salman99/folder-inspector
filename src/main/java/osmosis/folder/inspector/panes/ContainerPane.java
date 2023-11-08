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
import javafx.scene.paint.Color;
import osmosis.folder.inspector.container.Container;

import java.text.DecimalFormat;

import static osmosis.folder.inspector.constants.Constant.ICON_SIZE;
import static osmosis.folder.inspector.constants.Constant.PROGRESS_INDICATOR_SIZE;

public class ContainerPane extends BorderPane {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###");
    private static final Color ACCENT_COLOR = Color.rgb(190, 211, 231);
    private static boolean previousIsDefault = true;
    private final Container container;

    public ContainerPane(Container container) {
        this.container = container;
        setPadding(new Insets(5, 20, 5, 20));
        if (previousIsDefault) {
            setBackground(new Background(new BackgroundFill(ACCENT_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        previousIsDefault = !previousIsDefault;

        setLeft(createNamePane(container));
        setRight(createSizeLabel(container));
    }

    private Node createSizeLabel(Container container) {
        if (container.isReady()) {
            return new Label(DECIMAL_FORMAT.format(container.getSize()));
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
