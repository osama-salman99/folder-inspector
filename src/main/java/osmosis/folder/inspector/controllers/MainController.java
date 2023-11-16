package osmosis.folder.inspector.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import osmosis.folder.inspector.constants.Constant;
import osmosis.folder.inspector.constants.ErrorMessages;
import osmosis.folder.inspector.constants.ResourcePaths;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> pathInputField.requestFocus());
    }

    public void inspect(ActionEvent actionEvent) {
        showProgressIndicator();
        informationBox.setDisable(true);
        String path = pathInputField.getText();
        if (path.endsWith(Constant.COLON)) {
            path += Constant.FILE_SEPARATOR;
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
            containerManager.setCurrentContainer(ContainerFactory.createDirectoryContainer(file));
            informationBox.setDisable(false);
            hideProgressIndicator();
            setScene(actionEvent, ResourcePaths.FOLDERS);
        });
        calculatorThread.setDaemon(true);
        calculatorThread.start();
    }

    private void validateFile(File file) throws InvalidDirectoryException {
        if (!file.exists()) {
            throw new InvalidDirectoryException(ErrorMessages.DIRECTORY_DOES_NOT_EXIST);
        }
        if (!file.isDirectory()) {
            throw new InvalidDirectoryException(ErrorMessages.PATH_DOES_NOT_POINT_TO_A_DIRECTORY);
        }
    }

    private void showProgressIndicator() {
        progressIndicator.setVisible(true);
    }

    private void hideProgressIndicator() {
        progressIndicator.setVisible(false);
    }

    @FXML
    public void onEnter(ActionEvent actionEvent) {
        inspect(actionEvent);
    }
}
