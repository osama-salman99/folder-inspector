package osmosis.folder.inspector.calculation;

import osmosis.folder.inspector.container.DirectoryContainer;

import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DirectorySizeCalculator {
    private static final Logger LOGGER = Logger.getLogger(DirectorySizeCalculator.class.getName());
    private static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();
    private static final DirectorySizeCalculator INSTANCE = new DirectorySizeCalculator();

    private DirectorySizeCalculator() {
    }

    public void calculate(DirectoryContainer container) {
        LOGGER.log(Level.INFO, "Started size calculation for {0}", container.getPath());
        FORK_JOIN_POOL.submit(new DirectorySizeCalculationTask(container));
    }

    public static DirectorySizeCalculator getInstance() {
        return INSTANCE;
    }
}
