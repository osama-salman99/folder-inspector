module osmosis.folderinspector {
    requires javafx.controls;
    requires javafx.fxml;

    opens osmosis.folder.inspector to javafx.fxml;
    exports osmosis.folder.inspector;
    exports osmosis.folder.inspector.controllers;
    opens osmosis.folder.inspector.controllers to javafx.fxml;
    exports osmosis.folder.inspector.container;
    opens osmosis.folder.inspector.container to javafx.fxml;
    exports osmosis.folder.inspector.panes;
    opens osmosis.folder.inspector.panes to javafx.fxml;
}