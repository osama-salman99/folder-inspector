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

public class FolderInspector extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        setDimensions(primaryStage);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(ResourcePaths.MAIN_FXML)));
        primaryStage.setTitle(Constant.FOLDER_INSPECTOR);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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
