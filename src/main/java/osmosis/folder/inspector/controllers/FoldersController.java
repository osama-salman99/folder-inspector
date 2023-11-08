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
import osmosis.folder.inspector.container.Container;
import osmosis.folder.inspector.container.ContainerManager;
import osmosis.folder.inspector.container.ContainerPane;
import osmosis.folder.inspector.container.ContainerReadyListener;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FoldersController extends Controller {
    private static final ContainerManager containerManager = ContainerManager.getInstance();
    public ProgressIndicator progressIndicator;
    public TextField addressBar;
    public Button backButton;
    public Text progressText;
    public VBox foldersVBox;
    private ContainerReadyListener containerReadyListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addressBar.setText(containerManager.getCurrentContainer().getPath());
        Container container = containerManager.getCurrentContainer();
        containerReadyListener = new ContainerReadyListener(container) {
            @Override
            public void onContainerReady() {
                Platform.runLater(() -> showContainer(containerReadyListener.getContainer()));
            }
        };
        showContainer(container);
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        if (containerManager.getCurrentContainer().hasParentContainer()) {
            showContainer(containerManager.getCurrentContainer().getParent());
            return;
        }
        goToMainMenu(actionEvent);
    }

    private void goToMainMenu(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Would you like to go back to the main menu?",
                ButtonType.YES,
                ButtonType.CANCEL
        );
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            setScene(actionEvent, "main.fxml");
            ContainerManager.getInstance().clearContainer();
        }
    }

    private void showContainer(Container container) {
        if (container.hasNoChildren()) {
            return;
        }
        addressBar.requestFocus();
        containerManager.setCurrentContainer(container);
        addressBar.setText(container.getPath());
        containerReadyListener.setContainer(container);
        container.setContainerReadyListener(this.containerReadyListener);
        if (!container.isStarted()) {
            Thread calculatorThread = new Thread(container::calculateSize);
            calculatorThread.setDaemon(true);
            calculatorThread.start();
        }
        foldersVBox.getChildren().clear();
        long ready = List.copyOf(container.getChildren())
                .stream()
                .peek(this::addContainerPane)
                .filter(Container::isReady)
                .count();
        updateProgress(ready, container.getNumberOfChildren());
    }

    private void updateProgress(long completed, long all) {
        boolean visibility = completed != all;
        progressIndicator.setVisible(visibility);
        progressText.setVisible(visibility);
        progressText.setText(completed + "/" + all);
    }

    private void addContainerPane(Container container) {
        ContainerPane containerPane = new ContainerPane(container);
        containerPane.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> showContainer(containerPane.getContainer()));
        foldersVBox.getChildren().add(containerPane);
    }
}
