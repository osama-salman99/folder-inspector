package osmosis.folder.inspector.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.Objects;

public abstract class Controller implements Initializable {

    protected void setScene(ActionEvent actionEvent, String resourceName) {
        setScene(getStage(actionEvent), resourceName);
    }

    protected void setScene(Stage stage, String resourceName) {
        Platform.runLater(() -> {
            try {
                stage.setScene(new Scene(FXMLLoader
                        .load(Objects.requireNonNull(getClass().getResource("/osmosis/folder/inspector/" + resourceName)))));
            } catch (Exception exception) {
                exception.printStackTrace();
                showErrorAlert("Error occurred: " + exception.getMessage());
            }
        });
    }

    protected static Stage getStage(ActionEvent actionEvent) {
        return (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
    }

    protected static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}
