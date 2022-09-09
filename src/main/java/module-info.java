module osmosis.folderinspector {
	requires javafx.controls;
	requires javafx.fxml;


	opens osmosis.folder.inspector to javafx.fxml;
	exports osmosis.folder.inspector;
	exports osmosis.folder.inspector.controllers;
	opens osmosis.folder.inspector.controllers to javafx.fxml;
	exports osmosis.folder.inspector.file;
	opens osmosis.folder.inspector.file to javafx.fxml;
}