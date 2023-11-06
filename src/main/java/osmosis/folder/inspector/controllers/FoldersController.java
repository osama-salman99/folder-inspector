package osmosis.folder.inspector.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import osmosis.folder.inspector.file.Container;
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
        addressBar.setText(Container.getCurrentFile().getPath());
        Container container = Container.getCurrentFile();
        fileReadyListener = new FileReadyListener(container) {
            @Override
            public void onFileReady() {
                Platform.runLater(() -> showFile(fileReadyListener.getFile()));
            }
        };
        showFile(container);
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        Container parentContainer = Container.getCurrentFile().getParent();
        if (parentContainer == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Would you like to go back to the main menu?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                setScene(actionEvent, "main.fxml");
            }
            return;
        }
        showFile(parentContainer);
    }

    private void showFile(Container container) {
        addressBar.requestFocus();
        Container.setCurrentFile(container);
        if (container.getNumberOfChildren() == 0) {
            return;
        }
        addressBar.setText(container.getPath());
        fileReadyListener.setFile(container);
        container.setFileReadyListener(this.fileReadyListener);
        if (!container.isStarted()) {
            Thread calculatorThread = new Thread(container::calculateSize);
            calculatorThread.setDaemon(true);
            calculatorThread.start();
        }
        foldersVBox.getChildren().clear();
        List<Container> containers = new ArrayList<>(container.getChildren());
        int ready = 0;
        for (Container child : containers) {
            addFilePane(child);
            if (child.isReady()) {
                ready++;
            }
        }
        updateProgress(ready, container.getNumberOfChildren());
    }

    private void updateProgress(int completed, int all) {
        boolean visibility = completed != all;
        progressIndicator.setVisible(visibility);
        progressText.setVisible(visibility);
        progressText.setText(completed + "/" + all);
    }

    private void addFilePane(Container container) {
        FilePane filePane = new FilePane(container);
        filePane.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> showFile(filePane.getFile()));
        foldersVBox.getChildren().add(filePane);
    }
}
