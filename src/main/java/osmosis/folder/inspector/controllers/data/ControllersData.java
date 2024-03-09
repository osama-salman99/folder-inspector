package osmosis.folder.inspector.controllers.data;

import java.util.Optional;

public class ControllersData {
    private static final ControllersData INSTANCE = new ControllersData();
    private String mainPath;

    private ControllersData() {
    }

    public Optional<String> getMainPath() {
        return Optional.ofNullable(mainPath);
    }

    public void setMainPath(String mainPath) {
        this.mainPath = mainPath;
    }

    public static ControllersData getInstance() {
        return INSTANCE;
    }
}
