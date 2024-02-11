package osmosis.folder.inspector.calculation;

import osmosis.folder.inspector.container.DirectoryContainer;

import java.util.concurrent.ForkJoinPool;

public class DirectorySizeCalculator {
    private static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();
    private static final DirectorySizeCalculator INSTANCE = new DirectorySizeCalculator();

    private DirectorySizeCalculator() {
    }

    public void calculate(DirectoryContainer container) {
        FORK_JOIN_POOL.submit(new DirectorySizeCalculationTask(container));
    }

    public static DirectorySizeCalculator getInstance() {
        return INSTANCE;
    }
}
