package osmosis.folder.inspector.constants.providers;

import osmosis.folder.inspector.constants.ResourcePaths;

public class ResourcePathProvider {
    private ResourcePathProvider() {
    }

    public static String getFxml(String resourceName) {
        return ResourcePaths.FXML_DIR + resourceName;
    }
}
