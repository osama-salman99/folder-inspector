package osmosis.folder.inspector;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String LOGGING_PROPERTIES = "/logging.properties";

    private Main() {
    }

    public static void main(String[] args) {
        configureLogging();
        LOGGER.log(Level.INFO, "Starting Folder Inspector");
        FolderInspector.main(args);
    }

    private static void configureLogging() {
        try (InputStream stream = Main.class.getResourceAsStream(LOGGING_PROPERTIES)) {
            if (stream == null) {
                System.err.println("Logging configuration not found on classpath: " + LOGGING_PROPERTIES);
                return;
            }
            LogManager.getLogManager().readConfiguration(stream);
        } catch (Exception exception) {
            System.err.println("Failed to load logging configuration: " + exception.getMessage());
        }
    }
}
