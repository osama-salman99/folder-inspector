package osmosis.folder.inspector.file;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.text.DecimalFormat;

public class FilePane extends BorderPane {
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###");
	private static final Color ACCENT_COLOR = Color.color(190 / 256f, 211 / 256f, 231 / 256f);
	private static boolean previousIsDefault = true;
	private final File file;

	public FilePane(File file) {
		this.file = file;
		setPadding(new Insets(0, 20, 0, 20));
		if (previousIsDefault) {
			setBackground(new Background(new BackgroundFill(ACCENT_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		previousIsDefault = !previousIsDefault;

		setLeft(new Label(file.getName()));
		if (file.isReady()) {
			setRight(new Label(DECIMAL_FORMAT.format(file.getSize())));
		} else {
			ProgressIndicator progressIndicator = new ProgressIndicator();
			progressIndicator.setMaxHeight(13);
			progressIndicator.setMaxWidth(13);
			setRight(progressIndicator);
		}
	}

	public File getFile() {
		return file;
	}
}
