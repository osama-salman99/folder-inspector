package osmosis.folder.inspector.test;

import java.io.File;
import java.util.Objects;

public class TestUtils {
    private static final TestUtils INSTANCE = new TestUtils();
    private TestUtils() {
    }

    public static TestUtils getInstance() {
        return INSTANCE;
    }

    public File getFile(String path) {
        return new File(Objects.requireNonNull(getClass().getClassLoader().getResource(path)).getFile());
    }
}
