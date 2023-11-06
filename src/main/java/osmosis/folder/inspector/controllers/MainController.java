package osmosis.folder.inspector.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import osmosis.folder.inspector.container.ContainerFactory;
import osmosis.folder.inspector.container.ContainerManager;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller {
    private static final ContainerManager containerManager = ContainerManager.getInstance();
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
        File file = new File(path);
        if (!file.exists()) {
            showErrorAlert("Path is not valid");
            informationBox.setDisable(false);
            hideProgressIndicator();
            return;
        }
        Thread calculatorThread = new Thread(() -> {
            containerManager.setCurrentContainer(ContainerFactory.createContainer(file));
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
