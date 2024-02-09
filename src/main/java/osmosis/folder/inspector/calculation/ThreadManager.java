package osmosis.folder.inspector.calculation;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static osmosis.folder.inspector.constants.Constant.NUMBER_OF_THREADS;

public final class ThreadManager {
    private static final ThreadManager INSTANCE = new ThreadManager();
    private final ExecutorService executors = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private ThreadManager() {
    }

    public <T> Future<T> execute(Callable<T> callable) {
        return executors.submit(callable);
    }

    public static ThreadManager getInstance() {
        return INSTANCE;
    }
}
