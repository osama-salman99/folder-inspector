package osmosis.folder.inspector.calculation;

import osmosis.folder.inspector.exceptions.CalculationException;
import osmosis.folder.inspector.exceptions.ConcurrencyException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FutureWrapper<T> {
    private final Future<T> future;

    private FutureWrapper(Future<T> future) {
        this.future = future;
    }

    public T get() {
        try {
            return future.get();
        } catch (InterruptedException exception) {
            throw new ConcurrencyException(exception);
        } catch (ExecutionException e) {
            throw new CalculationException(e);
        }
    }

    public boolean isNotDone() {
        return !isDone();
    }

    public boolean isDone() {
        return future.isDone();
    }

    public static <T> FutureWrapper<T> of(Future<T> future) {
        return new FutureWrapper<>(future);
    }
}
