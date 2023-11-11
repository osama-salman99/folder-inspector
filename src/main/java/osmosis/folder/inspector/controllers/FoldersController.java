package osmosis.folder.inspector.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import osmosis.folder.inspector.constants.Constant;
import osmosis.folder.inspector.constants.ResourcePaths;
import osmosis.folder.inspector.constants.UserMessages;
import osmosis.folder.inspector.container.Container;
import osmosis.folder.inspector.container.ContainerManager;
import osmosis.folder.inspector.container.ContainerReadyListener;
import osmosis.folder.inspector.container.DirectoryContainer;
import osmosis.folder.inspector.panes.ContainerPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FoldersController extends Controller {
    private static final ContainerManager containerManager = ContainerManager.getInstance();
    public VBox foldersVBox;
    public Button backButton;
    public Text progressText;
    public TextField addressBar;
    public ProgressIndicator progressIndicator;
    private ContainerReadyListener containerReadyListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(progressText, new Tooltip(UserMessages.ITEMS_CALCULATED));
        addressBar.setText(containerManager.getCurrentContainer().getPath());
        DirectoryContainer container = containerManager.getCurrentContainer();
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
                UserMessages.GO_BACK_MAIN_MENU,
                ButtonType.YES,
                ButtonType.CANCEL
        );
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            setScene(actionEvent, ResourcePaths.MAIN);
            ContainerManager.getInstance().clearContainer();
        }
    }

    private void showContainer(DirectoryContainer container) {
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
        progressText.setText(completed + Constant.FILE_SEPARATOR + all);
    }

    private void addContainerPane(Container container) {
        ContainerPane containerPane = new ContainerPane(container);
        if (container instanceof DirectoryContainer) {
            containerPane.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> showContainer((DirectoryContainer) containerPane.getContainer()));
        }
        foldersVBox.getChildren().add(containerPane);
    }
}
