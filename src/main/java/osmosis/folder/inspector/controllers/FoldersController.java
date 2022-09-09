package osmosis.folder.inspector.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import osmosis.folder.inspector.file.File;
import osmosis.folder.inspector.file.FilePane;
import osmosis.folder.inspector.file.FileReadyListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FoldersController extends Controller {
	public ProgressIndicator progressIndicator;
	public TextField addressBar;
	public Button backButton;
	public Text progressText;
	public VBox foldersVBox;
	private FileReadyListener fileReadyListener;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addressBar.setText(File.getCurrentFile().getPath());
		File file = File.getCurrentFile();
		fileReadyListener = new FileReadyListener(file) {
			@Override
			public void onFileReady() {
				Platform.runLater(() -> showFile(fileReadyListener.getFile()));
			}
		};
		showFile(file);
	}

	@FXML
	public void goBack(ActionEvent actionEvent) {
		File parentFile = File.getCurrentFile().getParent();
		if (parentFile == null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
					"Would you like to go back to the main menu?",
					ButtonType.YES, ButtonType.CANCEL);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				setScene(actionEvent, "main.fxml");
			}
			return;
		}
		showFile(parentFile);
	}

	private void showFile(File file) {
		addressBar.requestFocus();
		File.setCurrentFile(file);
		if (file.getNumberOfChildren() == 0) {
			return;
		}
		addressBar.setText(file.getPath());
		fileReadyListener.setFile(file);
		file.setFileReadyListener(this.fileReadyListener);
		if (!file.isStarted()) {
			Thread calculatorThread = new Thread(file::calculateDirectorySize);
			calculatorThread.setDaemon(true);
			calculatorThread.start();
		}
		foldersVBox.getChildren().clear();
		List<File> files = new ArrayList<>(file.getChildren());
		int ready = 0;
		for (File child : files) {
			addFilePane(child);
			if (child.isReady()) {
				ready++;
			}
		}
		updateProgress(ready, file.getNumberOfChildren());
	}

	private void updateProgress(int completed, int all) {
		boolean visibility = completed != all;
		progressIndicator.setVisible(visibility);
		progressText.setVisible(visibility);
		progressText.setText(completed + "/" + all);
	}

	private void addFilePane(File file) {
		FilePane filePane = new FilePane(file);
		filePane.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> showFile(filePane.getFile()));
		foldersVBox.getChildren().add(filePane);
	}
}
