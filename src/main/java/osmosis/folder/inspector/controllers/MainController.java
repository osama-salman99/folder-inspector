package osmosis.folder.inspector.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import osmosis.folder.inspector.file.Container;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller {
    public TextField pathInputField;
    public VBox informationBox;
    public ProgressIndicator progressIndicator;

    public void inspect(ActionEvent actionEvent) {
        showProgressIndicator();
        informationBox.setDisable(true);
        String path = pathInputField.getText();
        if (path.endsWith(":")) {
            path += "\\";
        }
        java.io.File file = new java.io.File(path);
        if (!file.exists()) {
            showErrorAlert("Path is not valid");
            informationBox.setDisable(false);
            hideProgressIndicator();
            return;
        }
        Thread calculatorThread = new Thread(() -> {
            Container.setCurrentFile(new Container(file, null));
            informationBox.setDisable(false);
            hideProgressIndicator();
            setScene(actionEvent, "folders.fxml");
        });
        calculatorThread.setDaemon(true);
        calculatorThread.start();
    }

    private void showProgressIndicator() {
        progressIndicator.setVisible(true);
    }

    private void hideProgressIndicator() {
        progressIndicator.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> pathInputField.requestFocus());
    }

    @FXML
    public void onEnter(ActionEvent actionEvent) {
        inspect(actionEvent);
    }
}
