package osmosis.folder.inspector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import osmosis.folder.inspector.constants.Constant;
import osmosis.folder.inspector.constants.ResourcePaths;
import osmosis.folder.inspector.dimensions.Dimensions;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FolderInspector extends Application {
    private static final Logger LOGGER = Logger.getLogger(FolderInspector.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOGGER.log(Level.INFO, "Initializing primary stage");
        setDimensions(primaryStage);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(ResourcePaths.MAIN_FXML)));
        primaryStage.setTitle(Constant.FOLDER_INSPECTOR);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        LOGGER.log(Level.INFO, "Primary stage shown");
    }

    private void setDimensions(Stage primaryStage) {
        Dimensions dimensions = Dimensions.calculate();
        primaryStage.setWidth(dimensions.getPrefWidth());
        primaryStage.setHeight(dimensions.getPrefHeight());
        primaryStage.setMinWidth(dimensions.getMinWidth());
        primaryStage.setMinHeight(dimensions.getMinHeight());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
