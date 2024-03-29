package osmosis.folder.inspector.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import osmosis.folder.inspector.calculation.DirectorySizeCalculator;
import osmosis.folder.inspector.constants.Constant;
import osmosis.folder.inspector.constants.ResourcePaths;
import osmosis.folder.inspector.constants.UserMessages;
import osmosis.folder.inspector.constants.providers.AlertProvider;
import osmosis.folder.inspector.container.ChildContainerReadyListener;
import osmosis.folder.inspector.container.ChildrenContainersStatistics;
import osmosis.folder.inspector.container.Container;
import osmosis.folder.inspector.container.ContainerManager;
import osmosis.folder.inspector.container.DirectoryContainer;
import osmosis.folder.inspector.formatter.DigitalFormatter;
import osmosis.folder.inspector.panes.ContainerPane;

import java.net.URL;
import java.util.ResourceBundle;

public class FoldersController extends Controller implements ChildContainerReadyListener {
    public VBox foldersVBox;
    public Button backButton;
    public Text progressText;
    public TextField addressBar;
    public Text folderIsEmptyText;
    public Text directorySizeText;
    public Button copyAddressToClipboard;
    public ProgressIndicator progressIndicator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        installTooltips();
        initializeAddressBar();
        initializeParentContainer();
    }

    private void initializeParentContainer() {
        showContainer(containerManager.getCurrentContainer());
        startSizeCalculation();
    }

    @Override
    public void onContainerReady() {
        Platform.runLater(this::refreshContents);
    }

    @FXML
    public void copyAddressToClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(addressBar.getText());
        clipboard.setContent(content);
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        if (containerManager.getCurrentContainer().hasParentContainer()) {
            showContainer(containerManager.getCurrentContainer().getParent());
            return;
        }
        confirmGoingToMainMenu(actionEvent);
    }

    private void initializeAddressBar() {
        addressBar.setText(containerManager.getCurrentContainer().getPath());
    }

    private void installTooltips() {
        Tooltip.install(progressText, new Tooltip(UserMessages.ITEMS_CALCULATED));
        Tooltip.install(copyAddressToClipboard, new Tooltip(UserMessages.COPY_ADDRESS_TO_CLIPBOARD));
    }

    private void startSizeCalculation() {
        DirectorySizeCalculator.getInstance().calculate(containerManager.getCurrentContainer());
    }

    private void confirmGoingToMainMenu(ActionEvent actionEvent) {
        AlertProvider.createBackToMainMenuAlert()
                .showAndWait()
                .filter(ButtonType.YES::equals)
                .ifPresent(result -> goBackToMainMenu(actionEvent));
    }

    private void goBackToMainMenu(ActionEvent actionEvent) {
        setScene(actionEvent, ResourcePaths.MAIN_FXML);
        ContainerManager.getInstance().clearContainer();
    }

    private void showContainer(DirectoryContainer container) {
        containerManager.getCurrentContainer().clearChildContainerReadyListener();
        containerManager.setCurrentContainer(container);
        container.setChildContainerReadyListener(this);
        addressBar.requestFocus();
        addressBar.setText(container.getPath());
        folderIsEmptyText.setVisible(container.isEmpty());
        refreshContents();
    }

    private void refreshContents() {
        foldersVBox.getChildren().clear();
        DirectoryContainer container = containerManager.getCurrentContainer();
        if (container.isReady()) {
            directorySizeText.setText(DigitalFormatter.formatSize(container.getSize()));
        }
        ChildrenContainersStatistics statistics = ChildrenContainersStatistics.calculate(container.getChildrenContainers());
        statistics.getChildrenContainers().forEach(this::addContainerPane);
        updateProgress(statistics.getNumberOfReadyContainers(), statistics.getNumberOfChildren());
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
            addEventHandler(containerPane);
        }
        foldersVBox.getChildren().add(containerPane);
    }

    private void addEventHandler(ContainerPane containerPane) {
        containerPane.addEventHandler(MouseEvent.MOUSE_PRESSED, createMouseEventEventHandler(containerPane));
    }

    private EventHandler<MouseEvent> createMouseEventEventHandler(ContainerPane containerPane) {
        return mouseEvent -> showContainer((DirectoryContainer) containerPane.getContainer());
    }
}
