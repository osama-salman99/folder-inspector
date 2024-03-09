package osmosis.folder.inspector.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import osmosis.folder.inspector.constants.Constant;
import osmosis.folder.inspector.constants.ErrorMessages;
import osmosis.folder.inspector.constants.ResourcePaths;
import osmosis.folder.inspector.container.ContainerFactory;
import osmosis.folder.inspector.exceptions.InvalidDirectoryException;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller {
    public VBox mainBox;
    public Button inspectButton;
    public TextField pathInputField;
    public ProgressIndicator progressIndicator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this::initializePathInputField);
    }

    private void initializePathInputField() {
        pathInputField.requestFocus();
        pathInputField.addEventHandler(KeyEvent.KEY_TYPED, keyEvent -> greyOutInspectButton());
        data.getMainPath().ifPresent(pathInputField::setText);
        greyOutInspectButton();
    }

    private void greyOutInspectButton() {
        Platform.runLater(() -> inspectButton.setDisable(pathInputField.getText().isBlank()));
    }

    public void inspect(ActionEvent actionEvent) {
        showProgressIndicator();
        mainBox.setDisable(true);
        File file;
        try {
            file = getFile();
        } catch (InvalidDirectoryException exception) {
            showAlert(exception.getMessage());
            return;
        }
        goToFolderView(actionEvent, file);
    }

    private File getFile() throws InvalidDirectoryException {
        String path = getPath();
        validatePath(path);
        File file = new File(path);
        validateFile(file);
        return file;
    }

    private void showAlert(String message) {
        showErrorAlert(message);
        mainBox.setDisable(false);
        hideProgressIndicator();
    }

    private void goToFolderView(ActionEvent actionEvent, File file) {
        data.setMainPath(file.getAbsolutePath());
        containerManager.setCurrentContainer(ContainerFactory.createDirectoryContainer(file));
        mainBox.setDisable(false);
        hideProgressIndicator();
        setScene(actionEvent, ResourcePaths.FOLDERS_FXML);
    }

    private String getPath() {
        String path = pathInputField.getText();
        if (path.endsWith(Constant.COLON)) {
            path += Constant.FILE_SEPARATOR;
        }
        return path;
    }

    private void validatePath(String path) throws InvalidDirectoryException {
        if (path.isBlank()) {
            throw new InvalidDirectoryException(ErrorMessages.PATH_CANNOT_BE_EMPTY);
        }
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
