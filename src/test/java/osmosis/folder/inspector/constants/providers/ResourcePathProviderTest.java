package osmosis.folder.inspector.constants.providers;

import org.junit.jupiter.api.Test;
import osmosis.folder.inspector.constants.ResourcePaths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourcePathProviderTest {
    @Test
    public void getFxmlPrefixesWithFxmlDir() {
        assertEquals(ResourcePaths.FXML_DIR + "main.fxml", ResourcePathProvider.getFxml("main.fxml"));
    }

    @Test
    public void getFxmlHandlesEmptyResourceName() {
        assertEquals(ResourcePaths.FXML_DIR, ResourcePathProvider.getFxml(""));
    }
}
