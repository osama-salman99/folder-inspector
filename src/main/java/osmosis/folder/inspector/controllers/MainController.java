package osmosis.folder.inspector.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import osmosis.folder.inspector.container.ContainerFactory;
import osmosis.folder.inspector.container.ContainerManager;
import osmosis.folder.inspector.exceptions.InvalidDirectoryException;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller {
    private static final ContainerManager containerManager = ContainerManager.getInstance();
    public VBox informationBox;
    public TextField pathInputField;
    public ProgressIndicator progressIndicator;

    public void inspect(ActionEvent actionEvent) {
        showProgressIndicator();
        informationBox.setDisable(true);
        String path = pathInputField.getText();
        if (path.endsWith(":")) {
            path += "\\";
        }
        File file = new File(path);
        try {
            validateFile(file);
        } catch (InvalidDirectoryException exception) {
            showErrorAlert(exception.getMessage());
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

    private void validateFile(File file) throws InvalidDirectoryException {
        if (!file.exists()) {
            throw new InvalidDirectoryException("Directory does not exist");
        }
        if (!file.isDirectory()) {
            throw new InvalidDirectoryException("Path does not point to a directory");
        }
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
