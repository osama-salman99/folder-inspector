module osmosis.folderinspector {
    requires javafx.controls;
    requires javafx.fxml;

    exports osmosis.folder.inspector;
    exports osmosis.folder.inspector.panes;
    exports osmosis.folder.inspector.container;
    exports osmosis.folder.inspector.controllers;
    exports osmosis.folder.inspector.controllers.data;
    opens osmosis.folder.inspector to javafx.fxml;
    opens osmosis.folder.inspector.panes to javafx.fxml;
    opens osmosis.folder.inspector.container to javafx.fxml;
    opens osmosis.folder.inspector.controllers to javafx.fxml;
}