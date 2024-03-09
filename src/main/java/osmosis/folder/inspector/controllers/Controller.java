package osmosis.folder.inspector.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import osmosis.folder.inspector.constants.ErrorMessages;
import osmosis.folder.inspector.constants.providers.ResourcePathProvider;
import osmosis.folder.inspector.container.ContainerManager;
import osmosis.folder.inspector.controllers.data.ControllersData;

import java.util.Objects;

public abstract class Controller implements Initializable {
    protected static final ContainerManager containerManager = ContainerManager.getInstance();
    protected static final ControllersData data = ControllersData.getInstance();

    protected void setScene(ActionEvent actionEvent, String resourceName) {
        setSceneAsync(getStage(actionEvent), resourceName);
    }

    protected void setSceneAsync(Stage stage, String resourceName) {
        Platform.runLater(() -> setScene(stage, resourceName));
    }

    private void setScene(Stage stage, String resourceName) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(ResourcePathProvider.getFxml(resourceName))))));
        } catch (Exception exception) {
            exception.printStackTrace();
            showErrorAlert(ErrorMessages.ERROR_OCCURRED + exception.getMessage());
        }
    }

    protected static Stage getStage(ActionEvent actionEvent) {
        return (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
    }

    protected static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}
