package osmosis.folder.inspector.container;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.text.DecimalFormat;

public class ContainerPane extends BorderPane {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###");
    private static final Color ACCENT_COLOR = Color.color(190 / 256f, 211 / 256f, 231 / 256f);
    private static boolean previousIsDefault = true;
    private final Container container;

    public ContainerPane(Container container) {
        this.container = container;
        setPadding(new Insets(0, 20, 0, 20));
        if (previousIsDefault) {
            setBackground(new Background(new BackgroundFill(ACCENT_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        previousIsDefault = !previousIsDefault;

        setLeft(new Label(container.getName()));
        if (container.isReady()) {
            setRight(new Label(DECIMAL_FORMAT.format(container.getSize())));
        } else {
            ProgressIndicator progressIndicator = new ProgressIndicator();
            progressIndicator.setMaxHeight(13);
            progressIndicator.setMaxWidth(13);
            setRight(progressIndicator);
        }
    }

    public Container getFile() {
        return container;
    }
}
